package cs213.chess.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import cs213.chess.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Starts the replay for L'Chess
 * @author Hua Yang
 */
public class AndroidChessReplay extends Activity {

	// keeps track of the image buttons
	public static ImageButton a1 = null, a2 = null, a3 = null, a4 = null, a5 = null, a6 = null, a7 = null, a8 = null;
	public static ImageButton b1 = null, b2 = null, b3 = null, b4 = null, b5 = null, b6 = null, b7 = null, b8 = null;
	public static ImageButton c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null;
	public static ImageButton d1 = null, d2 = null, d3 = null, d4 = null, d5 = null, d6 = null, d7 = null, d8 = null;
	public static ImageButton e1 = null, e2 = null, e3 = null, e4 = null, e5 = null, e6 = null, e7 = null, e8 = null;
	public static ImageButton f1 = null, f2 = null, f3 = null, f4 = null, f5 = null, f6 = null, f7 = null, f8 = null;
	public static ImageButton g1 = null, g2 = null, g3 = null, g4 = null, g5 = null, g6 = null, g7 = null, g8 = null;
	public static ImageButton h1 = null, h2 = null, h3 = null, h4 = null, h5 = null, h6 = null, h7 = null, h8 = null;
	// keeps track of the data read from file
	public LinkedList<String> data;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);
        // ++++++++++
        data = new LinkedList<String>();
        initialize();
        // ++++++++++
        // reads from file "chessdata.txt"
        read("");
        
        Button next = (Button) findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	// if there are still moves left
            	if (!data.isEmpty()) {
            		move(data.remove());
            	} else {
            		print("No moves left!");
            	}
            }
        });
        
        Button back = (Button) findViewById(R.id.button_exit);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AndroidChessMain.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button extra = (Button) findViewById(R.id.button_extra);
        extra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                print("Work-in progress!");
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
    }
	
	/**
	 * References the associated image button
	 * @param button
	 */
	public ImageButton get_button(String button) {
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
	 * Attempts to read the desired saved local file
	 * @param filename
	 */
	public void read(String filename) {
		try {
		    InputStream is = openFileInput("chessdata.txt");
		    // if file is not empty
		    if (is != null) {
		    	InputStreamReader inputreader = new InputStreamReader(is);
		    	BufferedReader buffreader = new BufferedReader(inputreader);
		    	String line = "";
		    	// stores each line in an linked list of strings
		    	while ((line = buffreader.readLine()) != null) {
		    		data.add(line);
		    	}
		    } else {
		    	print("File is empty!");
		    }
		    is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the pieces with the given starting and ending positions
	 * @param line
	 */
	public void move(String line) {
		String[] position = line.split(" ");
		// gets the two image buttons
		ImageButton from = get_button(position[0]);
		ImageButton to = get_button(position[1]);
		String image = position[2];
		String color = position[3];
		String condition = position[4];
		AndroidChessEngine.set_image(to, image);
		AndroidChessEngine.set_image(from, color);
		if (condition.equals("castling")) {
			AndroidChessEngine.set_castling(position[1], false);
		} else if (condition.equals("passant")) {
			AndroidChessEngine.set_passant(position[1], false);
		}
	}
	
	/**
	 * Outputs the given string via Toast
	 * @param text
	 */
	public void print(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
