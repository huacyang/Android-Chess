package bg;

import cs213.chess.android.AndroidChessActivity;

/**
 * The Chess control center
 * @author Hua Yang
 */
public class Control {
	
	// keeps track of en_passant
	static int en_passant_turn;
	static boolean white_en_passant;
	static boolean black_en_passant;
	// keeps track of the kings
	static int white_king_row = 0;
	static int white_king_col = 4;
	static int black_king_row = 7;
	static int black_king_col = 4;
	// keeps track of checks
	static boolean white_check = false;
	static boolean black_check = false;
	// keeps track of the activation of castling
	static boolean white_castle = false;
	static boolean black_castle = false;
	// keeps track of appending draws
	public static boolean draw = false;
	// keeps track of the last piece's position
	static int last_row = -1;
	static int last_column = -1;
	
	/**
	 * Checks the given promotional input
	 * @param input
	 * @return true if promoting to Rook, Knight, Bishop, Queen, or Knight, false otherwise
	 */
	public static boolean check_input(char input) {
		if (input == 'R' || input == 'N' || input == 'B' || input == 'Q') {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Creates a new chess piece when promoting a Pawn piece
	 * @param promotion
	 * @return the new piece Object created
	 */
	public static Object create_object(char promotion, int row, int column) {
		Object new_piece = null;
		
		switch(promotion) {
		case 'R': // if rook
			new_piece = new Rook(row, column);
			break;
		case 'N': // if knight
			new_piece = new Knight(row, column);
			break;
		case 'B': // if bishop
			new_piece = new Bishop(row, column);
			break;
		case 'Q': // if queen
			new_piece = new Queen(row, column);
			break;
		default :
			System.out.println("SHOULD NEVER GO HERE!");
			break;
		}
		AndroidChessActivity.promote = true;
		return new_piece;
	}
	
	/**
	 * Checks if the last piece can place the king in check
	 * @param board
	 * @param piece
	 * @param row
	 * @param column
	 * @return false if conditions are not met, true otherwise
	 */
	public static boolean check(Block[][] board, String piece, int row, int column) {
		int king_row = -1;
		int king_column = -1;
		// gets the kings position
		if (piece.charAt(0) == 'w') {
			king_row = black_king_row;
			king_column = black_king_col;
		} else {
			king_row = white_king_row;
			king_column = white_king_col;
		}
		return check_piece(board, piece, row, column, king_row, king_column, true);
	}
	
	/**
	 * Given the piece and the current turn of the game, verify the ownership
	 * @param piece
	 * @param turn
	 * @return false if moving the incorrect piece, true otherwise
	 */
	public static boolean check_ownership(String piece, int turn) {
		if (turn%2 == 0 && piece.charAt(0) != 'w') {
			return false;
		} else if (turn%2 != 0 && piece.charAt(0) != 'b') {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Updates the position of the king
	 * @param row
	 * @param column
	 */
	public static void update_king(int row, int column) {
		if (Chess.current_player.equals("White")) {
			white_king_row = row;
			white_king_col = column;
		} else {
			black_king_row = row;
			black_king_col = column;
		}
	}
	
	/**
	 * Calls upon the respective movement validation for the given piece
	 * @param board
	 * @param piece
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @return false if move is invalid, true otherwise
	 */
	public static boolean check_piece(Block[][] board, String piece, int row1, int column1, int row2, int column2, boolean checking) {
		boolean valid = false;
		switch(piece.charAt(1)) {
		case 'p': // if pawn
			valid = ((Pawn) board[row1][column1].piece_object).check_pawn(board, row1, column1, row2, column2, false);
			if (!(valid) && !(checking)) { // show all possible pawn moves if not valid
				((Pawn) board[row1][column1].piece_object).show_moves(board, row1, column1);
			}
			break;
		case 'R': // if rook
			valid = ((Rook) board[row1][column1].piece_object).check_rook(board, row1, column1, row2, column2);
			if (!(valid) && !(checking)) { // show all possible rook moves if not valid
				((Rook) board[row1][column1].piece_object).show_moves(board, row1, column1, false);
			}
			break;
		case 'N': // if knight
			valid = ((Knight) board[row1][column1].piece_object).check_knight(board, row1, column1, row2, column2);
			if (!(valid) && !(checking)) { // show all possible knight moves if not valid
				((Knight) board[row1][column1].piece_object).show_moves(board, row1, column1);
			}
			break;
		case 'B': // if bishop
			valid = ((Bishop) board[row1][column1].piece_object).check_bishop(board, row1, column1, row2, column2);
			if (!(valid) && !(checking)) { // show all possible knight moves if not valid
				((Bishop) board[row1][column1].piece_object).show_moves(board, row1, column1, false);
			}
			break;
		case 'Q': // if queen
			valid = ((Queen) board[row1][column1].piece_object).check_queen(board, row1, column1, row2, column2);
			if (!(valid) && !(checking)) { // show all possible queen moves if not valid
				((Queen) board[row1][column1].piece_object).show_moves(board, row1, column1);
			}
			break;
		case 'K': // if king
			valid = ((King) board[row1][column1].piece_object).check_king(board, row1, column1, row2, column2, false);
			if (!(valid) && !(checking)) { // show all possible king moves if not valid
				((King) board[row1][column1].piece_object).show_moves(board, row1, column1, false);
			}
			else if (valid && !(checking)) { // updates the king's position if move is valid
				update_king(row2, column2);
			}
			else if (valid && checking){ // if checking
				((King) board[row1][column1].piece_object).show_moves(board, row1, column1, true);
				valid = false;
			}
			break;
		default :
			valid = false;
			break;
		}
		return valid;
	}
	
	/**
	 * Checks if the position is available for promotion
	 * @param piece
	 * @param line
	 * @return false if promotional requirements are not met, true otherwise
	 */
	public static boolean check_promote(String piece, int line) {
		if (line == 7 && piece.charAt(0) == 'w') {
			return true;
		} else if (line == 0 && piece.charAt(0) == 'b') {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the space is empty and/or compare the ownership of the two pieces
	 * @param space
	 * @param capturing
	 * @return true if the space is empty or the ownerships are different, false otherwise
	 */
	public static boolean check_space(Block space, char capturing) {
		// when space is empty
		if (capturing == 'n' && space.current_piece == null) {
			return true;
		}
		// when attempting to capture
		if (capturing != 'n' && space.current_piece != null && space.current_piece.charAt(0) != capturing) {
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to convert the given user input into ints
	 * @param at
	 * @return the int -1 value of the given char
	 */
	public static int convert(char at) {
		switch (at) {
			case 'a': return 0;
			case 'b': return 1;
			case 'c': return 2;
			case 'd': return 3;
			case 'e': return 4;
			case 'f': return 5;
			case 'g': return 6;
			case 'h': return 7;
			case '1': return 0;
			case '2': return 1;
			case '3': return 2;
			case '4': return 3;
			case '5': return 4;
			case '6': return 5;
			case '7': return 6;
			case '8': return 7;
			default : return -1;
		}
	}
	
	/**
	 * Forcefully move the pieces backward
	 * @param board
	 * @param move
	 */
	public static void force_undo(Block[][] board, String move, String last_piece, Object last_object) {
		String[] position = move.split(" ");
		// gets the moved piece
		int row2 = Control.convert(position[1].charAt(1));
		int column2 = Control.convert(position[1].charAt(0));
		// gets the original position of the moved piece
		int row1 = Control.convert(position[0].charAt(1));
		int column1 = Control.convert(position[0].charAt(0));
		board[row1][column1].current_piece = board[row2][column2].current_piece;
		board[row1][column1].piece_object = board[row2][column2].piece_object;
		board[row2][column2].current_piece = last_piece;
		board[row2][column2].piece_object = last_object;
		Chess.turn--;
	}
	
	/**
	 * Attempts to get the chess piece on the board with the given position
	 * @param board
	 * @param position
	 * @return the String name of the chess piece if valid, null otherwise
	 */
	public static String getPiece(Block[][] board, String position) {
		String piece = null;
		int row = convert(position.charAt(1));
		int column = convert(position.charAt(0));
		if (row != -1 && column != -1) {
			return board[row][column].current_piece;
		}
		return piece;
	}
	
	public static String getColor(Block[][] board, String position) {
		int row = convert(position.charAt(1));
		int column = convert(position.charAt(0));
		return board[row][column].block;
	}
	
	/**
	 * Converts the given input back to char
	 * @param at
	 * @param row
	 * @return the char representation of the int
	 */
	public static char revert(int at, boolean row) {
		if (row) {
			switch (at) {
			case 0: return '1';
			case 1: return '2';
			case 2: return '3';
			case 3: return '4';
			case 4: return '5';
			case 5: return '6';
			case 6: return '7';
			case 7: return '8';
			default : return 'n';
			}
		} else {
			switch (at) {
			case 0: return 'a';
			case 1: return 'b';
			case 2: return 'c';
			case 3: return 'd';
			case 4: return 'e';
			case 5: return 'f';
			case 6: return 'g';
			case 7: return 'h';
			default : return 'n';
			}
		}
	}
	
	/**
	 * Checks the piece with it's respected rules, moves the chess piece if valid
	 * @param board
	 * @param piece
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @param promotion
	 * @return false if the move is invalid, true otherwise
	 */
	public static boolean valid_move(Block[][] board, String piece, int row1, int column1, int row2, int column2, char promotion) {
		
		boolean valid = false;
		
		if (row1 == -1 || row2 == -1 || column1 == -1 || column2 == -1) { // invalid number
			return valid;
		}
		
		valid = check_piece(board, piece, row1, column1, row2, column2, false);
		if (valid) { // if moves are valid
			// keeps track of position two
			AndroidChessActivity.last_piece = board[row2][column2].current_piece;
			AndroidChessActivity.piece_object = board[row2][column2].piece_object;
			// checks for promotion
			if (piece.charAt(1) == 'p' && check_promote(piece, row2)) {
				board[row2][column2].current_piece = "" + piece.charAt(0) + promotion;
				System.out.println("PROMOTING TO: " + board[row2][column2].current_piece);
				board[row2][column2].piece_object = create_object(promotion, row2, column2);
			} else {
				board[row2][column2].current_piece = piece;
				board[row2][column2].piece_object = board[row1][column1].piece_object;
			}
			board[row1][column1].current_piece = null;
			board[row1][column1].piece_object = null;
			last_row = row2;
			last_column = column2;
		} else {
			AndroidChessActivity.last_piece = "";
			AndroidChessActivity.piece_object = null;
		}
		return valid;
	}
	
	/**
	 * Prints out the move with the given positions
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @param checking
	 */
	public static void print_move(int row1, int column1, int row2, int column2, boolean checking) {
		if (!(checking)) {
			AndroidChessActivity.possible_moves = Control.revert(column1, false) + "" + Control.revert(row1, true) + " " + Control.revert(column2, false) + "" + Control.revert(row2, true);
			System.out.println(Control.revert(column1, false) + "" + Control.revert(row1, true) + " " + Control.revert(column2, false) + "" + Control.revert(row2, true));
		}
	}
}
