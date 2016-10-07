package co.codecrunch.UtilityProject.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import co.codecrunch.UtilityProject.R;
import co.codecrunch.UtilityProject.helper.NetworkConnectivity;
import co.codecrunch.UtilityProject.helper.NetworkHelper;
import co.codecrunch.UtilityProject.helper.PreferenceHelper;
import co.codecrunch.UtilityProject.permission.ActivityManagePermission;

/**
 * Created by Jinesh Soni on 15-08-2015.
 */
public class BaseAppActivity extends ActivityManagePermission {

	public static String TAG = "jinesh_log";
	private NetworkConnectivity networkConnectivity;
	Toolbar toolbar = null;
	Activity activity;
	ProgressDialog pDialog;
	public NetworkHelper networkHelper;
	public PreferenceHelper preferenceHelper;

	public static void setTAG(String tag){
		TAG = tag;
	}

	public void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null)
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	public int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public int pxToDp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	public void expand(final View v) {
		v.measure(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();

		// Older versions of android (pre API 21) cancel animations for views with a height of 0.
		v.getLayoutParams().height = 1;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? LinearLayoutCompat.LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static void backgroundThreadShortToast(final Context context, final String msg) {
		if (context != null && msg != null) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	public void setAnimation(View viewToAnimate, int anime) {
		viewToAnimate.startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), anime));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
	}

	@Override
	protected void onPause() {
		super.onPause();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = BaseAppActivity.this;
		networkConnectivity = new NetworkConnectivity(this);
		networkHelper=new NetworkHelper(this);
		preferenceHelper = new PreferenceHelper(this);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Processing Data . .");
		pDialog.setCancelable(false);


		if (!connectivityChecker())
			connectivityWarningDialog();

	}

	public void connectivityWarningDialog() {
		LinearLayout ll = new LinearLayout(BaseAppActivity.this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView tv = new TextView(BaseAppActivity.this);
		ll.setPadding(10, 10, 10, 10);
		tv.setText("No internet Connection Detected.Please connect to Internet in order to access Application");
		ll.addView(tv);
		tv.setPadding(10, 10, 10, 10);
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseAppActivity.this);
		alertDialogBuilder.setView(ll);
		alertDialogBuilder.setTitle("No Internet Connection");
		alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				activity.finish();
			}
		});
		alertDialogBuilder.show();
	}

	public Toolbar getToolbar() {
		return toolbar;
	}

	public Boolean connectivityChecker() {
		if (networkConnectivity.isConnected()) {
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "Please Connect to the Network", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	public void showSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}

	public void showDialog() {
		pDialog.show();
		hideSoftKeyboard(BaseAppActivity.this);
	}

	public void showDialog(String message) {
		pDialog.setMessage(message);
		pDialog.show();
		hideSoftKeyboard(BaseAppActivity.this);
	}

	public void showDialogFrag(View v, Activity activity) {
		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("Processing Data . .");
		pDialog.setCancelable(false);
		pDialog.show();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	public void showDialogFrag(View v, String message) {
		pDialog.setMessage(message);
		pDialog.show();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	public void dismissDialogFrag(View v) {
		if (pDialog.isShowing())
			pDialog.dismiss();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	public void dismissDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();

		hideSoftKeyboard(BaseAppActivity.this);
	}

	public Toolbar setToolbarWithoutArrow(String title, int id) {
		toolbar = (Toolbar) findViewById(id);
		toolbar.setTitle(title);
		toolbar.setTitleTextColor(getResources().getColor(R.color.white));
		toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

		return toolbar;
	}

	public Toolbar setToolbar(String title, int id) {
		toolbar = (Toolbar) findViewById(id);
		toolbar.setTitle(title);
		toolbar.setTitleTextColor(getResources().getColor(R.color.white));
		toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseAppActivity.this.onBackPressed();
			}
		});
		return toolbar;
	}

	public void mToast(String text) {
		backgroundThreadShortToast(getApplicationContext(), text);
	}

	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	public Snackbar mSnackbar(View view,final String msg){
		Snackbar snackbar = Snackbar
				.make(view, msg, Snackbar.LENGTH_LONG);

		snackbar.show();
		return snackbar;
	}
}

