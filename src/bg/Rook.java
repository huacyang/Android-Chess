package bg;

/**
 * The Rook chess piece Object
 * @author Hua Yang
 */
public class Rook {

	private char not_capturing;
	private boolean moved;
	int rook_row;
	int rook_column;
	
	/**
	 * Constructor for the Rook object
	 * @param row
	 * @param column
	 */
	public Rook(int row, int column) {
		rook_row = row;
		rook_column = column;
		moved = false;
		not_capturing = 'n';
	}
	
	/**
	 * Validates the movement of the Rook
	 * @param board
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @return false if the move is invalid, true otherwise
	 */
	public boolean check_rook(Block[][] board, int row1, int column1, int row2, int column2) {
		int start = 0, end = 0;
		boolean valid = false;
		// when moving horizontally
		if (row1 == row2 && column1 != column2) {
			if (column1 > column2) { // moving left
				start = column2;
				end = column1;
			} else { // moving right
				start = column1;
				end = column2;
			}
			// only 1 space difference
			if (start+1 == end) {
				valid = true;
			} else { // checks all but the last space 
				for (int i = start+1; i < end; i++) {
					valid = Control.check_space(board[row1][i], not_capturing);
					if (!(valid)) {
						break;
					}
				}
			}
			// checks last space
			if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
				valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
			}
		}
		// when moving vertically
		else if (column1 == column2 && row1 != row2) {
			if (row1 > row2) { // moving down
				start = row2;
				end = row1;
			} else { // moving up
				start = row1;
				end = row2;
			}
			// only 1 space difference
			if (start+1 == end) {
				valid = true;
			} else { // checks all but the last space
				for (int i = start+1; i < end; i++) {
					valid = Control.check_space(board[i][column1], not_capturing);
					if (!(valid)) {
						break;
					}
				}
			}
			// checks last space
			if (valid && !(Control.check_space(board[row2][column2], not_capturing))) {
				valid = Control.check_space(board[row1][column1], board[row2][column2].current_piece.charAt(0));
			}
		}
		// if the piece was moved, mark it
		if (!(moved) && valid) {
			moved = true;
		}
		return valid;
	}
	
	/**
	 * Gets the moved status of the rook
	 * @return the boolean of moved
	 */
	public boolean move_status() {
		return moved;
	}
	
	/**
	 * Prints out all valid moves for the rook at the given position
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
		
		for (int i = row+1; i <= 7; i++) { // moving up
			if (check_rook(board, row, column, i, column)) {
				Control.print_move(row, column, i, column, false);
				possible = true;
			}
		}
		for (int i = row-1; i >= 0; i--) { // moving down
			if (check_rook(board, row, column, i, column)) {
				Control.print_move(row, column, i, column, false);
				possible = true;
			}
		}
		for (int i = column+1; i <= 7; i++) { // moving right
			if (check_rook(board, row, column, row, i)) {
				Control.print_move(row, column, row, i, false);
				possible = true;
			}
		}
		for (int i = column-1; i >= 0; i--) { // moving left
			if (check_rook(board, row, column, row, i)) {
				Control.print_move(row, column, row, i, false);
				possible = true;
			}
		}
		
		if (!(queen) && !(possible)) {
			System.out.println("NONE!");
		}
		return possible;
	}
}
