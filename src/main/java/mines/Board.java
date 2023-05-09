package mines;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
    private static final long serialVersionUID = 6195235521361212179L;

    private static final int NUM_IMAGES = 13;
    private static final int CELL_SIZE = 15;

    private GameStates gameState = GameStates.IN_GAME;
    private transient Image[] img;

    private transient Field field = new Field();
    private int marksLeft = field.getMines();
    private JLabel statusbar;

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] = new ImageIcon("images/" + (i)
                    + ".gif").getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new Board.MinesAdapter());
        field.setupField();
    }

    public void restart() {
        gameState = GameStates.IN_GAME;
        marksLeft = field.getMines();
        field.reset();
        field.setupField();
        repaint();
    }

    public void updateGameState() {
        int nCoveredMines = 0;
        for (int i = 0; i < field.getAllCells(); i++) {
            if (field.getCell(i).isCoveredMineCell()) {
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

        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {

                Cell cell = field.getCell((i * field.getCols()) + j);

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
            int newMarksLeft = field.getCell(position).toggleCellMark(marksLeft);
            boolean redraw = newMarksLeft != marksLeft;
            marksLeft = newMarksLeft;
            return redraw;
        }

        private boolean manageLeftClickCase(int position) {
            if ((field.getCell(position).isCoveredCell()) &&
                    (!field.getCell(position).isMarkedCell())) {

                field.getCell(position).unCoverCell();
                if (field.getCell(position).isMineCell())
                    gameState = GameStates.LOST;
                else if (field.getCell(position).isEmptyCell())
                    field.findEmptyCells(position);

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

            int position = (cRow * field.getCols()) + cCol;

            boolean rep = false;

            if (gameState != GameStates.IN_GAME) {
                restart();
            } else if (cCol < field.getCols() && cRow < field.getRows()) {

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
