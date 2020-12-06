package controller;

import model.*;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.Timer;

public class Controller implements KeyListener, ActionListener {
    private BlockingQueue<Message> queue;
    private View view;
    private int cnt;
    private Airplane airplane;
    private int airplaneWidth;
    private int airplaneHeight;
    private BulletList bulletList;
    private BulletList enemyBulletList;
    private EnemyList enemyList;
    private GameInfo gameInfo; // Direct reference to the state of the Game/Application
    private Timer timer;

    // Added in Model
    private Score score;

    private final int DELAY = 70;
    private boolean[] moveAirplane = new boolean[] {false, false, false, false};
    private boolean bullet_available = true;

    private List<Valve> valves = new LinkedList<Valve>();

    public Controller(View view, Airplane airplane, BlockingQueue<Message> queue) {
        this.view = view;
        this.airplane = airplane;
        this.airplaneWidth = airplane.getWidth();
        this.airplaneHeight = airplane.getHeight();
        this.queue = queue;
        this.enemyList = new EnemyList();
        this.bulletList = new BulletList();
        this.enemyBulletList = new BulletList();

        this.score = new Score(0);

        timer = new Timer(DELAY, this);
        view.init(airplane, enemyList, bulletList, enemyBulletList, score);
        valves.add(new DoNewGameValve());
        valves.add(new DoHitValve());
        view.addKeyListener(this);
        timer.start();

    }

    public void mainLoop() {
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;
        while (response != ValveResponse.FINISH) {
            try {
                message = queue.take(); // <--- take next message from the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Look for a Valve that can process a message
            for (Valve valve : valves) {
                response = valve.execute(message);
                // if successfully processed or game over, leave the loop
                if (response != ValveResponse.MISS) {
                    break;
                }
            }
        }
    }

    private void updateGameInfo() {

    }

    private interface Valve {
        /**
         * Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }

    private class DoNewGameValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != NewGameMessage.class) {
                return ValveResponse.MISS;
            }
            // otherwise it means that it is a NewGameMessage message
            // actions in Model
            // actions in View
            return ValveResponse.EXECUTED;
        }
    }

    private class DoHitValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != HitMessage.class) {
                return ValveResponse.MISS;
            }
            // otherwise message is of HitMessage type
            // actions in Model and View
            return ValveResponse.EXECUTED;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cnt++;
        timer.restart();
        if (collisionCheck())
            timer.stop();

        if (moveAirplane[0] && airplane.getY() > 320)
            airplane.moveY(-10);
        if (moveAirplane[1] && airplane.getY() < 560 - airplaneHeight)
            airplane.moveY(10);
        if (moveAirplane[2] && airplane.getX() > 0)
            airplane.moveX(-10);
        if (moveAirplane[3] && airplane.getX() < 385 - airplaneWidth)
            airplane.moveX(10);
        if (cnt > 30 && enemyList.getSize() < 6) {
            enemyList.add(175, -50);
            cnt = 0;
        }
        //fire enemy bullets
        for (int i = 0; i < enemyList.getSize(); i++) {
            Enemy enemy = enemyList.get(i);
            enemy.move(400, 300);
            enemy.inc_cnt();
            if (enemy.bulletReady())
                enemyBulletList.add(new Bullet(enemy.getX() + 20, enemy.getY() + 50, 10, 10, 15));
        }
        view.repaint();
    }

    public boolean collisionCheck() {
        //checking between enemy bullets and player
        for (int i = 0; i < enemyBulletList.getSize(); i++) {
            Bullet bullet = enemyBulletList.get(i);
            int bX = bullet.getX();
            int bY = bullet.getY();
            int aX = airplane.getX();
            int aY = airplane.getY();
            if (bX + 8 >= aX && bX < aX + airplaneWidth && bY + 12 >= aY && bY < aY + airplaneHeight/3) {
                return true;
            }
        }

        //checking between enemies and player's bullets
        int enemyWidth = 50;
        int enemyHeight = 50;
        for (int i = 0; i < bulletList.getSize(); i++) {
            for (int j = 0; j < enemyList.getSize(); j++) {
                int bX = bulletList.get(i).getX();
                int bY = bulletList.get(i).getY();
                int eX = enemyList.get(j).getX();
                int eY = enemyList.get(j).getY();
                if (bX + 8 >= eX && bX < eX + enemyWidth && bY >= eY + enemyHeight / 2 && bY < eY + enemyHeight) {
                    bulletList.remove(i);
                    enemyList.remove(j);
                    score.addScore();
                    break;
                }
            }
        }
        return false;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 65)
            moveAirplane[2] = true;

        else if (e.getKeyCode() == 68)
            moveAirplane[3] = true;

        if (e.getKeyCode() == 87)
            moveAirplane[0] = true;

        else if (e.getKeyCode() == 83)
            moveAirplane[1] = true;

        if (e.getKeyCode() == 32 && bullet_available) {
            //shoot bullets
            bulletList.add(new Bullet(airplane.getX() + 20, airplane.getY() - 10, 10, 10, -20));
            bullet_available = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 65)
            moveAirplane[2] = false;

        if (e.getKeyCode() == 68)
            moveAirplane[3] = false;

        if (e.getKeyCode() == 87)
            moveAirplane[0] = false;

        if (e.getKeyCode() == 83)
            moveAirplane[1] = false;

        if (e.getKeyCode() == 32)
            bullet_available = true;
    }
}
