package controller;

import model.*;
import view.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 */

public class Controller {
    private BlockingQueue<Message> queue;
    private View view; 
    private Airplane airplane;
    private BulletList bulletList;
    private BulletList enemyBulletList;
    private EnemyList enemyList;
    private Score score;

    private List<Valve> valves = new LinkedList<Valve>();

    /**
     *
     * @param view initialize view
     * @param airplane initialize airplane
     * @param queue initialize blockingQueue
     */
    public Controller(View view, Airplane airplane, BlockingQueue<Message> queue) {
        this.view = view;
        this.airplane = airplane;
        
        this.queue = queue;
        this.enemyList = new EnemyList();
        this.bulletList = new BulletList();
        this.enemyBulletList = new BulletList();
        this.score = new Score(0);
        
        view.init(airplane, enemyList, bulletList, enemyBulletList, score);
        valves.add(new DoNewGameValve());
        valves.add(new RemoveValve());
        valves.add(new MoveAirplaneValve());
        valves.add(new AddEnemyValve());
        valves.add(new AddBulletValve());
        valves.add(new MoveListValve());
        valves.add(new AddScoreValve());
    }

    /**
     * check valve response and get a next message from blockingQueue
     * until there is no more valve response
     */
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

    private interface Valve {
    	//Performs certain action in response to message
        public ValveResponse execute(Message message);
    }

    private class DoNewGameValve implements Valve {
        /**
         *
         * @param check message type and start timer
         * @return valveresponse to let know execution is done
         */
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != NewGameMessage.class) {
                return ValveResponse.MISS;
            }
            view.setTimer(true);
            return ValveResponse.EXECUTED;
        }
    }

    private class AddEnemyValve implements Valve {
        /**
         *
         * @param message check message type and add to enemyList
         * @return valveresponse to let know execution is done
         */

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != AddEnemyMessage.class) {
                return ValveResponse.MISS;
            }
            AddEnemyMessage m = (AddEnemyMessage) message;
            enemyList.add(m.getX(), m.getY());
            return ValveResponse.EXECUTED;
        }
    }
    
    private class AddBulletValve implements Valve {
        /**
         *
         * @param message check message and add bullet list
         * @return valveresponse to let know execution is done
         */
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != AddBulletMessage.class) {
                return ValveResponse.MISS;
            }
            AddBulletMessage m = (AddBulletMessage) message;
            bulletList.add(m.getBullet());
            return ValveResponse.EXECUTED;
        }
    }
    
    private class RemoveValve implements Valve {
        /**
         *
         * @param message check messageType and check if object is enemy, bullet, or enemyBUllet
         *        ( 0 = enemy 1 = bullet 2 = enemyBullet  ) and remove the object
         * @return valveresponse to let know execution is done
         */
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != RemoveMessage.class) {
                return ValveResponse.MISS;
            }
            RemoveMessage m = (RemoveMessage) message;
            if (m.getWhichObj() == 0) 
            	enemyList.remove(m.getIndex());
            else if (m.getWhichObj() == 1) 
            	bulletList.remove(m.getIndex());
            else if (m.getWhichObj() == 2) 
            	enemyBulletList.remove(m.getIndex());
            return ValveResponse.EXECUTED;
        }
    }

    private class MoveAirplaneValve implements Valve {
        /**
         *
         * @param message check message type and move airplane X cordinate or Y cordinate
         * @return valveresponse to let know execution is done
         */

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != MoveAirplaneMessage.class) {
                return ValveResponse.MISS;
            }
            MoveAirplaneMessage m = (MoveAirplaneMessage) message;
        	if (m.getXorY() == 0)
        		airplane.moveX(m.getSpeed());
        	else
        		airplane.moveY(m.getSpeed());
            return ValveResponse.EXECUTED;
        }
    }
    
    private class MoveListValve implements Valve {
        /**
         *
         * @param message　check message and move enemy and bullets
         * @return valveresponse to let know execution is done
         */
    	@Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != MoveListMessage.class) {
                return ValveResponse.MISS;
            }
        	for (int i = 0; i < enemyList.getSize(); i++) {
    			Enemy enemy = enemyList.get(i);
    			enemy.move(400, 300);
    			enemy.inc_cnt();
    			if (enemy.bulletReady())
    				enemyBulletList.add(new Bullet(enemy.getX() + 20, enemy.getY() + 50, 10, 10, 15));
    		}
        	
        	for (int i = 0; i < bulletList.getSize(); i++) 
        		bulletList.get(i).move();
        		
        	for (int i = 0; i < enemyBulletList.getSize(); i++) 
        		enemyBulletList.get(i).move();
        	
            return ValveResponse.EXECUTED;
        }
    }


    private class AddScoreValve implements Valve {
        /**
         *
         * @param message check message and add score when bullet hits enemy
         * @return valveresponse to let know execution is done
         */
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != AddScoreMessage.class) {
                return ValveResponse.MISS;
            }
        	score.addScore();
            return ValveResponse.EXECUTED;
        }
    }
}
