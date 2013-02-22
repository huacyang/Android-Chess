package bg;

import cs213.chess.android.AndroidChessActivity;

/**
 * The Pawn chess piece Object
 * @author Hua Yang
 */
public class Pawn {

	private char not_capturing;
	private boolean moved;
	int pawn_row;
	int pawn_column;
	
	/**
	 * Constructor for the Pawn object
	 * @param row
	 * @param column
	 */
	public Pawn(int row, int column) {
		pawn_row = row;
		pawn_column = column;
		moved = false;
		not_capturing = 'n';
	}
	
	/**
	 * Validates the movements of the Pawn
	 * @param board
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @param checking
	 * @return false if the move is invalid, true otherwise
	 */
	public boolean check_pawn(Block[][] board, int row1, int column1, int row2, int column2, boolean checking) {
		int one = 1, two = 2;
		int one_space = -1, two_space = -1;
		char owner = board[row1][column1].current_piece.charAt(0);
		boolean valid = false;
		// calculates the distance in rows
		if (owner == 'b') {
			one_space = row1-one;
			two_space = row1-two;
		} else {
			one_space = row1+one;
			two_space = row1+two;
		}
		// when attempting to move 1 space
		if (row2 == one_space) {
			if (column1 == column2 && board[row2][column2].current_piece == null) { // moving vertically
				valid = true;
			} else if (column1 == column2-one || column1 == column2+one) { // moving diagonally
				if (Control.last_column == column2 && en_passant(board, row1, column1)) { // checks if en passant is possible
					valid = true;
					if (!(checking)) { // removes the pawn taken during en passant
						remove_pawn(board);
						AndroidChessActivity.en_passant = true;
					}
				} else { // checks if colors are the same
					valid = Control.check_space(board[row2][column2], owner);
				}
			}
		} else if (!(moved) && row2 == two_space && column1 == column2) { // when attempting to move 2 spaces
			if (Control.check_space(board[one_space][column1], not_capturing)
					&& Control.check_space(board[two_space][column1], not_capturing)) {
				valid = true;
				if (!(checking)) { // sets the respected en passant to true
					if (Chess.current_player.equals("White")) {
						Control.white_en_passant = true;
					} else {
						Control.black_en_passant = true;
					}
				}
			}
		}
		// if piece was moved, mark it
		if (!(checking) && !(moved) && valid) {
			moved = true;
		}
		return valid;
	}
	
	/**
	 * Checks if an en passant is possible, by using the current position and the previous position
	 * @param board
	 * @param row
	 * @param column
	 * @return false if conditions for en passant are not met, true otherwise
	 */
	private boolean en_passant(Block[][] board, int row, int column) {
		boolean valid = false;
		int row_diff = Math.abs(row - Control.last_row);
		int col_diff = Math.abs(column - Control.last_column);
		if (row_diff == 0 && col_diff == 1 && row_diff == 0 && board[Control.last_row][Control.last_column].current_piece.charAt(1) == 'p') {
			if (Chess.current_player.equals("White") && Control.black_en_passant) {
				valid = true;
			} else if (Chess.current_player.equals("Black") && Control.white_en_passant) {
				valid = true;
			}
		}
		return valid;
	}
	

	/**
	 * Removes the pawn that was taken out during en passant
	 * @param board
	 */
	public static void remove_pawn(Block[][] board) {
		board[Control.last_row][Control.last_column].current_piece = null;
		board[Control.last_row][Control.last_column].piece_object = null;
	}
	
	/**
	 * Prints out all valid moves for the pawn at the given position
	 * @param board
	 * @param row1
	 * @param column1
	 */
	public void show_moves(Block[][] board, int row, int column) {
		int row_one = -1, row_two = -1;
		int col_plus_one = column+1;
		int col_min_one = column-1;
		boolean possible = false;
		System.out.println("Valid moves:");
		// goes up or down depending on the player
		if (Chess.current_player.equals("White")) {
			row_one = row + 1;
			row_two = row + 2;
		} else {
			row_one = row - 1;
			row_two = row - 2;
		}
		
		if (row_one < 8 && row_one > -1 && check_pawn(board, row, column, row_one, column, true)) { // one space ahead
			Control.print_move(row, column, row_one, column, false);
			possible = true;
		}
		if (row_two < 8 && row_two > -1 && check_pawn(board, row, column, row_two, column, true)) { // two space ahead
			Control.print_move(row, column, row_two, column, false);
			possible = true;
		}
		if (col_plus_one < 8 && col_plus_one > -1 && check_pawn(board, row, column, row_one, col_plus_one, true)) { // up/down right
			Control.print_move(row, column, row_one, col_plus_one, false);
			possible = true;
		}
		if (col_min_one < 8 && col_plus_one > -1 && check_pawn(board, row, column, row_one, col_min_one, true)) { // up/down left
			Control.print_move(row, column, row_one, col_min_one, false);
			possible = true;
		}
		if (!(possible)) {
			System.out.println("NONE!");
		}
	}
}
