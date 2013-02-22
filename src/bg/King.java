package bg;

import cs213.chess.android.AndroidChessActivity;

/**
 * The King chess piece Object
 * @author Hua Yang
 */
public class King {

	private char not_capturing;
	private char left_or_right;
	int king_row;
	int king_column;
	boolean moved;
	
	/**
	 * Constructor for the King object
	 * @param row
	 * @param column
	 */
	public King(int row, int column) {
		king_row = row;
		king_column = column;
		moved = false;
		not_capturing = 'n';
		left_or_right = 'n';
	}
	
	/**
	 * Validates the movements of a King
	 * @param board
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @return false if the move is invalid, true otherwise
	 */
	public boolean check_king(Block[][] board, int row1, int column1, int row2, int column2, boolean checking) {
		int row_diff = Math.abs(row1 - row2);
		int col_diff = Math.abs(column1 - column2);
		boolean valid = false;
		// if the two pieces are the same color
		if (board[row2][column2].current_piece != null && board[row1][column1].current_piece.charAt(0) == board[row2][column2].current_piece.charAt(0)) {
			return valid;
		}
		// check moves
		if (row_diff == 0 && col_diff == 2) { // if castling
			valid = check_castling(board, row1, column1, row2, column2);
		} else if (row_diff == 0 && col_diff == 1) { // moving 1 space left/right
			valid = true;
		} else if (row_diff == 1 && col_diff == 0) { // moving 1 space up/down
			valid = true;
		} else if (row_diff == 1 && col_diff == 1) { // moving 1 space diagonally
			valid = true;
		}
		// if valid
		if (valid && !(checking)) {
			if (!(moved)) { // if moved, mark it
				moved = true;
			}
		}
		return valid;
	}
	
	/**
	 * Checks if castling is possible, if so, mark it
	 * @param board
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @return false if the conditions are not met, true otherwise
	 */
	public boolean check_castling(Block[][] board, int row1, int column1, int row2, int column2) {
		int start = -1, end = -1;
		boolean valid = false;
		boolean castling = false;
		boolean rook = false;
		
		// moving right
		if (column2 > column1) {
			start = column1;
			end = column1+3;
			rook = rook_status(board, board[row1][column1].current_piece.charAt(0), 'r');
		} else { // moving left
			start = column2;
			end = column2+4;
			rook = rook_status(board, board[row1][column1].current_piece.charAt(0), 'l');
		}
		// checks if player have already activated castling
		if (board[row1][column1].current_piece.charAt(0) == 'w') {
			castling = Control.white_castle;
		} else {
			castling = Control.black_castle;
		}
		// checks if the king and rook have been moved
		if (!(castling) && !(moved) && !(rook)) {
			for (int i = start+1; i < end; i++) {
				System.out.println("CHECKING CASTLING: " + row1 + " " + i + " ending at: " + end);
				if (!(valid = Control.check_space(board[row1][i], not_capturing))) {
					break;
				}
			}
		}
		// if castling is valid, mark it
		if (valid) {
			if (board[row1][column1].current_piece.charAt(0) == 'w') {
				Control.white_castle = true;
				update_castling(board, 'w');
			} else {
				Control.black_castle = true;
				update_castling(board, 'b');
			}
			AndroidChessActivity.castling = true;
		}
		return valid;
	}
	
	/**
	 * Gets the move status of the Rook involved in the castling
	 * @param board
	 * @param owner
	 * @param left_right
	 * @return the boolean Rook movement status
	 */
	public boolean rook_status(Block[][] board, char owner, char left_right) {
		int row = -1, column = -1;
		if (owner == 'w') {
			row = 0;
		} else {
			row = 7;
		}
		
		if (left_right == 'l') {
			column = 0;
			left_or_right = 'l';
		} else {
			column = 7;
			left_or_right = 'r';
		}
		return ((Rook) board[row][column].piece_object).move_status();
	}
	
	/**
	 * Moves the rook if a castling was made
	 * @param board
	 */
	public void update_castling(Block[][] board, char player) {
		if (player == 'w') {
			if (left_or_right == 'l') {
				Control.valid_move(board, "wR", 0, 0, 0, 3, 'Q');
			} else {
				Control.valid_move(board, "wR", 0, 7, 0, 5, 'Q');
			}
		} else {
			if (left_or_right == 'l') {
				Control.valid_move(board, "bR", 7, 0, 7, 3, 'Q');
			} else {
				Control.valid_move(board, "bR", 7, 7, 7, 5, 'Q');
			}
		}
	}
	
	/**
	 * Prints out all valid moves for the king at the given position
	 * @param board
	 * @param row
	 * @param column
	 * @return false if no possible moves, true otherwise
	 */
	public boolean show_moves(Block[][] board, int row, int column, boolean checking) {
		boolean possible = false;
		if (!(checking)) {
			System.out.println("Valid moves:");
		}
		
		if (!(moved)) { // check castling
			if (Chess.current_player.equals("White")) {
				if (check_king(board, row, column, 0, 6, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 0, 6, checking);
				}
				if (check_king(board, row, column, 0, 2, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 0, 2, checking);
				}
			} else {
				if (check_king(board, row, column, 7, 6, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 7, 6, checking);
				}
				if (check_king(board, row, column, 7, 2, true) && possible_check(board, row, column)) {
					possible = true;
					Control.print_move(row, column, 7, 2, checking);
				}
			}
		}
		
		if (row+1 <= 7 && check_king(board, row, column, row+1, column, true) && possible_check(board, row+1, column)) { // up
			possible = true;
			Control.print_move(row, column, row+1, column, checking);
		}
		if (row-1 >= 0 && check_king(board, row, column, row-1, column, true) && possible_check(board, row-1, column)) { // down
			possible = true;
			Control.print_move(row, column, row-1, column, checking);
		}
		if (column+1 <= 7 && check_king(board, row, column, row, column+1, true) && possible_check(board, row, column+1)) { // right
			possible = true;
			Control.print_move(row, column, row, column+1, checking);
		}
		if (column-1 >= 0 && check_king(board, row, column, row, column-1, true) && possible_check(board, row, column-1)) { // left
			possible = true;
			Control.print_move(row, column, row, column-1, checking);
		}
		if (column+1 <= 7 && row+1 <=7 && check_king(board, row, column, row+1, column+1, true) && possible_check(board, row+1, column+1)) { // up right
			possible = true;
			Control.print_move(row, column, row+1, column+1, checking);
		}
		if (column+1 <= 7 && row-1 >= 0 && check_king(board, row, column, row-1, column+1, true) && possible_check(board, row-1, column+1)) { // down right
			possible = true;
			Control.print_move(row, column, row-1, column+1, checking);
		}
		if (column-1 >= 0 && row+1 <= 7 && check_king(board, row, column, row+1, column-1, true) && possible_check(board, row+1, column-1)) { // up left
			possible = true;
			Control.print_move(row, column, row+1, column-1, checking);
		}
		if (column-1 >= 0 && row-1 >= 0 && check_king(board, row, column, row-1, column-1, true) && possible_check(board, row-1, column-1)) { // down left
			possible = true;
			Control.print_move(row, column, row-1, column-1, checking);
		}
		if (!(checking) && !(possible)) { // if no possible moves
			System.out.println("NONE!");
		}
		return possible;
	}
	
	/**
	 * Checks if any opponent pieces can check the king
	 * @param board
	 * @param row
	 * @param column
	 * @return false if conditions are not met, true otherwise
	 */
	public boolean possible_check(Block[][] board, int row, int column) {
		char opponent = 'n';
		boolean valid = true;
		if (Chess.next_player.equals("White")) {
			opponent = 'w';
		} else {
			opponent = 'b';
		}
		
		for (int i = board.length-1; i >= 0; i--) {
			for (int n = 0; n < board[i].length; n++) { // if possible of checking the king
				if (board[i][n].current_piece != null && board[i][n].current_piece.charAt(0) == opponent &&
					Control.check_piece(board, board[i][n].current_piece, i, n, row, column, true))  {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}
}
