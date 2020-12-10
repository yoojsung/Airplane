import controller.Controller;
import controller.Message;
import model.*;
import view.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author Airplane team
 * this is the main method
 * creates View, Airplane object, controller
 *
 */
public class AirplaneMain {
    private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private static View view;
    private static Airplane airplane;

    public static void main(String[] args) {
        view = View.init(queue);
        airplane = new Airplane(175, 500, 50, 55);
        Controller controller = new Controller(view, airplane, queue);

        controller.mainLoop();
        view.dispose();
        queue.clear();
    }
}

