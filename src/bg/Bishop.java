package bg;

/**
 * The Bishop chess piece Object
 * @author Hua Yang
 */
public class Bishop {

	int bishop_row;
	int bishop_column;
	private char not_capturing;
	
	/**
	 * Constructor for the Bishop object
	 * @param row
	 * @param column
	 */
	public Bishop (int row, int column) {
		bishop_row = row;
		bishop_column = column;
		not_capturing = 'n';
	}
	
	/**
	 * Validates the movements of a Bishop
	 * @param board
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @return false if the move is invalid, true otherwise
	 */
	public boolean check_bishop(Block[][] board, int row1, int column1, int row2, int column2) {
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		int start_row = -1, end_row = -1, start_col = -1;
		boolean upward_slope = false;
		boolean valid = false;
		
		// checks the direction that it is moving
		if (row1 != row2 && column1 != column2 && row_diff == col_diff) {
			if (row1 > row2 && column1 > column2) { // from upper right to lower left : '/' shape
				start_row = row2;
				end_row = row1;
				start_col = column2;
				upward_slope = true;
			} else if (row1 > row2 && column1 < column2) { // from upper left to lower right : '\' shape
				start_row = row1;
				end_row = row2;
				start_col = column1;
			} else if (row1 < row2 && column1 < column2) { // from lower left to upper right : '/' shape
				start_row = row1;
				end_row = row2;
				start_col = column1;
				upward_slope = true;
			} else { // from lower right to upper left : '\' shape
				start_row = row2;
				end_row = row1;
				start_col = column2;
			}
			valid = true;
		}
		// checks all the spaces before the last space
		if (valid) {
			if (row_diff == 1 && col_diff == 1) { // when moving 1 space diagonally
				// does nothing
			} else if (upward_slope) { // calculates '/' shape movement
				for (int i = start_row+1, n = start_col+1; i < end_row; i++, n++) {
					valid = Control.check_space(board[i][n], not_capturing);
					if (!(valid)) {	break;	}
				}
			} else { // calculates '\' shape movement
				for (int i = start_row-1, n = start_col+1; i > end_row; i--, n++) {
					valid = Control.check_space(board[i][n], not_capturing);
					if (!(valid)) {	break;	}
				}
			}
		}
		// checks the last space
		if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
			valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
		}
		return valid;
	}
	
	/**
	 * Prints out all valid moves for the bishop at the given position
	 * @param board
	 * @param row
	 * @param column
	 * @param queen
	 * @return false if no possible moves, true otherwise
	 */
	public boolean show_moves(Block[][] board, int row, int column, boolean queen) {
		boolean possible = false;
		if (!(queen)) {
			System.out.println("Valid moves:");
		}
		
		for (int i = row+1, n = column+1; i <= 7 && n <= 7; i++, n++) { // upper right
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row-1, n = column+1; i >= 0 && n <= 7; i--, n++) { // lower right
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row+1, n = column-1; i <= 7 && n >= 0; i++, n--) { // upper left
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		for (int i = row-1, n = column-1; i >= 0 && n >= 0; i--, n--) { // lower left
			if (check_bishop(board, row, column, i, n)) {
				Control.print_move(row, column, i, n, false);
				possible = true;
			}
		}
		
		if (!(queen) && !(possible)) {
			System.out.println("NONE!");
		}
		return possible;
	}
}
