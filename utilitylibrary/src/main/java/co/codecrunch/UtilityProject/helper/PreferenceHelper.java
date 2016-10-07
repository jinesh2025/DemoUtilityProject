package co.codecrunch.UtilityProject.helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jinesh Soni on 06/10/2016.
 */

public class PreferenceHelper {

	static Activity activity;

	public PreferenceHelper(Activity activity) {
		this.activity = activity;
	}

	public Boolean loadSavedBooleanPreferences(String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		Boolean token = sharedPreferences.getBoolean(key, false);
		return token;
	}

	public String loadSavedStringPreferences(String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		String token = sharedPreferences.getString(key, "");
		return token;
	}

	public void saveBooleanPreferences(String key, Boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public void saveStringPreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}
}
