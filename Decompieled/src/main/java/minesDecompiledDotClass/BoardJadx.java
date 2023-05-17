package minesDecompiledDotClass;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardJadx extends JPanel {
    private static final long serialVersionUID = 6195235521361212179L;
    private final int CELL_SIZE = 15;
    private final int COVERED_MINE_CELL = 19;
    private final int COVER_FOR_CELL = 10;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_MINE = 9;
    private final int DRAW_WRONG_MARK = 12;
    private final int EMPTY_CELL = 0;
    private final int MARKED_MINE_CELL = 29;
    private final int MARK_FOR_CELL = 10;
    private final int MINE_CELL = 9;
    private final int NUM_IMAGES = 13;
    private int all_cells;
    /* access modifiers changed from: private */
    public int cols = 16;
    /* access modifiers changed from: private */
    public int[] field;
    private Image[] img;
    /* access modifiers changed from: private */
    public boolean inGame;

    /* renamed from: mines  reason: collision with root package name */
    private int f0mines = 40;
    /* access modifiers changed from: private */
    public int mines_left;
    /* access modifiers changed from: private */
    public int rows = 16;
    /* access modifiers changed from: private */
    public JLabel statusbar;

    static /* synthetic */ int access$408(BoardJadx x0) {
        int i = x0.mines_left;
        x0.mines_left = i + 1;
        return i;
    }

    static /* synthetic */ int access$410(BoardJadx x0) {
        int i = x0.mines_left;
        x0.mines_left = i - 1;
        return i;
    }

    public BoardJadx(JLabel statusbar2) {
        this.statusbar = statusbar2;
        this.img = new Image[13];
        for (int i = 0; i < 13; i++) {
            this.img[i] = new ImageIcon("images/" + i + ".gif").getImage();
        }
        setDoubleBuffered(true);
        addMouseListener(new MinesAdapter(this));
        newGame();
    }

    public void newGame() {
        Random random = new Random();
        this.inGame = true;
        this.mines_left = this.f0mines;
        this.all_cells = this.rows * this.cols;
        this.field = new int[this.all_cells];
        for (int i = 0; i < this.all_cells; i++) {
            this.field[i] = 10;
        }
        this.statusbar.setText(Integer.toString(this.mines_left));
        int i2 = 0;
        while (i2 < this.f0mines) {
            int position = (int) (((double) this.all_cells) * random.nextDouble());
            if (position < this.all_cells && this.field[position] != 19) {
                int current_col = position % this.cols;
                this.field[position] = 19;
                i2++;
                if (current_col > 0) {
                    int cell = (position - 1) - this.cols;
                    if (cell >= 0 && this.field[cell] != 19) {
                        int[] iArr = this.field;
                        iArr[cell] = iArr[cell] + 1;
                    }
                    int cell2 = position - 1;
                    if (cell2 >= 0 && this.field[cell2] != 19) {
                        int[] iArr2 = this.field;
                        iArr2[cell2] = iArr2[cell2] + 1;
                    }
                    int cell3 = (this.cols + position) - 1;
                    if (cell3 < this.all_cells && this.field[cell3] != 19) {
                        int[] iArr3 = this.field;
                        iArr3[cell3] = iArr3[cell3] + 1;
                    }
                }
                int cell4 = position - this.cols;
                if (cell4 >= 0 && this.field[cell4] != 19) {
                    int[] iArr4 = this.field;
                    iArr4[cell4] = iArr4[cell4] + 1;
                }
                int cell5 = position + this.cols;
                if (cell5 < this.all_cells && this.field[cell5] != 19) {
                    int[] iArr5 = this.field;
                    iArr5[cell5] = iArr5[cell5] + 1;
                }
                if (current_col < this.cols - 1) {
                    int cell6 = (position - this.cols) + 1;
                    if (cell6 >= 0 && this.field[cell6] != 19) {
                        int[] iArr6 = this.field;
                        iArr6[cell6] = iArr6[cell6] + 1;
                    }
                    int cell7 = this.cols + position + 1;
                    if (cell7 < this.all_cells && this.field[cell7] != 19) {
                        int[] iArr7 = this.field;
                        iArr7[cell7] = iArr7[cell7] + 1;
                    }
                    int cell8 = position + 1;
                    if (cell8 < this.all_cells && this.field[cell8] != 19) {
                        int[] iArr8 = this.field;
                        iArr8[cell8] = iArr8[cell8] + 1;
                    }
                }
            }
        }
    }

    public void find_empty_cells(int j) {
        int current_col = j % this.cols;
        if (current_col > 0) {
            int cell = (j - this.cols) - 1;
            if (cell >= 0 && this.field[cell] > 9) {
                int[] iArr = this.field;
                iArr[cell] = iArr[cell] - 10;
                if (this.field[cell] == 0) {
                    find_empty_cells(cell);
                }
            }
            int cell2 = j - 1;
            if (cell2 >= 0 && this.field[cell2] > 9) {
                int[] iArr2 = this.field;
                iArr2[cell2] = iArr2[cell2] - 10;
                if (this.field[cell2] == 0) {
                    find_empty_cells(cell2);
                }
            }
            int cell3 = (this.cols + j) - 1;
            if (cell3 < this.all_cells && this.field[cell3] > 9) {
                int[] iArr3 = this.field;
                iArr3[cell3] = iArr3[cell3] - 10;
                if (this.field[cell3] == 0) {
                    find_empty_cells(cell3);
                }
            }
        }
        int cell4 = j - this.cols;
        if (cell4 >= 0 && this.field[cell4] > 9) {
            int[] iArr4 = this.field;
            iArr4[cell4] = iArr4[cell4] - 10;
            if (this.field[cell4] == 0) {
                find_empty_cells(cell4);
            }
        }
        int cell5 = j + this.cols;
        if (cell5 < this.all_cells && this.field[cell5] > 9) {
            int[] iArr5 = this.field;
            iArr5[cell5] = iArr5[cell5] - 10;
            if (this.field[cell5] == 0) {
                find_empty_cells(cell5);
            }
        }
        if (current_col < this.cols - 1) {
            int cell6 = (j - this.cols) + 1;
            if (cell6 >= 0 && this.field[cell6] > 9) {
                int[] iArr6 = this.field;
                iArr6[cell6] = iArr6[cell6] - 10;
                if (this.field[cell6] == 0) {
                    find_empty_cells(cell6);
                }
            }
            int cell7 = this.cols + j + 1;
            if (cell7 < this.all_cells && this.field[cell7] > 9) {
                int[] iArr7 = this.field;
                iArr7[cell7] = iArr7[cell7] - 10;
                if (this.field[cell7] == 0) {
                    find_empty_cells(cell7);
                }
            }
            int cell8 = j + 1;
            if (cell8 < this.all_cells && this.field[cell8] > 9) {
                int[] iArr8 = this.field;
                iArr8[cell8] = iArr8[cell8] - 10;
                if (this.field[cell8] == 0) {
                    find_empty_cells(cell8);
                }
            }
        }
    }

    public void paint(Graphics g) {
        int uncover = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                int cell = this.field[(this.cols * i) + j];
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
                    uncover++;
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
}
