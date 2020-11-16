package view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PauseView {
	private JFrame pauseFrame;
	
    private PauseView() {
        // TODO:
        // you should initalize JFrame and show it,
        // JFrame should be able to add Messages to queue
        // JFrame can be in a separate class or created JFrame with all the elements in this class
        // or you can make View a subclass of JFrame by extending it
        pauseFrame = new JFrame();

        JButton resume = new JButton("RESUME");
        JButton restart = new JButton("RESTART");

        resume.addActionListener(event -> {
            //action when button is clicked
        });

        restart.addActionListener(event -> {
           
        });

        // add everything and set layout and other standard JFrame settings
        pauseFrame.add(resume);
        pauseFrame.add(restart);
        pauseFrame.pack();
        pauseFrame.setLayout(new FlowLayout());
        pauseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pauseFrame.setVisible(false);
    }

}
