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

    private static final int NUM_IMAGES = 13;
    private static final int CELL_SIZE = 15;

    private static final int COVER_FOR_CELL = 10;
    private static final int MARK_FOR_CELL = 10;
    private static final int EMPTY_CELL = 0;
    private static final int MINE_CELL = 9;
    private static final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private static final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private static final int DRAW_MINE = 9;
    private static final int DRAW_COVER = 10;
    private static final int DRAW_MARK = 11;
    private static final int DRAW_WRONG_MARK = 12;

    private GameStates gameState = GameStates.IN_GAME;
    private transient Image[] img;
    private int mines = 40;
    private int rows = 16;
    private int cols = 16;
    private int allCells = rows * cols;
    private int[] field = new int[allCells];
    private int minesLeft = mines;
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

    public void restart() {
        gameState = GameStates.IN_GAME;
        mines = 40;
        rows = 16;
        cols = 16;
        allCells = rows * cols;
        field = new int[allCells];
        minesLeft = mines;
        newGame();
        repaint();
    }

    private int getRandomPosition() {
        int position;
        do {
            position = random.nextInt(0, allCells);
        } while ((field[position] == COVERED_MINE_CELL));
        return position;
    }

    private void coverCells() {
        for (int i = 0; i < allCells; i++)
            field[i] = COVER_FOR_CELL;
    }

    public void newGame() {

        coverCells();

        for (int i = 0; i < mines; i++) {
            int position = getRandomPosition();

            int currentCol = position % cols;
            int currentRow = (position - currentCol) / cols;
            field[position] = COVERED_MINE_CELL;

            int startX = Math.max(currentCol - 1, 0);
            int endX = Math.min(currentCol + 1, cols - 1);
            int startY = Math.max(currentRow - 1, 0);
            int endY = Math.min(currentRow + 1, rows - 1);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    int cell = y * cols + x;
                    if (cell != position && field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }
            }
        }
    }

    public void findEmptyCells(int j) {
        int currentCol = j % cols;
        int currentRow = (j - currentCol) / cols;
        int startX = Math.max(currentCol - 1, 0);
        int endX = Math.min(currentCol + 1, cols - 1);
        int startY = Math.max(currentRow - 1, 0);
        int endY = Math.min(currentRow + 1, rows - 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int cell = y * cols + x;
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        findEmptyCells(cell);
                    }
                }
            }
        }
    }

    public void updateGameState() {
        int nCoveredMines = 0;
        for (int i = 0; i < allCells; i++) {
            if (field[i] == COVERED_MINE_CELL) {
                nCoveredMines++;
            }
        }
        if (nCoveredMines == 0)
            gameState = GameStates.WON;
    }

    public int getCellStateToDraw(int cell) {
        if (gameState != GameStates.IN_GAME) {
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

    @Override
    public void paint(Graphics g) {

        if (minesLeft > 0)
            statusbar.setText(Integer.toString(minesLeft));
        else
            statusbar.setText("No marks left");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int cell = field[(i * cols) + j];

                g.drawImage(img[getCellStateToDraw(cell)], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        updateGameState();

        if (gameState == GameStates.WON) {
            statusbar.setText("Game won");
        } else if (gameState == GameStates.LOST)
            statusbar.setText("Game lost");
    }

    class MinesAdapter extends MouseAdapter {

        private boolean manageRightClickCase(int position) {
            if (field[position] > MINE_CELL) {
                if (field[position] <= COVERED_MINE_CELL && minesLeft > 0) {
                    field[position] += MARK_FOR_CELL;
                    minesLeft--;
                } else {
                    field[position] -= MARK_FOR_CELL;
                    minesLeft++;
                }
                return true;
            }
            return false;
        }

        private boolean manageLeftClickCase(int position) {
            if ((field[position] >= COVER_FOR_CELL) &&
                    (field[position] < COVER_FOR_CELL + MARK_FOR_CELL)) {

                field[position] -= COVER_FOR_CELL;
                if (field[position] == MINE_CELL)
                    gameState = GameStates.LOST;
                if (field[position] == EMPTY_CELL)
                    findEmptyCells(position);

                return true;
            }
            return false;
        }

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            int position = (cRow * cols) + cCol;

            boolean rep = false;

            if (gameState != GameStates.IN_GAME) {
                restart();
            } else if (cCol < cols && cRow < rows) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    rep = manageRightClickCase(position);
                } else {
                    rep = manageLeftClickCase(position);
                }

                if (rep)
                    repaint();

            }
        }
    }
}
