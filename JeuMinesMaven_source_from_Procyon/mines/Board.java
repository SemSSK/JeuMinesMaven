// 
// Decompiled by Procyon v0.5.36
// 

package mines;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.ImageObserver;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.JPanel;

public class Board extends JPanel
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
    private int mines;
    private int rows;
    private int cols;
    private int all_cells;
    private JLabel statusbar;
    
    public Board(final JLabel statusbar) {
        this.mines = 40;
        this.rows = 16;
        this.cols = 16;
        this.statusbar = statusbar;
        this.img = new Image[13];
        for (int i = 0; i < 13; ++i) {
            this.img[i] = new ImageIcon("images/" + i + ".gif").getImage();
        }
        this.setDoubleBuffered(true);
        this.addMouseListener(new MinesAdapter());
        this.newGame();
    }
    
    public void newGame() {
        int i = 0;
        int position = 0;
        int cell = 0;
        final Random random = new Random();
        this.inGame = true;
        this.mines_left = this.mines;
        this.all_cells = this.rows * this.cols;
        this.field = new int[this.all_cells];
        for (i = 0; i < this.all_cells; ++i) {
            this.field[i] = 10;
        }
        this.statusbar.setText(Integer.toString(this.mines_left));
        i = 0;
        while (i < this.mines) {
            position = (int)(this.all_cells * random.nextDouble());
            if (position < this.all_cells && this.field[position] != 19) {
                final int current_col = position % this.cols;
                this.field[position] = 19;
                ++i;
                if (current_col > 0) {
                    cell = position - 1 - this.cols;
                    if (cell >= 0 && this.field[cell] != 19) {
                        final int[] field = this.field;
                        final int n = cell;
                        ++field[n];
                    }
                    cell = position - 1;
                    if (cell >= 0 && this.field[cell] != 19) {
                        final int[] field2 = this.field;
                        final int n2 = cell;
                        ++field2[n2];
                    }
                    cell = position + this.cols - 1;
                    if (cell < this.all_cells && this.field[cell] != 19) {
                        final int[] field3 = this.field;
                        final int n3 = cell;
                        ++field3[n3];
                    }
                }
                cell = position - this.cols;
                if (cell >= 0 && this.field[cell] != 19) {
                    final int[] field4 = this.field;
                    final int n4 = cell;
                    ++field4[n4];
                }
                cell = position + this.cols;
                if (cell < this.all_cells && this.field[cell] != 19) {
                    final int[] field5 = this.field;
                    final int n5 = cell;
                    ++field5[n5];
                }
                if (current_col >= this.cols - 1) {
                    continue;
                }
                cell = position - this.cols + 1;
                if (cell >= 0 && this.field[cell] != 19) {
                    final int[] field6 = this.field;
                    final int n6 = cell;
                    ++field6[n6];
                }
                cell = position + this.cols + 1;
                if (cell < this.all_cells && this.field[cell] != 19) {
                    final int[] field7 = this.field;
                    final int n7 = cell;
                    ++field7[n7];
                }
                cell = position + 1;
                if (cell >= this.all_cells || this.field[cell] == 19) {
                    continue;
                }
                final int[] field8 = this.field;
                final int n8 = cell;
                ++field8[n8];
            }
        }
    }
    
    public void find_empty_cells(final int j) {
        final int current_col = j % this.cols;
        if (current_col > 0) {
            int cell = j - this.cols - 1;
            if (cell >= 0 && this.field[cell] > 9) {
                final int[] field = this.field;
                final int n = cell;
                field[n] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
            cell = j - 1;
            if (cell >= 0 && this.field[cell] > 9) {
                final int[] field2 = this.field;
                final int n2 = cell;
                field2[n2] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
            cell = j + this.cols - 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                final int[] field3 = this.field;
                final int n3 = cell;
                field3[n3] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
        }
        int cell = j - this.cols;
        if (cell >= 0 && this.field[cell] > 9) {
            final int[] field4 = this.field;
            final int n4 = cell;
            field4[n4] -= 10;
            if (this.field[cell] == 0) {
                this.find_empty_cells(cell);
            }
        }
        cell = j + this.cols;
        if (cell < this.all_cells && this.field[cell] > 9) {
            final int[] field5 = this.field;
            final int n5 = cell;
            field5[n5] -= 10;
            if (this.field[cell] == 0) {
                this.find_empty_cells(cell);
            }
        }
        if (current_col < this.cols - 1) {
            cell = j - this.cols + 1;
            if (cell >= 0 && this.field[cell] > 9) {
                final int[] field6 = this.field;
                final int n6 = cell;
                field6[n6] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
            cell = j + this.cols + 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                final int[] field7 = this.field;
                final int n7 = cell;
                field7[n7] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
            cell = j + 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                final int[] field8 = this.field;
                final int n8 = cell;
                field8[n8] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
        }
    }
    
    @Override
    public void paint(final Graphics g) {
        int cell = 0;
        int uncover = 0;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                cell = this.field[i * this.cols + j];
                if (this.inGame && cell == 9) {
                    this.inGame = false;
                }
                if (!this.inGame) {
                    if (cell == 19) {
                        cell = 9;
                    }
                    else if (cell == 29) {
                        cell = 11;
                    }
                    else if (cell > 19) {
                        cell = 12;
                    }
                    else if (cell > 9) {
                        cell = 10;
                    }
                }
                else if (cell > 19) {
                    cell = 11;
                }
                else if (cell > 9) {
                    cell = 10;
                    ++uncover;
                }
                g.drawImage(this.img[cell], j * 15, i * 15, this);
            }
        }
        if (uncover == 0 && this.inGame) {
            this.inGame = false;
            this.statusbar.setText("Game won");
        }
        else if (!this.inGame) {
            this.statusbar.setText("Game lost");
        }
    }
    
    static /* synthetic */ void access$5(final Board board, final int mines_left) {
        board.mines_left = mines_left;
    }
    
    static /* synthetic */ void access$7(final Board board, final boolean inGame) {
        board.inGame = inGame;
    }
    
    class MinesAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(final MouseEvent e) {
            final int x = e.getX();
            final int y = e.getY();
            final int cCol = x / 15;
            final int cRow = y / 15;
            boolean rep = false;
            if (!Board.this.inGame) {
                Board.this.newGame();
                Board.this.repaint();
            }
            if (x < Board.this.cols * 15 && y < Board.this.rows * 15) {
                if (e.getButton() == 3) {
                    if (Board.this.field[cRow * Board.this.cols + cCol] > 9) {
                        rep = true;
                        if (Board.this.field[cRow * Board.this.cols + cCol] <= 19) {
                            if (Board.this.mines_left > 0) {
                                final int[] access$3 = Board.this.field;
                                final int n = cRow * Board.this.cols + cCol;
                                access$3[n] += 10;
                                final Board this$0 = Board.this;
                                Board.access$5(this$0, this$0.mines_left - 1);
                                Board.this.statusbar.setText(Integer.toString(Board.this.mines_left));
                            }
                            else {
                                Board.this.statusbar.setText("No marks left");
                            }
                        }
                        else {
                            final int[] access$4 = Board.this.field;
                            final int n2 = cRow * Board.this.cols + cCol;
                            access$4[n2] -= 10;
                            final Board this$2 = Board.this;
                            Board.access$5(this$2, this$2.mines_left + 1);
                            Board.this.statusbar.setText(Integer.toString(Board.this.mines_left));
                        }
                    }
                }
                else {
                    if (Board.this.field[cRow * Board.this.cols + cCol] > 19) {
                        return;
                    }
                    if (Board.this.field[cRow * Board.this.cols + cCol] > 9 && Board.this.field[cRow * Board.this.cols + cCol] < 29) {
                        final int[] access$5 = Board.this.field;
                        final int n3 = cRow * Board.this.cols + cCol;
                        access$5[n3] -= 10;
                        rep = true;
                        if (Board.this.field[cRow * Board.this.cols + cCol] == 9) {
                            Board.access$7(Board.this, false);
                        }
                        if (Board.this.field[cRow * Board.this.cols + cCol] == 0) {
                            Board.this.find_empty_cells(cRow * Board.this.cols + cCol);
                        }
                    }
                }
                if (rep) {
                    Board.this.repaint();
                }
            }
        }
    }
}
