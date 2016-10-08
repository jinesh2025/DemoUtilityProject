package co.codecrunch.demoutilityproject;

import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.widget.Toolbar;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

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



		//EXAMPLE Network

		//Params - key,value
		List<ParamChild> param = new ArrayList<>();
		param.add(new ParamChild("key", "value"));


		List<ParamChild> paramImage = new ArrayList<>();
		paramImage.add(new ParamChild("key", "media_image_path"));

		//OKHTTP Zap Connectors
		//Fast GET
		networkHelper.zapGet("url", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {

			}
		});

		//Fast POST
		networkHelper.zapPost("url", new NetworkHelper.VolleyCallback() {
			@Override
			public void onSuccess(Boolean success, String data) {

			}
		},param);

		//Volley for queue management, using OKHTTP as HTTP connector over top of volley for fast connections

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

		 networkHelper.volleyGet("url", param ,new NetworkHelper.VolleyCallback() {
			 @Override
			 public void onSuccess(Boolean success, String data) {

			 }
		 });

		 networkHelper.volleyPost("url", param ,new NetworkHelper.VolleyCallback() {
			 @Override
			 public void onSuccess(Boolean success, String data) {

			 }
		 });

		// more in networkHelper

		//Preference helper
		preferenceHelper.saveStringPreferences("key","value");
		String val = preferenceHelper.loadSavedStringPreferences("key");

		preferenceHelper.saveBooleanPreferences("key",true);
		Boolean aBoolean = preferenceHelper.loadSavedBooleanPreferences("key");

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

		//Caching and settign image to imageview fom network
		//UrlImageViewHelper.setUrlDrawable(imageview,"url",R.drawable.loading_img);


	}
}
