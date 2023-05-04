# Class Board

The Board class extends the JPanel class, and it represents the Minesweeper board. The board consists of cells, some of which contain mines. The objective of the game is to uncover all cells that do not contain mines without detonating any of the cells that do contain mines.

# Attributes

* NUM_IMAGES: A constant integer representing the number of images available for the cells on the board.
* CELL_SIZE: A constant integer representing the size of each cell on the board.
* COVER_FOR_CELL: A constant integer representing the value assigned to a cell that is covered.
* MARK_FOR_CELL: A constant integer representing the value assigned to a cell that has been marked with a flag.
* EMPTY_CELL: A constant integer representing the value assigned to a cell that is not covered and does not contain a mine.
* MINE_CELL: A constant integer representing the value assigned to a cell that contains a mine.
* COVERED_MINE_CELL: A constant integer representing the value assigned to a cell that contains a mine and is covered.
* MARKED_MINE_CELL: A constant integer representing the value assigned to a cell that contains a mine and has been marked with a flag.
* DRAW_MINE: A constant integer representing the index of the image that represents a mine.
* DRAW_COVER: A constant integer representing the index of the image that represents a covered cell.
* DRAW_MARK: A constant integer representing the index of the image that represents a cell that has been marked with a flag.
* DRAW_WRONG_MARK: A constant integer representing the index of the image that represents a cell that has been marked with a flag but does not contain a mine.
* field: An integer array that represents the state of each cell on the board. The value assigned to each cell is determined by the constants described above.
* inGame: A boolean value that represents whether the game is currently in progress.
* mines_left: An integer representing the number of mines that have not been marked with a flag.
* img: An array of Image objects representing the images that can be displayed on the cells.
* mines: An integer representing the number of mines on the board.
* rows: An integer representing the number of rows on the board.
* cols: An integer representing the number of columns on the board.
* all_cells: An integer representing the total number of cells on the board.
* statusbar: A JLabel object representing the status bar at the bottom of the game window.

## Constructor
### public Board(JLabel statusbar)

The constructor creates a new Board object and sets the statusbar field to the specified JLabel object. It initializes the img field by loading the images from the "images" directory. It sets the double buffer of the panel to true, adds a MouseListener to the panel, and calls the newGame() method to start a new game.

## Methods
### public void newGame()

This method starts a new game by initializing the field array to contain only covered cells, setting the inGame flag to true, and setting the number of mines left to the total number of mines. It then randomly places the specified number of mines on the board and updates the values of the cells surrounding the mines to indicate the number of mines in the adjacent cells.
private void find_empty_cells(int j)

This method is called when a cell that does not contain a mine is uncovered. It recursively uncovers all adjacent cells that do not contain mines. The parameter j is the index of the current cell in the

## public void paint

The `paint` method is responsible for painting the Board in the window. The code is divided into three parts: covering the cells with an image that hides the content, displaying the content of a cell according to the value in the field array, and displaying the game status (win/lose).


# MouseAdapter

`MouseAdapter` is a convenience class that implements the `MouseListener` interface. The `MouseListener` interface is used to receive mouse events. This class has empty implementations for all methods defined in the `MouseListener` interface.

The `MinesAdapter` class is an inner class of the `Board` class and is used to handle mouse events. This class overrides the `mousePressed` method to handle left and right mouse clicks. Left-clicking on a covered cell will uncover the cell, and right-clicking on a covered cell will mark the cell with a flag. Left-clicking on an uncovered cell will reveal its content, and right-clicking on an uncovered cell will remove the flag. The method also checks the game status and updates the game accordingly.