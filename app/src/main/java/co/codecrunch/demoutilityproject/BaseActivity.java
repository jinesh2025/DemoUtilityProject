package co.codecrunch.demoutilityproject;

import android.os.Bundle;

import co.codecrunch.UtilityProject.utils.BaseAppActivity;
import co.codecrunch.demoutilityproject.libs.LibServer;

/**
 * Created by Jinesh Soni on 07/10/2016.
 */

//Make your own BAseActivity and extend BaseAppActivity
public class BaseActivity extends BaseAppActivity {

	LibServer libserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		libserver = new LibServer(this);
		setTAG("demo_log");

	}
}
