package view;

import controller.Message;
import controller.NewGameMessage;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.BlockingQueue;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private Airplane airplane;
	private BulletList bulletList;
	private BulletList enemyBulletList;
	private EnemyList enemyList;
    private BlockingQueue<Message> queue;
    private Score score;
    private double x, y, w, h;

    public static View init(BlockingQueue<Message> queue) {
        // Create object of type view
        return new View(queue);
    }
    public void init(Airplane airplane, EnemyList enemyList, BulletList bulletList, BulletList bulletList2, Score score) {
    	this.airplane = airplane;
    	this.enemyList = enemyList;
    	this.bulletList = bulletList;
    	this.enemyBulletList = bulletList2;
    	this.score = score;
    }
    private View(BlockingQueue<Message> queue) {
        this.queue = queue;
        // TODO:
        // you should initialize JFrame and show it,
        // JFrame should be able to add Messages to queue
        // JFrame can be in a separate class or created JFrame with all the elements in this class
        // or you can make View a subclass of JFrame by extending it
        
        JButton startGame = new JButton("Start Game");
        
        startGame.addActionListener(event -> {
            try {
                this.queue.put(new NewGameMessage()); // <--- adding NewGame message to the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // add everything and set layout and other standard JFrame settings
        setTitle("Airplane");
        setSize(400, 600);
        setLocationRelativeTo(null);
		setResizable(false);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
            	super.paint(g);
        		Graphics2D g2 = (Graphics2D) g;
        			
        		g2.setColor(Color.ORANGE);
        		x = airplane.getX();
        		y = airplane.getY();
        		w = airplane.getWidth();
        		h = airplane.getHeight();
        		g2.fill(new RoundRectangle2D.Double(x, y, w, h/4, 10, 10)); //wing
        		g2.fill(new RoundRectangle2D.Double(x + (3 * (w / 8)), y - 3, w / 4, h, 10, 10)); //body
        		g2.fill(new RoundRectangle2D.Double(x + (w / 6), y + 2 * (h / 3) + 3, w / 3 * 2, h/5, 30, 30)); //tail
        		
        		drawEnemies(g);
        		drawBullets(g);
        		drawEnemyBullets(g);
        		drawScore(g);
            }
        };
        add(panel);
        panel.setLayout(new FlowLayout());
        //panel.add(startGame);
        setVisible(true);
        panel.setBackground(new Color(130, 190, 230));
        panel.setFocusable(true);
        setFocusable(true);
        
    }
    
    public void change() {
        repaint();
    }

    public void dispose() {
        // TODO: clear all the resources
        // for example, dispose();
    }
    
    public void drawEnemies(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);

        for (int i = 0; i < enemyList.getSize(); i++) {
			x = enemyList.get(i).getX();
    		y = enemyList.get(i).getY();
    		w = enemyList.get(i).getWidth();
    		h = enemyList.get(i).getHeight();
			g2.fill(new RoundRectangle2D.Double(x, y + h * 0.75, w, h/4, 10, 10)); //wing
    		g2.fill(new RoundRectangle2D.Double(x + (3 * (w / 8)), y + 3, w / 4, h, 10, 10)); //body
            g2.fill(new RoundRectangle2D.Double(x + 8, y, w / 3 * 2, h/5, 30, 30)); //tail
		}
    }
    
    public void drawBullets(Graphics g) {
        g.setColor(Color.RED);

        for (int i = 0; i < bulletList.getSize(); i++) {
            Bullet bullet = bulletList.get(i);
            g.drawOval(bullet.getX(), bullet.getY(), 10, 10);

            bullet.move();

            if (bullet.getY() <= 0) {
                bulletList.remove(i);
            }
        }
    }
    
    public void drawEnemyBullets(Graphics g) {
        g.setColor(Color.BLUE);

        for (int i = 0; i < enemyBulletList.getSize(); i++) {
            Bullet bullet = enemyBulletList.get(i);
            g.drawOval(bullet.getX(), bullet.getY(), 10, 10);

            bullet.move();

            if (bullet.getY() >= 600) {
            	enemyBulletList.remove(i);
            }
        }
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.drawString("Score: " + this.score.getScore(), 137, 40);
    }
   
}