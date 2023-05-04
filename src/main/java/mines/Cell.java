package mines;

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

  public boolean isEmptyCell() {
    return state == EMPTY_CELL;
  }

  public boolean isMineCell() {
    return state == MINE_CELL;
  }

  public boolean isCoveredMineCell() {
    return state == COVERED_MINE_CELL;
  }

  public boolean isCoveredCell() {
    return state >= COVER_FOR_CELL;
  }

  public boolean isMarkedCell() {
    return state >= MARK_FOR_CELL + COVER_FOR_CELL;
  }

  public boolean isMarkedMineCell() {
    return state == MARKED_MINE_CELL;
  }

  public void coverCell() {
    state = COVER_FOR_CELL;
  }

  public void unCoverCell() {
    state -= COVER_FOR_CELL;
  }

  public void coverMineCell() {
    state = COVERED_MINE_CELL;
  }

  private void markCell() {
    state += MARK_FOR_CELL;
  }

  private void unMarkCell() {
    state -= MARK_FOR_CELL;
  }

  public int toggleCellMark(int marksLeft) {
    if (isMarkedCell()) {
      unMarkCell();
      marksLeft++;
    } else if (marksLeft > 0) {
      markCell();
      marksLeft--;
    }
    return marksLeft;
  }

  public void incrementCellNumber() {
    state++;
  }

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
