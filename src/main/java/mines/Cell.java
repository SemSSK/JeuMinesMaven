package mines;

/**
 * Decribes the state and behaviour of a Cell in the game
 * the state of a cell is represented by an integer which depending on its value changes
 * the classes using this class do not have know about the operations that are responsible for the changes in the states
 * and only need to interact with the Cell using the public methods it exposes
 */
public class Cell {

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

  private int state;

  /**
   * @return true if the cell is Empty
   * @return false otherwise
   */
  public boolean isEmptyCell() {
    return state == EMPTY_CELL;
  }

  /**
   * @return true if the cell is a mine
   * @return false otherwise
   */
  public boolean isMineCell() {
    return state == MINE_CELL;
  }

  /**
   * @return true if the cell is covering a mine
   * @return false otherwise
   */
  public boolean isCoveredMineCell() {
    return state == COVERED_MINE_CELL;
  }

  /**
   * @return true if the cell is covered
   * @return false otherwise
   */
  public boolean isCoveredCell() {
    return state >= COVER_FOR_CELL;
  }

  /**
   * @return true if the cell is flagged
   * @return false otherwise
   */
  public boolean isMarkedCell() {
    return state >= MARK_FOR_CELL + COVER_FOR_CELL;
  }

  /**
   * @return true if the cell is flagged and contains a mine
   * @return false otherwise
   */
  public boolean isMarkedMineCell() {
    return state == MARKED_MINE_CELL;
  }

  /**
   * Changes the state of a cell to a covered cell
   */
  public void coverCell() {
    state = COVER_FOR_CELL;
  }

  /**
   * Changes the state of a cell to a uncovered cell
   */
  public void unCoverCell() {
    state -= COVER_FOR_CELL;
  }

  /**
   * Changes the state of a cell to a covered cell that contains a mine
   */
  public void coverMineCell() {
    state = COVERED_MINE_CELL;
  }

  /**
   * Changes the state of a cell to a marked cell
   */
  private void markCell() {
    state += MARK_FOR_CELL;
  }

  /**
   * Changes the state of a cell unmarked
   */
  private void unMarkCell() {
      state -= MARK_FOR_CELL;
  }

  /**
   * Changes the state of a cell to unmarked if it was marked and to marked if it was unmarked
   * @return returns the number of marksLeft
   */
  public int toggleCellMark(int marksLeft) {
    if (isMarkedCell()) {
      unMarkCell();
      marksLeft++;
    } else if (isCoveredCell() && marksLeft > 0) {
      markCell();
      marksLeft--;
    }
    return marksLeft;
  }

  /**
   * increments the number of neighbooring cells
   */
  public void incrementCellNumber() {
    state++;
  }

  /**
   * @param gameState
   * @return the index of the image that needs to be displayed
   */
  public int getCellStateToDraw(GameStates gameState) {
    int cell = state;
    if (gameState != GameStates.IN_GAME) {
      if (isCoveredMineCell())
        cell = DRAW_MINE;
      else if (isMarkedMineCell())
        cell = DRAW_MARK;
      else if (isMarkedCell())
        cell = DRAW_WRONG_MARK;
      else if (isCoveredCell())
        cell = DRAW_COVER;
    } else {
      if (isMarkedCell())
        cell = DRAW_MARK;
      else if (isCoveredCell()) {
        cell = DRAW_COVER;
      }
    }
    return cell;
  }
}
