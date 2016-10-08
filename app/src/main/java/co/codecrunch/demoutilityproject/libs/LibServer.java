package co.codecrunch.demoutilityproject.libs;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import co.codecrunch.UtilityProject.cutomvolleyutils.ParamChild;
import co.codecrunch.UtilityProject.helper.NetworkHelper;

/**
 * Created by Jinesh Soni on 07/10/2016.
 */

public class LibServer {

	Activity activity;
	NetworkHelper networkHelper;

	public LibServer(Activity activity) {
		this.activity = activity;
		networkHelper = new NetworkHelper(activity);
	}

	//EXAMPLE
	public void login(String id, String pass, final NetworkHelper.VolleyCallback callback){
		List<ParamChild> param = new ArrayList<>();
		param.add(new ParamChild("id", id));
		param.add(new ParamChild("pass", pass));

		networkHelper.zapPost("url", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {
				callback.onSuccess(success,data);
			}
		}, param);

	}
}
