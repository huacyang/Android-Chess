package cs213.chess.android;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import bg.Block;
import bg.Board;
import bg.Chess;
import bg.Control;
import cs213.chess.android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Starts the game of L'Chess
 * @author Hua Yang
 */
public class AndroidChessActivity extends Activity {
	
	// keeps track of the board
	public static Block[][] board;
	// keeps track of the previous input move
	public static String previous_move;
	// keeps track of the input move
	public static String move;
	// keeps track of all the moves
	public static LinkedList<String> data;
	// keeps track of the image
	public static drawable draw;
	// keeps track of the first piece
	public static String piece;
	// keeps track of the second position
	public static String second_position;
	// keeps track of the previous moves
	public static ImageButton previous_first;
	public static ImageButton previous_second;
	// keeps track of the first && second image button
	public static ImageButton first_button;
	public static ImageButton second_button;
	// keeps track of the color of the block
	public static String color;
	public static String last_color;
	// boolean to detect first input
	public static boolean first;
	// boolean to detect castling
	public static boolean castling;
	// boolean to detect en passant
	public static boolean en_passant;
	// boolean to detect promotion
	public static boolean promote;
	// keeps track of what to print
	public static int game;
	// boolean to detect draws
	public static boolean first_draw;
	// keeps track of available moves
	public static String possible_moves;
	// boolean to detect undo's
	public static boolean undone;
	// boolean to detect first move
	public static boolean game_started;
	// boolean to detect AI moves
	public static boolean AI_move;
	// keeps track of last piece;
	public static String last_piece;
	public static Object piece_object;
	// keeps track of the buttons
	public static ImageButton a1 = null, a2 = null, a3 = null, a4 = null, a5 = null, a6 = null, a7 = null, a8 = null;
	public static ImageButton b1 = null, b2 = null, b3 = null, b4 = null, b5 = null, b6 = null, b7 = null, b8 = null;
	public static ImageButton c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null;
	public static ImageButton d1 = null, d2 = null, d3 = null, d4 = null, d5 = null, d6 = null, d7 = null, d8 = null;
	public static ImageButton e1 = null, e2 = null, e3 = null, e4 = null, e5 = null, e6 = null, e7 = null, e8 = null;
	public static ImageButton f1 = null, f2 = null, f3 = null, f4 = null, f5 = null, f6 = null, f7 = null, f8 = null;
	public static ImageButton g1 = null, g2 = null, g3 = null, g4 = null, g5 = null, g6 = null, g7 = null, g8 = null;
	public static ImageButton h1 = null, h2 = null, h3 = null, h4 = null, h5 = null, h6 = null, h7 = null, h8 = null;
	
    /** Called when the activity is first created */
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.chess);
        
        // cleans the program
        cleanse();
        // creates the board object
		board = Board.create_board();
		// sets the pieces on the board
		Board.set_board(board);
		// initializes the image buttons on the board
        initialize();
        // initializes the buttons
        create_buttons();
    }
	
	/**
	 * Resets the board values for the new game
	 */
	public void cleanse() {
		// ++++++++++++++++++++
        board = null;
    	move = "";
    	data = new LinkedList<String>();
    	draw = null;
    	piece = "";
    	first_button = null;
    	color = "";
    	first = true;
    	castling = false;
    	en_passant = false;
    	promote = false;
    	game = -1;
    	first_draw = true;
    	possible_moves = "";
    	undone = false;
    	game_started = false;
    	AI_move = false;
    	last_piece = "";
    	piece_object = null;
        // ++++++++++++++++++++
	}
	
	/**
	 * Initializes the buttons for "Undo", "AI", "Draw", & "Resign"
	 */
	public void create_buttons() {
		Button undo_button = (Button) findViewById(R.id.button_undo);
		undo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	// if undo button hasn't been used
                if (!undone) {
                	if (!game_started) {
                		print("The game hasn't started!");
                	} else if (move.length() > 1) {
                		print("You have already selected a piece!");
                	} else {
                    	undone = true;
                		AndroidChessEngine.undo(previous_first, previous_second, piece, last_color);
                		Control.force_undo(board, previous_move, last_piece, piece_object);
                		// resets any selected pieces prior to using the undo button
                		move = "";
                		first = true;
                	}
                } else {
                	print("You already used it this turn!");
                }
            }
        });
		
		Button ai_button = (Button) findViewById(R.id.button_ai);
		ai_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (move.length() > 1) {
                	move += move;
            		AI_move = true;
                	check();
                	if (possible_moves != null) {
                		String[] position = possible_moves.split(" ");
                		move = possible_moves;
                		second_button = set_button(position[1]);
                		check();
                	} else {
                		print("No possible moves!");
                		move = "";
                	}
                } else {
                	print("Select a piece first!");
                }
            }
        });
		
		Button draw_button = (Button) findViewById(R.id.button_draw);
		draw_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	if (first_draw) {
	            	first_draw = false;
	            } else {
            		first_draw = true;
            		game_status(5);
	            }
            }
        });
		
		Button resign = (Button) findViewById(R.id.button_resign);
		resign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	move = "resign";
                check();
            }
        });
	}
    
    /**
     * Initializes the image buttons
     */
    public void initialize() {
    	// creates the buttons
    	a8 = (ImageButton)findViewById(R.id.imageButton81);
        b8 = (ImageButton)findViewById(R.id.imageButton82);
        c8 = (ImageButton)findViewById(R.id.imageButton83);
        d8 = (ImageButton)findViewById(R.id.imageButton84);
        e8 = (ImageButton)findViewById(R.id.imageButton85);
        f8 = (ImageButton)findViewById(R.id.imageButton86);
        g8 = (ImageButton)findViewById(R.id.imageButton87);
        h8 = (ImageButton)findViewById(R.id.imageButton88);
        a7 = (ImageButton)findViewById(R.id.imageButton71);
        b7 = (ImageButton)findViewById(R.id.imageButton72);
        c7 = (ImageButton)findViewById(R.id.imageButton73);
        d7 = (ImageButton)findViewById(R.id.imageButton74);
        e7 = (ImageButton)findViewById(R.id.imageButton75);
        f7 = (ImageButton)findViewById(R.id.imageButton76);
        g7 = (ImageButton)findViewById(R.id.imageButton77);
        h7 = (ImageButton)findViewById(R.id.imageButton78);
        a6 = (ImageButton)findViewById(R.id.imageButton61);
        b6 = (ImageButton)findViewById(R.id.imageButton62);
        c6 = (ImageButton)findViewById(R.id.imageButton63);
        d6 = (ImageButton)findViewById(R.id.imageButton64);
        e6 = (ImageButton)findViewById(R.id.imageButton65);
        f6 = (ImageButton)findViewById(R.id.imageButton66);
        g6 = (ImageButton)findViewById(R.id.imageButton67);
        h6 = (ImageButton)findViewById(R.id.imageButton68);
        a5 = (ImageButton)findViewById(R.id.imageButton51);
        b5 = (ImageButton)findViewById(R.id.imageButton52);
        c5 = (ImageButton)findViewById(R.id.imageButton53);
        d5 = (ImageButton)findViewById(R.id.imageButton54);
        e5 = (ImageButton)findViewById(R.id.imageButton55);
        f5 = (ImageButton)findViewById(R.id.imageButton56);
        g5 = (ImageButton)findViewById(R.id.imageButton57);
        h5 = (ImageButton)findViewById(R.id.imageButton58);
        a4 = (ImageButton)findViewById(R.id.imageButton41);
        b4 = (ImageButton)findViewById(R.id.imageButton42);
        c4 = (ImageButton)findViewById(R.id.imageButton43);
        d4 = (ImageButton)findViewById(R.id.imageButton44);
        e4 = (ImageButton)findViewById(R.id.imageButton45);
        f4 = (ImageButton)findViewById(R.id.imageButton46);
        g4 = (ImageButton)findViewById(R.id.imageButton47);
        h4 = (ImageButton)findViewById(R.id.imageButton48);
        a3 = (ImageButton)findViewById(R.id.imageButton31);
        b3 = (ImageButton)findViewById(R.id.imageButton32);
        c3 = (ImageButton)findViewById(R.id.imageButton33);
        d3 = (ImageButton)findViewById(R.id.imageButton34);
        e3 = (ImageButton)findViewById(R.id.imageButton35);
        f3 = (ImageButton)findViewById(R.id.imageButton36);
        g3 = (ImageButton)findViewById(R.id.imageButton37);
        h3 = (ImageButton)findViewById(R.id.imageButton38);
        a2 = (ImageButton)findViewById(R.id.imageButton21);
        b2 = (ImageButton)findViewById(R.id.imageButton22);
        c2 = (ImageButton)findViewById(R.id.imageButton23);
        d2 = (ImageButton)findViewById(R.id.imageButton24);
        e2 = (ImageButton)findViewById(R.id.imageButton25);
        f2 = (ImageButton)findViewById(R.id.imageButton26);
        g2 = (ImageButton)findViewById(R.id.imageButton27);
        h2 = (ImageButton)findViewById(R.id.imageButton28);
        a1 = (ImageButton)findViewById(R.id.imageButton11);
        b1 = (ImageButton)findViewById(R.id.imageButton12);
        c1 = (ImageButton)findViewById(R.id.imageButton13);
        d1 = (ImageButton)findViewById(R.id.imageButton14);
        e1 = (ImageButton)findViewById(R.id.imageButton15);
        f1 = (ImageButton)findViewById(R.id.imageButton16);
        g1 = (ImageButton)findViewById(R.id.imageButton17);
        h1 = (ImageButton)findViewById(R.id.imageButton18);
        // add the listeners
        a8.setOnClickListener(new on_click("a8", a8));
        b8.setOnClickListener(new on_click("b8", b8));
        c8.setOnClickListener(new on_click("c8", c8));
        d8.setOnClickListener(new on_click("d8", d8));
        e8.setOnClickListener(new on_click("e8", e8));
        f8.setOnClickListener(new on_click("f8", f8));
        g8.setOnClickListener(new on_click("g8", g8));
        h8.setOnClickListener(new on_click("h8", h8));
        a7.setOnClickListener(new on_click("a7", a7));
        b7.setOnClickListener(new on_click("b7", b7));
        c7.setOnClickListener(new on_click("c7", c7));
        d7.setOnClickListener(new on_click("d7", d7));
        e7.setOnClickListener(new on_click("e7", e7));
        f7.setOnClickListener(new on_click("f7", f7));
        g7.setOnClickListener(new on_click("g7", g7));
        h7.setOnClickListener(new on_click("h7", h7));
        a6.setOnClickListener(new on_click("a6", a6));
        b6.setOnClickListener(new on_click("b6", b6));
        c6.setOnClickListener(new on_click("c6", c6));
        d6.setOnClickListener(new on_click("d6", d6));
        e6.setOnClickListener(new on_click("e6", e6));
        f6.setOnClickListener(new on_click("f6", f6));
        g6.setOnClickListener(new on_click("g6", g6));
        h6.setOnClickListener(new on_click("h6", h6));
        a5.setOnClickListener(new on_click("a5", a5));
        b5.setOnClickListener(new on_click("b5", b5));
        c5.setOnClickListener(new on_click("c5", c5));
        d5.setOnClickListener(new on_click("d5", d5));
        e5.setOnClickListener(new on_click("e5", e5));
        f5.setOnClickListener(new on_click("f5", f5));
        g5.setOnClickListener(new on_click("g5", g5));
        h5.setOnClickListener(new on_click("h5", h5));
        a4.setOnClickListener(new on_click("a4", a4));
        b4.setOnClickListener(new on_click("b4", b4));
        c4.setOnClickListener(new on_click("c4", c4));
        d4.setOnClickListener(new on_click("d4", d4));
        e4.setOnClickListener(new on_click("e4", e4));
        f4.setOnClickListener(new on_click("f4", f4));
        g4.setOnClickListener(new on_click("g4", g4));
        h4.setOnClickListener(new on_click("h4", h4));
        a3.setOnClickListener(new on_click("a3", a3));
        b3.setOnClickListener(new on_click("b3", b3));
        c3.setOnClickListener(new on_click("c3", c3));
        d3.setOnClickListener(new on_click("d3", d3));
        e3.setOnClickListener(new on_click("e3", e3));
        f3.setOnClickListener(new on_click("f3", f3));
        g3.setOnClickListener(new on_click("g3", g3));
        h3.setOnClickListener(new on_click("h3", h3));
        a2.setOnClickListener(new on_click("a2", a2));
        b2.setOnClickListener(new on_click("b2", b2));
        c2.setOnClickListener(new on_click("c2", c2));
        d2.setOnClickListener(new on_click("d2", d2));
        e2.setOnClickListener(new on_click("e2", e2));
        f2.setOnClickListener(new on_click("f2", f2));
        g2.setOnClickListener(new on_click("g2", g2));
        h2.setOnClickListener(new on_click("h2", h2));
        a1.setOnClickListener(new on_click("a1", a1));
        b1.setOnClickListener(new on_click("b1", b1));
        c1.setOnClickListener(new on_click("c1", c1));
        d1.setOnClickListener(new on_click("d1", d1));
        e1.setOnClickListener(new on_click("e1", e1));
        f1.setOnClickListener(new on_click("f1", f1));
        g1.setOnClickListener(new on_click("g1", g1));
        h1.setOnClickListener(new on_click("h1", h1));
	}
    
    /**
     * Checks the two inputs
     * @param position
     */
    public void check() {
    	try {
			move = move.trim();
			String condition = "";
			// if valid move
			if (AndroidChessEngine.validate(board, move)) {
				// checks for promotion
				if (promote) {
					promote = false;
					AndroidChessEngine.set_image(second_button, piece.charAt(0) + "Q");
				} else {
					AndroidChessEngine.set_image(second_button, piece);
				}
				// checks for castling
				if (castling) {
					AndroidChessEngine.set_castling(second_position, true);
					condition = "castling";
					castling = false;
				} else if (en_passant) { // checks for en passant
					AndroidChessEngine.set_passant(second_position, true);
					condition = "passant";
					en_passant = false;
				} else { // does nothing
					condition = "nothing";
				}
				// updates the current move as previous
				previous_first = first_button;
				previous_second = second_button;
				previous_move = move;
				AndroidChessEngine.set_image(first_button, color);
				data.add(move + " " + piece + " " + color + " " + condition);
			}
			if (game != 0 && !AI_move) {
				game_status(game);
			} else {
				AI_move = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// resets the values
		move = "";
		first = true;
    }
    
    /**
     * OnClickListener for each of the ImageButtons
     * @author Hua Yang
     */
    class on_click implements OnClickListener {
    	// keeps track of the position of the piece
    	private String position;
    	// keeps track of the image button
    	private ImageButton button;
    	
    	public on_click(String position, ImageButton button) {
	   		this.position = position;
	   		this.button = button;
		}
    	
    	public void onClick(View v) {
    		move += position + " ";
    		if (first) { // if first input
    			first_button = this.button;
				first = false;
			} else {// if second input
				second_position = this.position;
				second_button = this.button;
				check();
			}
    	}
	}
    
    
    /**
     * Displays the status of the game (win/draw/resign/invalid moves) via Toast
     */
    public void game_status(int game) {
    	String text = "";
    	boolean over = false;
    	// win or invalid conditions
    	if (game == 1) {
    		text = "-- Checkmate: White wins! --";
    		over = true;
    	} else if (game == 2) {
    		text = "-- Checkmate: Black wins! --";
    		over = true;
    	} else if (game == 3) {
    		text = "-- Resign: Black wins! --";
    		over = true;
    	} else if (game == 4) {
    		text = "-- Resign: White wins! --";
    		over = true;
    	} else if (game == 5) {
    		text = "-- Draw! --";
    		over = true;
    	} else if (game == 6) {
    		text = "-- Piece does not exist! --";
    	} else if (game == 7) {
    		text = "-- Not your piece! --";
    	} else if (game == 8) {
    		text = "-- Invalid move! --";
    	} else {
    		text = "-- Check! --";
    	}
    	// prints out on the console
    	print(text);
    	// if game over
    	if (over) {
    		write("", data);
    		print("The data has been saved!");
    		print("Thanks for playing!");
    		// goes back to main menu
    		Intent myIntent = new Intent(this, AndroidChessMain.class);
            startActivityForResult(myIntent, 0);
    	}
    }
    
    public void print(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    public ImageButton set_button(String button) {
		if (button.equals("a1")) {
			return a1;
		} else if (button.equals("a2")) {
			return a2;
		} else if (button.equals("a3")) {
			return a3;
		} else if (button.equals("a4")) {
			return a4;
		} else if (button.equals("a5")) {
			return a5;
		} else if (button.equals("a6")) {
			return a6;
		} else if (button.equals("a7")) {
			return a7;
		} else if (button.equals("a8")) {
			return a8;
		} else if (button.equals("b1")) {
			return b1;
		} else if (button.equals("b2")) {
			return b2;
		} else if (button.equals("b3")) {
			return b3;
		} else if (button.equals("b4")) {
			return b4;
		} else if (button.equals("b5")) {
			return b5;
		} else if (button.equals("b6")) {
			return b6;
		} else if (button.equals("b7")) {
			return b7;
		} else if (button.equals("b8")) {
			return b8;
		} else if (button.equals("c1")) {
			return c1;
		} else if (button.equals("c2")) {
			return c2;
		} else if (button.equals("c3")) {
			return c3;
		} else if (button.equals("c4")) {
			return c4;
		} else if (button.equals("c5")) {
			return c5;
		} else if (button.equals("c6")) {
			return c6;
		} else if (button.equals("c7")) {
			return c7;
		} else if (button.equals("c8")) {
			return c8;
		} else if (button.equals("d1")) {
			return d1;
		} else if (button.equals("d2")) {
			return d2;
		} else if (button.equals("d3")) {
			return d3;
		} else if (button.equals("d4")) {
			return d4;
		} else if (button.equals("d5")) {
			return d5;
		} else if (button.equals("d6")) {
			return d6;
		} else if (button.equals("d7")) {
			return d7;
		} else if (button.equals("d8")) {
			return d8;
		} else if (button.equals("e1")) {
			return e1;
		} else if (button.equals("e2")) {
			return e2;
		} else if (button.equals("e3")) {
			return e3;
		} else if (button.equals("e4")) {
			return e4;
		} else if (button.equals("e5")) {
			return e5;
		} else if (button.equals("e6")) {
			return e6;
		} else if (button.equals("e7")) {
			return e7;
		} else if (button.equals("e8")) {
			return e8;
		} else if (button.equals("f1")) {
			return f1;
		} else if (button.equals("f2")) {
			return f2;
		} else if (button.equals("f3")) {
			return f3;
		} else if (button.equals("f4")) {
			return f4;
		} else if (button.equals("f5")) {
			return f5;
		} else if (button.equals("f6")) {
			return f6;
		} else if (button.equals("f7")) {
			return f7;
		} else if (button.equals("f8")) {
			return f8;
		} else if (button.equals("g1")) {
			return g1;
		} else if (button.equals("g2")) {
			return g2;
		} else if (button.equals("g3")) {
			return g3;
		} else if (button.equals("g4")) {
			return g4;
		} else if (button.equals("g5")) {
			return g5;
		} else if (button.equals("g6")) {
			return g6;
		} else if (button.equals("g7")) {
			return g7;
		} else if (button.equals("g8")) {
			return g8;
		} else if (button.equals("h1")) {
			return h1;
		} else if (button.equals("h2")) {
			return h2;
		} else if (button.equals("h3")) {
			return h3;
		} else if (button.equals("h4")) {
			return h4;
		} else if (button.equals("h5")) {
			return h5;
		} else if (button.equals("h6")) {
			return h6;
		} else if (button.equals("h7")) {
			return h7;
		} else {
			return h8;
		}
	}
    
    /**
     * Writes the date into the desired file name
     * @param filename
     * @param data
     */
    public void write(String filename, LinkedList<String> data) {
    	try {
			FileOutputStream fos = openFileOutput("chessdata.txt", MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			/* ++++++++++++++++++++++++++++++++++++++++ 
			if (data == null) {
				osw.write("");
				System.out.println("WRITING NOTHING!");
				osw.close();
				return;
			}
			 ++++++++++++++++++++++++++++++++++++++++ */
			// Write the string to the file
			while (!data.isEmpty()) {
				move = data.remove();
				osw.write(move + "\n");
			}
			osw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}