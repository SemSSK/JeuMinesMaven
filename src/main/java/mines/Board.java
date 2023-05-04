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

    private GameStates gameState = GameStates.IN_GAME;
    private transient Image[] img;
    private int mines = 40;
    private int rows = 16;
    private int cols = 16;
    private int allCells = rows * cols;
    private Cell[] field = new Cell[allCells];
    private int marksLeft = mines;
    private JLabel statusbar;

    // declaring random one type and reusing it
    private Random random = new Random();

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;

        for (int i = 0; i < allCells; i++) {
            field[i] = new Cell();
        }

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
        field = new Cell[allCells];
        for (int i = 0; i < allCells; i++) {
            field[i] = new Cell();
        }
        marksLeft = mines;
        newGame();
        repaint();
    }

    private int getRandomPosition() {
        int position;
        do {
            position = random.nextInt(0, allCells);
        } while (field[position].isCoveredMineCell());
        return position;
    }

    private void coverCells() {
        for (int i = 0; i < allCells; i++)
            field[i].coverCell();
    }

    public void newGame() {

        coverCells();

        for (int i = 0; i < mines; i++) {
            int position = getRandomPosition();

            int currentCol = position % cols;
            int currentRow = (position - currentCol) / cols;
            field[position].coverMineCell();

            int startX = Math.max(currentCol - 1, 0);
            int endX = Math.min(currentCol + 1, cols - 1);
            int startY = Math.max(currentRow - 1, 0);
            int endY = Math.min(currentRow + 1, rows - 1);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    int cell = y * cols + x;
                    if (cell != position && !field[cell].isCoveredMineCell()) {
                        field[cell].incrementCellNumber();
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
                if (field[cell].isCoveredCell()) {
                    field[cell].unCoverCell();
                    if (field[cell].isEmptyCell()) {
                        findEmptyCells(cell);
                    }
                }
            }
        }
    }

    public void updateGameState() {
        int nCoveredMines = 0;
        for (int i = 0; i < allCells; i++) {
            if (field[i].isCoveredMineCell()) {
                nCoveredMines++;
            }
        }
        if (nCoveredMines == 0)
            gameState = GameStates.WON;
    }

    @Override
    public void paint(Graphics g) {

        if (marksLeft > 0)
            statusbar.setText(Integer.toString(marksLeft));
        else
            statusbar.setText("No marks left");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                Cell cell = field[(i * cols) + j];

                g.drawImage(img[cell.getCellStateToDraw(gameState)], (j * CELL_SIZE),
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
            int newMarksLeft = field[position].toggleCellMark(marksLeft);
            boolean redraw = newMarksLeft != marksLeft;
            marksLeft = newMarksLeft;
            return redraw;
        }

        private boolean manageLeftClickCase(int position) {
            if ((field[position].isCoveredCell()) &&
                    (!field[position].isMarkedCell())) {

                field[position].unCoverCell();
                if (field[position].isMineCell())
                    gameState = GameStates.LOST;
                if (field[position].isEmptyCell())
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
