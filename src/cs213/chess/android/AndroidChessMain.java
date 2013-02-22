package cs213.chess.android;

import cs213.chess.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Starts the main menu for L'Chess
 * @author Hua Yang
 */
public class AndroidChessMain extends Activity {

	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button play = (Button) findViewById(R.id.button_play);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AndroidChessActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button replay = (Button) findViewById(R.id.button_replay);
        replay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AndroidChessReplay.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button credit = (Button) findViewById(R.id.button_credit);
        credit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	/*
                Intent myIntent = new Intent(view.getContext(), AndroidChessActivity.class);
                startActivityForResult(myIntent, 0);
                */
            	print("Work-in progress!");
            }
        });
	}
	
	/**
	 * Outputs the given string via Toast
	 * @param text
	 */
	public void print(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
