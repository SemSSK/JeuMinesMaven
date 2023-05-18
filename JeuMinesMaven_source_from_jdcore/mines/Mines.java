package mines;

import javax.swing.JFrame;
import javax.swing.JLabel;




public class Mines
  extends JFrame
{
  private static final long serialVersionUID = 4772165125287256837L;
  private final int WIDTH = 250;
  private final int HEIGHT = 290;
  
  private JLabel statusbar;
  
  public Mines()
  {
    setDefaultCloseOperation(3);
    setSize(250, 290);
    setLocationRelativeTo(null);
    setTitle("Minesweeper");
    statusbar = new JLabel("");
    add(statusbar, "South");
    add(new Board(statusbar));
    setResizable(false);
    setVisible(true);
  }
  
  public static void main(String[] args) {
    new Mines();
  }
}
