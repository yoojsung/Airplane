package view;

import controller.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class View extends JFrame implements KeyListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private Airplane airplane;
	private BulletList bulletList;
	private BulletList enemyBulletList;
	private EnemyList enemyList;
	private Timer timer;
    private final int DELAY = 70;
    private boolean[] moveAirplane = new boolean[] {false, false, false, false};
    private boolean bullet_available = true;
    private int airplaneWidth;
    private int airplaneHeight;
    private int cnt;
    private Score score;
	
    private BlockingQueue<Message> queue;
    private double x, y, w, h;

    public static View init(BlockingQueue<Message> queue) {
        // Create object of type view
        return new View(queue);
    }

	/**
	 * initialize object and arraylist
	 * @param airplane
	 * @param enemyList
	 * @param bulletList
	 * @param bulletList2
	 * @param score
	 */
    public void init(Airplane airplane, EnemyList enemyList, BulletList bulletList, BulletList bulletList2, Score score) {
    	this.airplane = airplane;
    	this.enemyList = enemyList;
    	this.bulletList = bulletList;
    	this.enemyBulletList = bulletList2;
    	this.airplaneWidth = airplane.getWidth();
        this.airplaneHeight = airplane.getHeight();
        this.score = score;
    }

	/**
	 *
	 * @param queue get message from queue
	 *  paint objects and button
	 */
	private View(BlockingQueue<Message> queue) {
        this.queue = queue;
        // TODO:
        // you should initialize JFrame and show it,
        // JFrame should be able to add Messages to queue
        // JFrame can be in a separate class or created JFrame with all the elements in this class
        // or you can make View a subclass of JFrame by extending it
        
        JButton startGame = new JButton("Start Game");
        
        // add everything and set layout and other standard JFrame settings	
        setTitle("Airplane");
        setSize(400, 600);
        setLocationRelativeTo(null);
		setResizable(false);
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
        panel.add(startGame);
        panel.setLayout(new FlowLayout());
        setVisible(true);
        panel.setBackground(new Color(130, 190, 230));
        panel.setFocusable(true);
        setFocusable(true);
        timer = new Timer(DELAY, this);
        addKeyListener(this);
        
        startGame.addActionListener(event -> {
            try {
            	startGame.setVisible(false);
                this.queue.put(new NewGameMessage()); // <--- adding NewGame message to the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
    }
   
    public void dispose() {
        // TODO: clear all the resources
        // for example, dispose();
    }
    
    public void setTimer(boolean b) {
    	timer.start();
    }

	/**
	 *
	 * @param g
	 * draw enemy in enemylist
	 */
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

	/**
	 *
	 * @param g
	 * draw bullet in enemylist
	 */
	public void drawBullets(Graphics g) {
        g.setColor(Color.RED);

        for (int i = 0; i < bulletList.getSize(); i++) {
            Bullet bullet = bulletList.get(i);
            g.drawOval(bullet.getX(), bullet.getY(), 10, 10);

            if (bullet.getY() <= 0) {
            	this.queue.add(new RemoveMessage(1, i));
            }
        }
    }

	/**
	 *
	 * @param g
	 * draw enemy bullets in enemybulletlist
	 */
	public void drawEnemyBullets(Graphics g) {
        g.setColor(Color.BLUE);

        for (int i = 0; i < enemyBulletList.getSize(); i++) {
            Bullet bullet = enemyBulletList.get(i);
            g.drawOval(bullet.getX(), bullet.getY(), 10, 10);

            if (bullet.getY() >= 600) {
            	this.queue.add(new RemoveMessage(2, i));
            }
        }
    }

	/**
	 *
	 * @param g
	 * user's score
	 */
	public void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.drawString("Score: " + this.score.getScore(), 137, 55);
    }

	/**
	 * move airplane add to blockingQueue
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		cnt++;
		timer.restart();
		if (collisionCheck()) 
			timer.stop();
		
		if (moveAirplane[0] && airplane.getY() > 320) 
			this.queue.add(new MoveAirplaneMessage(1, -10));
		if (moveAirplane[1] && airplane.getY() < 560 - airplaneHeight)
			this.queue.add(new MoveAirplaneMessage(1, 10));
		if (moveAirplane[2] && airplane.getX() > 0) 
			this.queue.add(new MoveAirplaneMessage(0, -10));
		if (moveAirplane[3] && airplane.getX() < 385 - airplaneWidth) 
			this.queue.add(new MoveAirplaneMessage(0, 10));
		if (cnt > 30 && enemyList.getSize() < 6) {
			enemyList.add(175, -50);
			cnt = 0;
		}
		this.queue.add(new MoveListMessage());
		repaint();		
	}

	/**
	 * check if player is hit by enemy bullets
	 * when player hits bullet, remove the bullet from list
	 * @return
	 */
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
					this.queue.add(new RemoveMessage(0, j));
					this.queue.add(new RemoveMessage(1, i));
					this.queue.add(new AddScoreMessage());
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

	/**
	 * key listner move airplane
	 * @param e
	 */
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
			this.queue.add(new AddBulletMessage(new Bullet(airplane.getX() + 20, airplane.getY() - 10, 10, 10, -20)));
            bullet_available = false;
		}
	}

	/**
	 * key listner move airplane
	 * @param e
	 */
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