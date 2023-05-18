package mines;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Mines extends JFrame {
    private static final long serialVersionUID = 4772165125287256837L;
    private final int HEIGHT = 290;
    private final int WIDTH = 250;
    private JLabel statusbar;

    public Mines() {
        setDefaultCloseOperation(3);
        setSize(250, 290);
        setLocationRelativeTo((Component) null);
        setTitle("Minesweeper");
        this.statusbar = new JLabel("");
        add(this.statusbar, "South");
        add(new Board(this.statusbar));
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Mines();
    }
}
