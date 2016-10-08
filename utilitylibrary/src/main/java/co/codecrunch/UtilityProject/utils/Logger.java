package co.codecrunch.UtilityProject.utils;

import android.support.compat.BuildConfig;
import android.util.Log;

/**
 * Created by Jinesh Soni on 07/10/2016.
 */

public class Logger {
	private static String TAG = BaseAppActivity.getTAG();

	public static void v(String msg) {
		if (!BuildConfig.DEBUG) Log.v(TAG, msg);
	}

	public static void d(String msg) {
		if (!BuildConfig.DEBUG) Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (!BuildConfig.DEBUG) Log.e(TAG, msg);
	}

	public static void i(String msg) {
		if (!BuildConfig.DEBUG) Log.i(TAG, msg);
	}

}
