package fr.ups.l3info.l3info_catchgameactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fr.ups.l3info.l3info_catchgamedatastructure.Game;
import fr.ups.l3info.l3info_catchgametemplate.R;
import fr.ups.l3info.utils.Parameters;

/* 
 * Main screen for the game
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameActivity extends Activity {
	private CatchGameView fruitView;
	private Button bStart;
	private Button bStop;
	static TextView labelNbFruits;
	static TextView labelBestScore;
	
	public static void updateScore() {
		labelNbFruits.setText("coucou");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parameters.getInstance().initialize(getApplicationContext());
		setContentView(R.layout.activity_catch_game);
		fruitView = (CatchGameView) findViewById(R.id.l3InfoCatchGameView1);
		bStart = (Button) findViewById(R.id.buttonStart);
		bStop = (Button) findViewById(R.id.buttonStop);
		bStop.setEnabled(false);
		labelBestScore = (TextView)findViewById(R.id.textView2);
		labelNbFruits = (TextView)findViewById(R.id.textView1);
		bStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonStartClickEventHandler();
			}
		});
		
		bStop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonStopClickEventHandler();
			}
		});
	}

	private void buttonStartClickEventHandler() {
		Game.getInstance().start(fruitView);
		bStop.setEnabled(true);
		bStart.setEnabled(false);
	}
	
	private void buttonStopClickEventHandler() {
		fruitView.endGame();
		bStart.setEnabled(true);
		bStop.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to thfe action bar if it is present.
		getMenuInflater().inflate(R.menu.catch_game, menu);
		return true;
	}

	public void action_settings(MenuItem i) {
		// Activity is started with requestCode 2
		startActivityForResult(new Intent(CatchGameActivity.this, SettingsActivity.class), 2);
	}

	// Call Back method to get the Message form other Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed here it is 2
		if (requestCode == 2) {
			Game.getInstance().majParameters();
			Toast.makeText(this, "Paramètres sauvegardés", Toast.LENGTH_SHORT).show();
		}
	}

}
