package co.codecrunch.UtilityProject.helper;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.codecrunch.UtilityProject.cutomvolleyutils.CountingRequestBody;
import co.codecrunch.UtilityProject.cutomvolleyutils.CustomMultiPartBody;
import co.codecrunch.UtilityProject.cutomvolleyutils.OkHttpStack;
import co.codecrunch.UtilityProject.cutomvolleyutils.ParamChild;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jinesh Soni on 06/10/2016.
 */

public class NetworkHelper {

	private static final String TAG = "jinesh_util_log";

	private Activity activity;

	public NetworkHelper(Activity activity) {
		this.activity = activity;
	}

	private Call postOkhttp(OkHttpClient client, String url, RequestBody formBody, Callback callback) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();
		Call call = client.newCall(request);
		call.enqueue(callback);
		return call;
	}

	private Call getOkhttp(OkHttpClient client, String url, Callback callback) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		Call call = client.newCall(request);
		call.enqueue(callback);
		return call;
	}

	public void zapGet(String url,
	                   final VolleyCallback callback) {
		OkHttpClient client = new OkHttpClient();
		try {
			getOkhttp(client, url, new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							callback.onSuccess(false, null);
						}
					});
				}

				@Override
				public void onResponse(Call call, final Response response) throws IOException {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								callback.onSuccess(true, response.body().string());
							} catch (IOException e) {
								e.printStackTrace();
								callback.onSuccess(false, null);
							}
						}
					});
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void zapPost(String url,
	                    final VolleyCallback callback,
	                    final List<ParamChild> params) {
		OkHttpClient client = new OkHttpClient();

		RequestBody requestBody = new CustomMultiPartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart(params)
				.build();
		try {
			postOkhttp(client, url, requestBody, new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							callback.onSuccess(false, null);
						}
					});
				}

				@Override
				public void onResponse(Call call, final okhttp3.Response response) throws IOException {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								callback.onSuccess(true, response.body().string());
							} catch (IOException e) {
								e.printStackTrace();
								callback.onSuccess(false, null);
							}
						}
					});
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void volleyGet(String url,
	                      final VolleyCallback callback) {
		RequestQueue queue = Volley.newRequestQueue(activity, new OkHttpStack());
		JsonObjectRequest jsonObjectRequest;
		jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET
				, url, null, new com.android.volley.Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				callback.onSuccess(true, response.toString());
			}
		}, new com.android.volley.Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onSuccess(false, null);
			}
		});


		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 2));
		queue.add(jsonObjectRequest);

	}

	public void volleyGet(String url,
	                      final List<ParamChild> dataParam,
	                      final VolleyCallback callback) {
		RequestQueue queue = Volley.newRequestQueue(activity, new OkHttpStack());
		StringRequest request = new StringRequest(com.android.volley.Request.Method.GET
				, url, new com.android.volley.Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					callback.onSuccess(true, response);
				} catch (Exception e) {
					e.printStackTrace();
					callback.onSuccess(false, null);
				}
			}
		}, new com.android.volley.Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onSuccess(false, null);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<>();
				for (int i = 0; i < dataParam.size(); i++)
					params.put(dataParam.get(i).getKey(), dataParam.get(i).getValue());
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 2));
		queue.add(request);
	}

	public void volleyPost(String url,
	                       final List<ParamChild> dataParam,
	                       final VolleyCallback callback) {
		RequestQueue queue = Volley.newRequestQueue(activity, new OkHttpStack());
		StringRequest request = new StringRequest(com.android.volley.Request.Method.POST
				, url, new com.android.volley.Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					callback.onSuccess(true, response);
				} catch (Exception e) {
					e.printStackTrace();
					callback.onSuccess(false, null);
				}
			}
		}, new com.android.volley.Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onSuccess(false, null);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<>();
				for (int i = 0; i < dataParam.size(); i++)
					params.put(dataParam.get(i).getKey(), dataParam.get(i).getValue());
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 2));
		queue.add(request);
	}

	public void volleyMultipartImageUpload(String url,
	                                       final VolleyCallback callback,
	                                       final ProgressCallback callbackProgress,
	                                       final List<ParamChild> params,
	                                       final List<ParamChild> paramsImages) {


		OkHttpClient client = new OkHttpClient();

		RequestBody requestBody = new CustomMultiPartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart(params)
				.addFormDataPartMedia(paramsImages)
				.build();

		// Decorate the request body to keep track of the upload progress
		CountingRequestBody countingBody = new CountingRequestBody(requestBody,
				new CountingRequestBody.Listener() {

					@Override
					public void onRequestProgress(long bytesWritten, long contentLength) {
						final float percentage = 100f * bytesWritten / contentLength;
						Log.v(TAG, "per - " + percentage);

						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								callbackProgress.onProgress((int) percentage);
							}
						});

					}
				});

		try {
			postOkhttp(client, url, countingBody, new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							callback.onSuccess(false, null);
						}
					});
				}

				@Override
				public void onResponse(Call call, final okhttp3.Response response) throws IOException {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								callback.onSuccess(true, response.body().string());
							} catch (IOException e) {
								e.printStackTrace();
								callback.onSuccess(false, null);
							}
						}
					});
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface VolleyCallback {
		void onSuccess(Boolean success, String data);
	}

	public interface ProgressCallback {
		void onProgress(int percentage);
	}
}
