package mines;

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
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private boolean inGame = true;
    private Image[] img;
    private int mines = 40;
    private int rows = 16;
    private int cols = 16;
    private int all_cells = rows * cols;
    private int[] field = new int[all_cells];
    private int mines_left = mines;
    private JLabel statusbar;

    // declaring random one type and reusing it
    private Random random = new Random();

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] = new ImageIcon("images/" + (i)
                    + ".gif").getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new Board.MinesAdapter());
        newGame();
    }

    public void Restart() {
        inGame = true;
        mines = 40;
        rows = 16;
        cols = 16;
        all_cells = rows * cols;
        field = new int[all_cells];
        mines_left = mines;
        newGame();
        repaint();
    }

    public void newGame() {

        for (int i = 0; i < all_cells; i++)
            field[i] = COVER_FOR_CELL;

        statusbar.setText(Integer.toString(mines_left));

        for (int i = 0; i < mines; i++) {

            int position = (int) (all_cells * random.nextDouble());

            while ((field[position] == COVERED_MINE_CELL))
                position = (int) (all_cells * random.nextDouble());

            if ((position < all_cells)) {

                int current_col = position % cols;
                int current_row = (position - current_col) / cols;
                field[position] = COVERED_MINE_CELL;

                int start_x = Math.max(current_col - 1, 0);
                int end_x = Math.min(current_col + 1, cols - 1);
                int start_y = Math.max(current_row - 1, 0);
                int end_y = Math.min(current_row + 1, rows - 1);

                for (int x = start_x; x <= end_x; x++) {
                    for (int y = start_y; y <= end_y; y++) {
                        int cell = y * cols + x;
                        if (cell != position && field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    public void find_empty_cells(int j) {
        int current_col = j % cols;
        int current_row = (j - current_col) / cols;
        int start_x = Math.max(current_col - 1, 0);
        int end_x = Math.min(current_col + 1, cols - 1);
        int start_y = Math.max(current_row - 1, 0);
        int end_y = Math.min(current_row + 1, rows - 1);

        for (int x = start_x; x <= end_x; x++) {
            for (int y = start_y; y <= end_y; y++) {
                int cell = y * cols + x;
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {

        int cell = 0;
        int uncover = 0;

        if (mines_left > 0)
            statusbar.setText(Integer.toString(mines_left));
        else
            statusbar.setText("No marks left");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                cell = field[(i * cols) + j];

                if (inGame && cell == MINE_CELL)
                    inGame = false;

                if (!inGame) {
                    if (cell == COVERED_MINE_CELL)
                        cell = DRAW_MINE;
                    else if (cell == MARKED_MINE_CELL)
                        cell = DRAW_MARK;
                    else if (cell > COVERED_MINE_CELL)
                        cell = DRAW_WRONG_MARK;
                    else if (cell > MINE_CELL)
                        cell = DRAW_COVER;
                } else {
                    if (cell > COVERED_MINE_CELL)
                        cell = DRAW_MARK;
                    else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame)
            statusbar.setText("Game lost");
    }

    class MinesAdapter extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;

            if (!inGame) {
                Restart();
            } else if ((cCol < cols) && (cRow < rows)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * cols) + cCol] > MINE_CELL) {
                        rep = true;

                        if (field[(cRow * cols) + cCol] <= COVERED_MINE_CELL) {
                            if (mines_left > 0) {
                                field[(cRow * cols) + cCol] += MARK_FOR_CELL;
                                mines_left--;
                            }
                        } else {
                            field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
                            mines_left++;
                        }
                    }

                } else {

                    if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL) {
                        return;
                    }

                    if ((field[(cRow * cols) + cCol] > MINE_CELL) &&
                            (field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
                        rep = true;

                        if (field[(cRow * cols) + cCol] == MINE_CELL)
                            inGame = false;
                        if (field[(cRow * cols) + cCol] == EMPTY_CELL)
                            find_empty_cells((cRow * cols) + cCol);
                    }
                }

                if (rep)
                    repaint();

            }
        }
    }
}
