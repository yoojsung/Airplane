package view;

import controller.Message;
import controller.NewGameMessage;
import model.Airplane;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.BlockingQueue;

public class View extends JFrame {
    private Airplane airplane;
    private BlockingQueue<Message> queue;

    public static View init(BlockingQueue<Message> queue) {
        // Create object of type view
        return new View(queue);
    }
    public void init(Airplane airplane) {
    	this.airplane = airplane;
    }
    private View(BlockingQueue<Message> queue) {
        this.queue = queue;
        // TODO:
        // you should initalize JFrame and show it,
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
        setSize(400, 600);
        setLocationRelativeTo(null);
		setResizable(false);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        JPanel panel = new JPanel() {
            public void paint(Graphics g) {
            	super.paint(g);
        		Graphics2D g2 = (Graphics2D) g;
        			
        		g2.setColor(Color.ORANGE);
        		double x = airplane.getX();
        		double y = airplane.getY();
        		double w = airplane.getWidth();
        		double h = airplane.getHeight();
        		RoundRectangle2D wing = new RoundRectangle2D.Double(x, y, w, h/4, 10, 10);
        		RoundRectangle2D body = new RoundRectangle2D.Double(x + (3 * (w / 8)), y - 3, w / 4, h, 10, 10);
        		RoundRectangle2D tail = new RoundRectangle2D.Double(x + (w / 6), y + 2 * (h / 3) + 3, w / 3 * 2, h/5, 30, 30);
        		g2.fill(wing);
        		g2.fill(body);
        		g2.fill(tail);
            }
        };
        add(panel);
        panel.setLayout(new FlowLayout());
        //panel.add(startGame);
        panel.setFocusable(true);
        setFocusable(true);
        
    }
    
    /**
    public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
			
		g2.setColor(Color.ORANGE);
		double x = airplane.getX();
		double y = airplane.getY();
		double w = airplane.getWidth();
		double h = airplane.getHeight();
		RoundRectangle2D wing = new RoundRectangle2D.Double(x, y, w, h/4, 10, 10);
		RoundRectangle2D body = new RoundRectangle2D.Double(x + (3 * (w / 8)), y - 3, w / 4, h, 10, 10);
		RoundRectangle2D tail = new RoundRectangle2D.Double(x + (w / 6), y + 2 * (h / 3) + 3, w / 3 * 2, h/5, 30, 30);
		g2.fill(wing);
		g2.fill(body);
		g2.fill(tail);
		
		
	}
	**/
    
    public void change() {
        // TODO: do all the updates and repaint
        //repaint();
    }

    public void dispose() {
        // TODO: clear all the resources
        // for example, dispose();
    }
   
}