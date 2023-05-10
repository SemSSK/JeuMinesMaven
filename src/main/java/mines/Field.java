package mines;

import java.util.Random;

public class Field {
  private int mines = 40;
  private int rows = 16;
  private int cols = 16;
  private int allCells = rows * cols;
  private Cell[] cells = new Cell[allCells];

  private Random random = new Random();

  public Field() {
    for (int i = 0; i < allCells; i++) {
      cells[i] = new Cell();
    }
  }

  private int getRandomPosition() {
    int position;
    do {
      position = random.nextInt(allCells);
    } while (cells[position].isCoveredMineCell());
    return position;
  }

  public void reset() {
    for (int i = 0; i < allCells; i++) {
      cells[i] = new Cell();
    }
  }

  private void coverCells() {
    for (int i = 0; i < allCells; i++)
      cells[i].coverCell();
  }

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
        if (cells[cell].isCoveredCell()) {
          cells[cell].unCoverCell();
          if (cells[cell].isEmptyCell()) {
            findEmptyCells(cell);
          }
        }
      }
    }
  }

  public int getNCoveredCells() {
    int nCoveredMines = 0;
    for (int i = 0; i < allCells; i++) {
      if (cells[i].isCoveredMineCell()) {
        nCoveredMines++;
      }
    }
    return nCoveredMines;
  }

  public Cell getCell(int position) {
    return cells[position];
  }

  public int getMines() {
    return mines;
  }

  public int getAllCells() {
    return allCells;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }
}
