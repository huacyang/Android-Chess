package cs213.chess.android;

import java.io.IOException;

import bg.Block;
import bg.Chess;
import bg.Control;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Engine for AndroidChessActivity && AndroidChessReplay
 * @author Hua Yang
 */
public class AndroidChessEngine {

	/**
     * Validates the given two inputs
     * @return true if the move is valid, false otherwise
	 * @throws IOException 
     */
    public static boolean validate(Block[][] board, String move) throws IOException {
    	boolean valid = true;
		AndroidChessActivity.game = Chess.chess(board, move);
		// if valid
		if (AndroidChessActivity.game == 3 || AndroidChessActivity.game == 4
				|| AndroidChessActivity.game == 6 || AndroidChessActivity.game == 7
				|| AndroidChessActivity.game == 8) {
			valid = false;
		}
		return valid;
    }
    
    /**
     * Returns the name of the piece with the given board and the position on the board
     * @param board
     * @param position
     * @return the name of the piece in a String
     */
    public static String get_piece(Block[][] board, String position) {
    	System.out.println(Control.getPiece(board, position));
    	return Control.getPiece(board, position);
    }
    
    /**
     * With the given position of the king, sets the rook according during a castling
     * @param position
     * @param play
     */
    public static void set_castling(String position, boolean play) {
    	if (position.equals("g1")) {
    		if (play) {
	    		AndroidChessActivity.f1.setImageResource(R.drawable.white_rook);
	    		AndroidChessActivity.h1.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.f1.setImageResource(R.drawable.white_rook);
	    		AndroidChessReplay.h1.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("c1")) {
    		if (play) {
	    		AndroidChessActivity.d1.setImageResource(R.drawable.white_rook);
	    		AndroidChessActivity.a1.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.d1.setImageResource(R.drawable.white_rook);
    			AndroidChessReplay.a1.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("g8")) {
    		if (play) {
	    		AndroidChessActivity.f8.setImageResource(R.drawable.black_rook);
	    		AndroidChessActivity.h8.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.f8.setImageResource(R.drawable.black_rook);
    			AndroidChessReplay.h8.setImageResource(R.drawable.black);
    		}
    	} else {
    		if (play) {
	    		AndroidChessActivity.d8.setImageResource(R.drawable.black_rook);
	    		AndroidChessActivity.a8.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.d8.setImageResource(R.drawable.black_rook);
    			AndroidChessReplay.a8.setImageResource(R.drawable.white);
    		}
    	}
    }
    
    /**
     * Deletes the pawn taken during the process of en passant
     * @param position
     * @param play
     */
    public static void set_passant(String position, boolean play) {
    	if (position.equals("a3")) {
    		if (play) {
    			AndroidChessActivity.a4.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.a4.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("b3")) {
    		if (play) {
    			AndroidChessActivity.b4.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.b4.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("c3")) {
    		if (play) {
    			AndroidChessActivity.c4.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.c4.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("d3")) {
    		if (play) {
    			AndroidChessActivity.d4.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.d4.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("e3")) {
    		if (play) {
    			AndroidChessActivity.e4.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.e4.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("f3")) {
    		if (play) {
    			AndroidChessActivity.f4.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.f4.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("g3")) {
    		if (play) {
    			AndroidChessActivity.g4.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.g4.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("h3")) {
    		if (play) {
    			AndroidChessActivity.h4.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.h4.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("a6")) {
    		if (play) {
    			AndroidChessActivity.a5.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.a5.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("b6")) {
    		if (play) {
    			AndroidChessActivity.b5.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.b5.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("c6")) {
    		if (play) {
    			AndroidChessActivity.c5.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.c5.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("d6")) {
    		if (play) {
    			AndroidChessActivity.d5.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.d5.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("e6")) {
    		if (play) {
    			AndroidChessActivity.e5.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.e5.setImageResource(R.drawable.black);
    		}
    	} else if (position.equals("f6")) {
    		if (play) {
    			AndroidChessActivity.f5.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.f5.setImageResource(R.drawable.white);
    		}
    	} else if (position.equals("g6")) {
    		if (play) {
    			AndroidChessActivity.g5.setImageResource(R.drawable.black);
    		} else {
    			AndroidChessReplay.g5.setImageResource(R.drawable.black);
    		}
    	} else {
    		if (play) {
    			AndroidChessActivity.h5.setImageResource(R.drawable.white);
    		} else {
    			AndroidChessReplay.h5.setImageResource(R.drawable.white);
    		}
    	}
    }
    
    /**
     * Sets the image accordingly to the piece that is on top of it
     * @param iButton
     * @param piece
     */
	public static void set_image(ImageButton iButton, String piece) {
		if (piece.equals("wR")) {
			iButton.setImageResource(R.drawable.white_rook);
		} else if (piece.equals("wN")) {
			iButton.setImageResource(R.drawable.white_knight);
		} else if (piece.equals("wB")) {
			iButton.setImageResource(R.drawable.white_bishop);
		} else if (piece.equals("wQ")) {
			iButton.setImageResource(R.drawable.white_queen);
		} else if (piece.equals("wK")) {
			iButton.setImageResource(R.drawable.white_king);
		} else if (piece.equals("wp")) {
			iButton.setImageResource(R.drawable.white_pawn);
		} else if (piece.equals("bR")) {
			iButton.setImageResource(R.drawable.black_rook);
		} else if (piece.equals("bN")) {
			iButton.setImageResource(R.drawable.black_knight);
		} else if (piece.equals("bB")) {
			iButton.setImageResource(R.drawable.black_bishop);
		} else if (piece.equals("bQ")) {
			iButton.setImageResource(R.drawable.black_queen);
		} else if (piece.equals("bK")) {
			iButton.setImageResource(R.drawable.black_king);
		} else if (piece.equals("bp")) {
			iButton.setImageResource(R.drawable.black_pawn);
		} else if (piece.equals("white")) {
			iButton.setImageResource(R.drawable.white);
		} else if (piece.equals("black")) {
			iButton.setImageResource(R.drawable.black);
		} else {
			iButton.setImageResource(R.drawable.ic_launcher);
		}
	}
	
	/**
	 * Undos the move with the given two image buttons
	 * @param one
	 * @param two
	 * @param move
	 */
	public static void undo(ImageButton one, ImageButton two, String piece, String color) {
		set_image(one, piece);
		set_image(two, color);
	}
}
