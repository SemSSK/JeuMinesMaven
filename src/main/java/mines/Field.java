package mines;

import java.util.Random;

/**
 * Used to instanciate the logic of a mine field
 */
public class Field {
  private int mines = 40;
  private int rows = 16;
  private int cols = 16;
  private int allCells = rows * cols;
  private Cell[] cells = new Cell[allCells];

  private Random random = new Random();

  /**
   * default Constructor
   * sets all cells to empty
   */
  public Field() {
    for (int i = 0; i < allCells; i++) {
      cells[i] = new Cell();
    }
  }

  /**
   * @return a random empty cell position
   */
  private int getRandomPosition() {
    int position;
    do {
      position = random.nextInt(allCells);
    } while (cells[position].isCoveredMineCell());
    return position;
  }

  /**
   * resets the state of all the cells in the field
   */
  public void reset() {
    for (int i = 0; i < allCells; i++) {
      cells[i] = new Cell();
    }
  }

  /**
   * covers all the cells
   */
  private void coverCells() {
    for (int i = 0; i < allCells; i++)
      cells[i].coverCell();
  }

  /**
   * initializes all the cells to a playable state
   * puts the mines
   * counts the neighbors
   */
  public void setupField() {
    coverCells();

    for (int i = 0; i < mines; i++) {
      int position = getRandomPosition();

      int currentCol = position % cols;
      int currentRow = (position - currentCol) / cols;
      cells[position].coverMineCell();

      int startX = Math.max(currentCol - 1, 0);
      int endX = Math.min(currentCol + 1, cols - 1);
      int startY = Math.max(currentRow - 1, 0);
      int endY = Math.min(currentRow + 1, rows - 1);

      for (int x = startX; x <= endX; x++) {
        for (int y = startY; y <= endY; y++) {
          int cell = y * cols + x;
          if (cell != position && !cells[cell].isCoveredMineCell()) {
            cells[cell].incrementCellNumber();
          }
        }
      }
    }
  }

  /**
   * Recursive function that uncovers all the empty neighboring cells
   * @param postion
   */
  public void findEmptyCells(int postion) {
    int currentCol = postion % cols;
    int currentRow = (postion - currentCol) / cols;
    int startX = Math.max(currentCol - 1, 0);
    int endX = Math.min(currentCol + 1, cols - 1);
    int startY = Math.max(currentRow - 1, 0);
    int endY = Math.min(currentRow + 1, rows - 1);

    for (int x = startX; x <= endX; x++) {
      for (int y = startY; y <= endY; y++) {
        int cell = y * cols + x;
        if (cells[cell].isCoveredCell() && !cells[cell].isMarkedCell()) {
          cells[cell].unCoverCell();
          if (cells[cell].isEmptyCell()) {
            findEmptyCells(cell);
          }
        }
      }
    }
  }

  /**
   *
   * @return the number of covered cells left in the game
   */
  public int getNCoveredCells() {
    int nCoveredMines = 0;
    for (int i = 0; i < allCells; i++) {
      if (cells[i].isCoveredMineCell()) {
        nCoveredMines++;
      }
    }
    return nCoveredMines;
  }

  /**
   *
   * @param position
   * @return a cell object on that position
   */
  public Cell getCell(int position) {
    return cells[position];
  }

  /**
   *
   * @return number of mines
   */
  public int getMines() {
    return mines;
  }

  /**
   *
   * @return number of cells
   */
  public int getAllCells() {
    return allCells;
  }

  /**
   *
   * @return number of rows
   */
  public int getRows() {
    return rows;
  }

  /**
   *
   * @return number of columns
   */
  public int getCols() {
    return cols;
  }
}
