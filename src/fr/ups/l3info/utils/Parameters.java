package fr.ups.l3info.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Parameters {
	private SharedPreferences _preferences;
	private SharedPreferences.Editor _editor;
	private static Parameters _instance;
	
	private Parameters() { }
	
	public void initialize(final Context c) {
		_preferences = PreferenceManager.getDefaultSharedPreferences(c);
		_editor = _preferences.edit();
		_editor.commit();
	}
	public static Parameters getInstance() {
		if(_instance == null) {
			_instance = new Parameters();
		} 
		
		return _instance;
	}
	
	public void commit() {
		_editor.commit();
	}
	
	public void put(String s, boolean b) {
		_editor.putBoolean(s, b);
	}
	
	public void put(String s, int i) {
		_editor.putInt(s, i);
	}
	
	public void put(String s, String value) {
		_editor.putString(s, value);
	}
	
	public int get(String s, int defaultValue) {
		return _preferences.getInt(s, defaultValue);
	}
	
	public boolean get(String s, boolean b) {
		return _preferences.getBoolean(s, b);
	}
	
	public String get(String s, String defaultValue) {
		return _preferences.getString(s, defaultValue);
	}
	
}
