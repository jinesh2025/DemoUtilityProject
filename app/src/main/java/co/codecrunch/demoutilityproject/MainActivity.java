package co.codecrunch.demoutilityproject;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import co.codecrunch.UtilityProject.cutomvolleyutils.ParamChild;
import co.codecrunch.UtilityProject.helper.NetworkHelper;
import co.codecrunch.UtilityProject.permission.PermissionResult;
import co.codecrunch.UtilityProject.permission.PermissionUtils;
import co.codecrunch.UtilityProject.utils.BaseAppActivity;
import co.codecrunch.UtilityProject.utils.Logger;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//--To set toolbar
		//setToolbar("Title",R.id.toolbar);

		//--To get toolbar
		//Toolbar toolbar = getToolbar();

		//hide keboard
		hideSoftKeyboard(this);

		//show keyboard -> to set view
		//showSoftKeyboard(rootview);

		//sackbar -> setview & msg
		//mSnackbar(view,msg);

		//Easy logger that only show logs in debug and not in production builds
		Logger.i("Hello this is sample tag");

		//Expand / collapse view - animation to view
		/*
		expand(view);
		collapse(view);
		setAnimation(view,R.anim.accelerate);
		*/



		//EXAMPLE Netowrk
		List<ParamChild> paramImage = new ArrayList<>();
		paramImage.add(new ParamChild("key", "media_image_path"));


		List<ParamChild> param = new ArrayList<>();
		param.add(new ParamChild("key", "value"));

		networkHelper.zapGet("url", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {

			}
		}, param);

		//For Image Upload
		networkHelper.volleyMultipartImageUpload("url", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {

			}
		}, new NetworkHelper.ProgressCallback() {
			@Override
			public void onProgress(int percentage) {

			}
		}, param, paramImage);

		//More volley example
		 networkHelper.volleyGet("url", new NetworkHelper.VolleyCallback() {
			 @Override
			 public void onSuccess(Boolean success, String data) {

			 }
		 });

		// more in networkHelper

		//Preference helper
		preferenceHelper.saveStringPreferences("key","value");
		String val = preferenceHelper.loadSavedStringPreferences("key");

		//check Permission
		Boolean permCheck = isPermissionGranted(getApplicationContext(), PermissionUtils.Manifest_CAMERA);
		if(!permCheck){
			askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
				@Override
				public void permissionGranted() {

				}

				@Override
				public void permissionDenied() {

				}
			});
		}

		//From Base Act

		//easy progressbar
		showDialog("Processing");

		libserver.login("myuserid", "mypassword", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {
				dismissDialog();
				if(success)
					mToast("Success");
			}
		});

		//Fore more option look BaseAppActivity
		BaseAppActivity baseAppActivity;



	}
}
