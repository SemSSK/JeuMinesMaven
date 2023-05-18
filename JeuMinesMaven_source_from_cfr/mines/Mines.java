/*
 * Decompiled with CFR 0.150.
 */
package mines;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import mines.Board;

public class Mines
extends JFrame {
    private static final long serialVersionUID = 4772165125287256837L;
    private final int WIDTH = 250;
    private final int HEIGHT = 290;
    private JLabel statusbar;

    public Mines() {
        this.setDefaultCloseOperation(3);
        this.setSize(250, 290);
        this.setLocationRelativeTo(null);
        this.setTitle("Minesweeper");
        this.statusbar = new JLabel("");
        this.add((Component)this.statusbar, "South");
        this.add(new Board(this.statusbar));
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Mines();
    }
}

