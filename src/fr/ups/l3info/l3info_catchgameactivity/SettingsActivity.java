package fr.ups.l3info.l3info_catchgameactivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Spinner;
import fr.ups.l3info.l3info_catchgametemplate.R;
import fr.ups.l3info.utils.Parameters;

public class SettingsActivity extends Activity {
	private SeekBar _sFruitSpeed;
	private SeekBar _sFruitNumber;
	private Spinner _sFruitSize;
	private Spinner _sFruitLimit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		_sFruitNumber = (SeekBar) findViewById(R.id.sFruitNumber); 
		_sFruitSize = (Spinner) findViewById(R.id.fruitSize);
		_sFruitSpeed = (SeekBar) findViewById(R.id.sFruitSpeed); 
		_sFruitLimit = (Spinner) findViewById(R.id.spinner1);
		_sFruitSpeed.setProgress(Parameters.getInstance().get("fruitSpeed", 50));
		_sFruitSize.setSelection(Parameters.getInstance().get("fruitSize", 1)-1);
		_sFruitNumber.setProgress(Parameters.getInstance().get("fruitNumbers", 30));
		_sFruitLimit.setSelection(Parameters.getInstance().get("fruitLimit", 3)-1);
		setResult(2);
	}
	
	@Override
	public void finish() {
		Parameters.getInstance().put("fruitSpeed", _sFruitSpeed.getProgress());
		Parameters.getInstance().put("fruitSize", _sFruitSize.getSelectedItemPosition()+1);
		Parameters.getInstance().put("fruitNumbers", _sFruitNumber.getProgress());
		Parameters.getInstance().put("fruitLimit", _sFruitLimit.getSelectedItemPosition()+1);

		Parameters.getInstance().commit();

		super.finish();
	}
	
}
