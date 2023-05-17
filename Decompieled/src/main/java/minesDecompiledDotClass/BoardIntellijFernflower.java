package minesDecompiledDotClass;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
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
        this.img = new Image[13];

        for(int i = 0; i < 13; ++i) {
            this.img[i] = (new ImageIcon("images/" + i + ".gif")).getImage();
        }

        this.setDoubleBuffered(true);
        this.addMouseListener(new MinesAdapter());
        this.newGame();
    }

    public void newGame() {
        int i = false;
        int position = false;
        int cell = false;
        Random random = new Random();
        this.inGame = true;
        this.mines_left = this.mines;
        this.all_cells = this.rows * this.cols;
        this.field = new int[this.all_cells];

        int i;
        for(i = 0; i < this.all_cells; ++i) {
            this.field[i] = 10;
        }

        this.statusbar.setText(Integer.toString(this.mines_left));
        i = 0;

        while(i < this.mines) {
            int position = (int)((double)this.all_cells * random.nextDouble());
            if (position < this.all_cells && this.field[position] != 19) {
                int current_col = position % this.cols;
                this.field[position] = 19;
                ++i;
                int var10002;
                int cell;
                if (current_col > 0) {
                    cell = position - 1 - this.cols;
                    if (cell >= 0 && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }

                    cell = position - 1;
                    if (cell >= 0 && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }

                    cell = position + this.cols - 1;
                    if (cell < this.all_cells && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }
                }

                cell = position - this.cols;
                if (cell >= 0 && this.field[cell] != 19) {
                    var10002 = this.field[cell]++;
                }

                cell = position + this.cols;
                if (cell < this.all_cells && this.field[cell] != 19) {
                    var10002 = this.field[cell]++;
                }

                if (current_col < this.cols - 1) {
                    cell = position - this.cols + 1;
                    if (cell >= 0 && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }

                    cell = position + this.cols + 1;
                    if (cell < this.all_cells && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }

                    cell = position + 1;
                    if (cell < this.all_cells && this.field[cell] != 19) {
                        var10002 = this.field[cell]++;
                    }
                }
            }
        }

    }

    public void find_empty_cells(int j) {
        int current_col = j % this.cols;
        int[] var10000;
        int cell;
        if (current_col > 0) {
            cell = j - this.cols - 1;
            if (cell >= 0 && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }

            cell = j - 1;
            if (cell >= 0 && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }

            cell = j + this.cols - 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
        }

        cell = j - this.cols;
        if (cell >= 0 && this.field[cell] > 9) {
            var10000 = this.field;
            var10000[cell] -= 10;
            if (this.field[cell] == 0) {
                this.find_empty_cells(cell);
            }
        }

        cell = j + this.cols;
        if (cell < this.all_cells && this.field[cell] > 9) {
            var10000 = this.field;
            var10000[cell] -= 10;
            if (this.field[cell] == 0) {
                this.find_empty_cells(cell);
            }
        }

        if (current_col < this.cols - 1) {
            cell = j - this.cols + 1;
            if (cell >= 0 && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }

            cell = j + this.cols + 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }

            cell = j + 1;
            if (cell < this.all_cells && this.field[cell] > 9) {
                var10000 = this.field;
                var10000[cell] -= 10;
                if (this.field[cell] == 0) {
                    this.find_empty_cells(cell);
                }
            }
        }

    }

    public void paint(Graphics g) {
        int cell = false;
        int uncover = 0;

        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                int cell = this.field[i * this.cols + j];
                if (this.inGame && cell == 9) {
                    this.inGame = false;
                }

                if (!this.inGame) {
                    if (cell == 19) {
                        cell = 9;
                    } else if (cell == 29) {
                        cell = 11;
                    } else if (cell > 19) {
                        cell = 12;
                    } else if (cell > 9) {
                        cell = 10;
                    }
                } else if (cell > 19) {
                    cell = 11;
                } else if (cell > 9) {
                    cell = 10;
                    ++uncover;
                }

                g.drawImage(this.img[cell], j * 15, i * 15, this);
            }
        }

        if (uncover == 0 && this.inGame) {
            this.inGame = false;
            this.statusbar.setText("Game won");
        } else if (!this.inGame) {
            this.statusbar.setText("Game lost");
        }

    }

    class MinesAdapter extends MouseAdapter {
        MinesAdapter() {
        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / 15;
            int cRow = y / 15;
            boolean rep = false;
            if (!Board.this.inGame) {
                Board.this.newGame();
                Board.this.repaint();
            }

            if (x < Board.this.cols * 15 && y < Board.this.rows * 15) {
                int[] var10000;
                int var10001;
                if (e.getButton() == 3) {
                    if (Board.this.field[cRow * Board.this.cols + cCol] > 9) {
                        rep = true;
                        if (Board.this.field[cRow * Board.this.cols + cCol] <= 19) {
                            if (Board.this.mines_left > 0) {
                                var10000 = Board.this.field;
                                var10001 = cRow * Board.this.cols + cCol;
                                var10000[var10001] += 10;
                                Board.this.mines_left--;
                                Board.this.statusbar.setText(Integer.toString(Board.this.mines_left));
                            } else {
                                Board.this.statusbar.setText("No marks left");
                            }
                        } else {
                            var10000 = Board.this.field;
                            var10001 = cRow * Board.this.cols + cCol;
                            var10000[var10001] -= 10;
                            Board.this.mines_left++;
                            Board.this.statusbar.setText(Integer.toString(Board.this.mines_left));
                        }
                    }
                } else {
                    if (Board.this.field[cRow * Board.this.cols + cCol] > 19) {
                        return;
                    }

                    if (Board.this.field[cRow * Board.this.cols + cCol] > 9 && Board.this.field[cRow * Board.this.cols + cCol] < 29) {
                        var10000 = Board.this.field;
                        var10001 = cRow * Board.this.cols + cCol;
                        var10000[var10001] -= 10;
                        rep = true;
                        if (Board.this.field[cRow * Board.this.cols + cCol] == 9) {
                            Board.this.inGame = false;
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
