package minesDecompiledDotClass;

import javax.swing.JLabel;
import javax.swing.JFrame;

public class Mines extends JFrame
{
    private static final long serialVersionUID = 4772165125287256837L;
    private final int WIDTH = 250;
    private final int HEIGHT = 290;
    private JLabel statusbar;

    public Mines() {
        this.setDefaultCloseOperation(3);
        this.setSize(250, 290);
        this.setLocationRelativeTo(null);
        this.setTitle("Minesweeper");
        this.add(this.statusbar = new JLabel(""), "South");
        this.add(new BoardProcyon(this.statusbar));
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(final String[] args) {
        new Mines();
    }
}