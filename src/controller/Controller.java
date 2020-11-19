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
    private View view; // Direct reference to view
    //private Model model; // Direct reference to model
    private Airplane airplane;
    private GameInfo gameInfo; // Direct reference to the state of the Game/Application
    private Timer timer;
    private boolean[] moveAirplane = new boolean[] {false, false, false, false};

    private List<Valve> valves = new LinkedList<Valve>();

    public Controller(View view, Airplane airplane, BlockingQueue<Message> queue) {
        this.view = view;
        this.airplane = airplane;
        this.queue = queue;
        
        timer = new Timer(50, this);
        view.init(airplane);
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
		timer.restart();
		if (moveAirplane[0]) 
			airplane.moveY(-10);
		if (moveAirplane[1]) 
			airplane.moveY(10);
		if (moveAirplane[2]) 
			airplane.moveX(-10);
		if (moveAirplane[3]) 
			airplane.moveX(10);
		view.repaint();		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 65) {
			moveAirplane[2] = true;
		}
		if (e.getKeyCode() == 68) {
			moveAirplane[3] = true;
		}
		if (e.getKeyCode() == 87) {
			moveAirplane[0] = true;
		}
		if (e.getKeyCode() == 83) {
			moveAirplane[1] = true;
		}
		if (e.getKeyCode() == 32) {
			//shoot bullets
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 65) {
			moveAirplane[2] = false;
		}
		if (e.getKeyCode() == 68) {
			moveAirplane[3] = false;
		}
		if (e.getKeyCode() == 87) {
			moveAirplane[0] = false;
		}
		if (e.getKeyCode() == 83) {
			moveAirplane[1] = false;
		}
	}
}

