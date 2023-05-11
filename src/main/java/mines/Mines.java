package mines;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

// Source: http://zetcode.com/tutorials/javagamestutorial/minesweeper/

/**
 * Entry class of the project
 */
public class Mines extends JFrame {
    private static final long serialVersionUID = 4772165125287256837L;

    /**
     * Represents the width of the window
     */
    private static final int WIDTH = 250;
    /**
     * Represents the height of the window
     */
    private static final int HEIGHT = 290;

    /**
     * Used to display the status of the game
     * when the game is ongoing shows the number of flags left
     * when the game has ended shows the result either game lost or won
     */
    private JLabel statusbar;

    /**
     * Constructor of the class
     * initializes the {@code Board} class
     * and the interface
     */
    public Mines() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        add(new Board(statusbar));

        setResizable(false);
        setVisible(true);
    }

    /**
     * Entry point methods starts the game
     */
    public static void main(String[] args) {
        new Mines();
    }
}