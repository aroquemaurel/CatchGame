package fr.ups.l3info.l3info_catchgameactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import fr.ups.l3info.l3info_catchgametemplate.R;
import fr.ups.l3info.utils.Parameters;

public class SettingsActivity extends Activity {
	private SeekBar _sFruitSpeed;
	private SeekBar _sFruitNumber;
	private SeekBar _sFruitSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		_sFruitNumber = (SeekBar) findViewById(R.id.sFruitNumber); 
		_sFruitSize = (SeekBar) findViewById(R.id.sFruitSize);
		_sFruitSpeed = (SeekBar) findViewById(R.id.sFruitSpeed);  
		_sFruitSpeed.setProgress(Parameters.getInstance().get("fruitSpeed", 50));		
		setResult(2);
	}
	
	@Override
	public void finish() {
		Parameters.getInstance().put("fruitSpeed", _sFruitSpeed.getProgress());
		Parameters.getInstance().commit();
	/*	Parameters.getInstance(this).put("fruitSize", _sFruitSize.getProgress());
		Parameters.getInstance(this).put("fruitNumbers", _sFruitNumber.getProgress());
		Parameters.getInstance(this).commit();
*/
	//	setContentView(R.layout.activity_catch_game);
		super.finish();
	}
	
}
