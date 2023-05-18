package mines;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board
  extends JPanel
{
  private static final long serialVersionUID = 6195235521361212179L;
  private final int NUM_IMAGES = 13;
  private final int CELL_SIZE = 15;
  
  private final int COVER_FOR_CELL = 10;
  private final int MARK_FOR_CELL = 10;
  private final int EMPTY_CELL = 0;
  private final int MINE_CELL = 9;
  private final int COVERED_MINE_CELL = 19;
  private final int MARKED_MINE_CELL = 29;
  
  private final int DRAW_MINE = 9;
  private final int DRAW_COVER = 10;
  private final int DRAW_MARK = 11;
  private final int DRAW_WRONG_MARK = 12;
  
  private int[] field;
  private boolean inGame;
  private int mines_left;
  private Image[] img;
  private int mines = 40;
  private int rows = 16;
  private int cols = 16;
  private int all_cells;
  private JLabel statusbar;
  
  public Board(JLabel statusbar) {
    this.statusbar = statusbar;
    
    img = new Image[13];
    
    for (int i = 0; i < 13; i++) {
      img[i] = new ImageIcon("images/" + i + 
        ".gif").getImage();
    }
    
    setDoubleBuffered(true);
    
    addMouseListener(new MinesAdapter());
    newGame();
  }
  



  public void newGame()
  {
    int i = 0;
    int position = 0;
    int cell = 0;
    
    Random random = new Random();
    inGame = true;
    mines_left = mines;
    
    all_cells = (rows * cols);
    field = new int[all_cells];
    
    for (i = 0; i < all_cells; i++) {
      field[i] = 10;
    }
    statusbar.setText(Integer.toString(mines_left));
    
    i = 0;
    while (i < mines)
    {
      position = (int)(all_cells * random.nextDouble());
      
      if ((position < all_cells) && 
        (field[position] != 19))
      {
        int current_col = position % cols;
        field[position] = 19;
        i++;
        
        if (current_col > 0) {
          cell = position - 1 - cols;
          if ((cell >= 0) && 
            (field[cell] != 19))
            field[cell] += 1;
          cell = position - 1;
          if ((cell >= 0) && 
            (field[cell] != 19)) {
            field[cell] += 1;
          }
          cell = position + cols - 1;
          if ((cell < all_cells) && 
            (field[cell] != 19)) {
            field[cell] += 1;
          }
        }
        cell = position - cols;
        if ((cell >= 0) && 
          (field[cell] != 19))
          field[cell] += 1;
        cell = position + cols;
        if ((cell < all_cells) && 
          (field[cell] != 19)) {
          field[cell] += 1;
        }
        if (current_col < cols - 1) {
          cell = position - cols + 1;
          if ((cell >= 0) && 
            (field[cell] != 19))
            field[cell] += 1;
          cell = position + cols + 1;
          if ((cell < all_cells) && 
            (field[cell] != 19))
            field[cell] += 1;
          cell = position + 1;
          if ((cell < all_cells) && 
            (field[cell] != 19)) {
            field[cell] += 1;
          }
        }
      }
    }
  }
  
  public void find_empty_cells(int j) {
    int current_col = j % cols;
    

    if (current_col > 0) {
      int cell = j - cols - 1;
      if ((cell >= 0) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
      cell = j - 1;
      if ((cell >= 0) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
      cell = j + cols - 1;
      if ((cell < all_cells) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
    }
    int cell = j - cols;
    if ((cell >= 0) && 
      (field[cell] > 9)) {
      field[cell] -= 10;
      if (field[cell] == 0) {
        find_empty_cells(cell);
      }
    }
    cell = j + cols;
    if ((cell < all_cells) && 
      (field[cell] > 9)) {
      field[cell] -= 10;
      if (field[cell] == 0) {
        find_empty_cells(cell);
      }
    }
    if (current_col < cols - 1) {
      cell = j - cols + 1;
      if ((cell >= 0) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
      cell = j + cols + 1;
      if ((cell < all_cells) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
      cell = j + 1;
      if ((cell < all_cells) && 
        (field[cell] > 9)) {
        field[cell] -= 10;
        if (field[cell] == 0) {
          find_empty_cells(cell);
        }
      }
    }
  }
  
  public void paint(Graphics g)
  {
    int cell = 0;
    int uncover = 0;
    
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++)
      {
        cell = field[(i * cols + j)];
        
        if ((inGame) && (cell == 9)) {
          inGame = false;
        }
        if (!inGame) {
          if (cell == 19) {
            cell = 9;
          } else if (cell == 29) {
            cell = 11;
          } else if (cell > 19) {
            cell = 12;
          } else if (cell > 9) {
            cell = 10;
          }
          
        }
        else if (cell > 19) {
          cell = 11;
        } else if (cell > 9) {
          cell = 10;
          uncover++;
        }
        

        g.drawImage(img[cell], j * 15, 
          i * 15, this);
      }
    }
    
    if ((uncover == 0) && (inGame)) {
      inGame = false;
      statusbar.setText("Game won");
    } else if (!inGame) {
      statusbar.setText("Game lost");
    }
  }
  
  class MinesAdapter extends MouseAdapter { MinesAdapter() {}
    
    public void mousePressed(MouseEvent e) { int x = e.getX();
      int y = e.getY();
      
      int cCol = x / 15;
      int cRow = y / 15;
      
      boolean rep = false;
      
      if (!inGame) {
        newGame();
        repaint();
      }
      
      if ((x < cols * 15) && (y < rows * 15))
      {
        if (e.getButton() == 3)
        {
          if (field[(cRow * cols + cCol)] > 9) {
            rep = true;
            
            if (field[(cRow * cols + cCol)] <= 19) {
              if (mines_left > 0) {
                field[(cRow * cols + cCol)] += 10;
                mines_left -= 1;
                statusbar.setText(Integer.toString(mines_left));
              } else {
                statusbar.setText("No marks left");
              }
            } else {
              field[(cRow * cols + cCol)] -= 10;
              mines_left += 1;
              statusbar.setText(Integer.toString(mines_left));
            }
          }
        }
        else
        {
          if (field[(cRow * cols + cCol)] > 19) {
            return;
          }
          
          if ((field[(cRow * cols + cCol)] > 9) && 
            (field[(cRow * cols + cCol)] < 29))
          {
            field[(cRow * cols + cCol)] -= 10;
            rep = true;
            
            if (field[(cRow * cols + cCol)] == 9)
              inGame = false;
            if (field[(cRow * cols + cCol)] == 0) {
              find_empty_cells(cRow * cols + cCol);
            }
          }
        }
        if (rep) {
          repaint();
        }
      }
    }
  }
}
