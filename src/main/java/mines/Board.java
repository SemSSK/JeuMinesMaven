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

    private GameStates gameState = GameStates.InGame;
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
        gameState = GameStates.InGame;
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

    public void updateGameState() {
        int n_covered_mines = 0;
        for (int i = 0; i < all_cells; i++) {
            if (field[i] == COVERED_MINE_CELL) {
                n_covered_mines++;
            }
        }
        if (n_covered_mines == 0)
            gameState = GameStates.Won;
    }

    public int getCellStateToDraw(int cell) {
        if (gameState != GameStates.InGame) {
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
            }
        }
        return cell;
    }

    public void paint(Graphics g) {

        if (mines_left > 0)
            statusbar.setText(Integer.toString(mines_left));
        else
            statusbar.setText("No marks left");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int cell = field[(i * cols) + j];

                g.drawImage(img[getCellStateToDraw(cell)], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (gameState == GameStates.Won) {
            statusbar.setText("Game won");
        } else if (gameState == GameStates.Lost)
            statusbar.setText("Game lost");
    }

    class MinesAdapter extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            int position = (cRow * cols) + cCol;

            boolean rep = false;

            if (gameState != GameStates.InGame) {
                Restart();
            } else if (position <= all_cells) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[position] > MINE_CELL) {
                        rep = true;
                        if (field[position] <= COVERED_MINE_CELL && mines_left > 0) {
                            field[position] += MARK_FOR_CELL;
                            mines_left--;
                        } else {
                            field[position] -= MARK_FOR_CELL;
                            mines_left++;
                        }
                    }

                } else {

                    if ((field[position] >= COVER_FOR_CELL) &&
                            (field[position] < COVER_FOR_CELL + MARK_FOR_CELL)) {

                        field[position] -= COVER_FOR_CELL;
                        rep = true;

                        if (field[position] == MINE_CELL)
                            gameState = GameStates.Lost;
                        if (field[position] == EMPTY_CELL)
                            find_empty_cells(position);
                    }
                }

                if (rep)
                    repaint();

            }
        }
    }
}
