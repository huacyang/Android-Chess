package bg;

/**
 * The chess board itself
 * @author Hua Yang
 */
public class Board {
	private static int board_space = 8;

	/**
	 * Initializes the board, setting the black and white spaces
	 * @return the Block[][] object
	 */
	public static Block[][] create_board() {
		Block[][] board = new Block[board_space][board_space];
		for (int i = 0; i < board.length; i++)
			for (int n = 0; n < board[i].length; n++) {
				Block new_block = new Block();
				// through a lot of trial and error, dunno the reasoning
				if (i%2 == n%2) {
					new_block.block = "black";
				} else {
					new_block.block = "white";
				}
				
				new_block.current_piece = null;
				new_block.piece_object = null;
				board[i][n] = new_block;
			}
		return board;
	}
	
	/**
	 * Prints out the given Block[][] object and output each line
	 * @param board
	 */
	public static void print_board(Block[][] board) {
		int side = 8;
		System.out.println("_______________________");
		for (int i = board.length-1; i >= 0; i--) {
			String row = "";
			for (int n = 0; n < board[i].length; n++) {
				if (board[i][n].current_piece == null) {
					if (board[i][n].block.equals("white")) {
						row += "   ";
					} else {
						row += "## ";
					}
				} else {
					row += board[i][n].current_piece + " ";
				}
			}
			System.out.println(row + " " + side);
			side--;
		}
		System.out.println(" a  b  c  d  e  f  g  h \n");
	}
	
	/**
	 * Initialize the Block[][] object, placing the chess pieces on the board
	 * @param board
	 */
	public static void set_board(Block[][] board) {
		// sets the white pieces
		board[0][0].current_piece = "wR";
		board[0][0].piece_object = new Rook(0, 0);
		board[0][1].current_piece = "wN";
		board[0][1].piece_object = new Knight(0, 1);
		board[0][2].current_piece = "wB";
		board[0][2].piece_object = new Bishop(0, 2);
		board[0][3].current_piece = "wQ";
		board[0][3].piece_object = new Queen(0, 3);
		board[0][4].current_piece = "wK";
		board[0][4].piece_object = new King(0, 4);
		board[0][5].current_piece = "wB";
		board[0][5].piece_object = new Bishop(0, 5);
		board[0][6].current_piece = "wN";
		board[0][6].piece_object = new Knight(0, 6);
		board[0][7].current_piece = "wR";
		board[0][7].piece_object = new Rook(0, 7);
		for (int i = 0; i < board.length; i++) {
			board[1][i].current_piece = "wp";
			board[1][i].piece_object = new Pawn(1, i);
		}
		// sets the black pieces
		board[7][0].current_piece = "bR";
		board[7][0].piece_object = new Rook(7, 0);
		board[7][1].current_piece = "bN";
		board[7][1].piece_object = new Knight(7, 1);
		board[7][2].current_piece = "bB";
		board[7][2].piece_object = new Bishop(7, 2);
		board[7][3].current_piece = "bQ";
		board[7][3].piece_object = new Queen(7, 3);
		board[7][4].current_piece = "bK";
		board[7][4].piece_object = new King(7, 4);
		board[7][5].current_piece = "bB";
		board[7][5].piece_object = new Bishop(7, 5);
		board[7][6].current_piece = "bN";
		board[7][6].piece_object = new Knight(7, 6);
		board[7][7].current_piece = "bR";
		board[7][7].piece_object = new Rook(7, 7);
		for (int i = 0; i < board.length; i++) {
			board[6][i].current_piece = "bp";
			board[6][i].piece_object = new Pawn(6, i);
		}
	}
}
