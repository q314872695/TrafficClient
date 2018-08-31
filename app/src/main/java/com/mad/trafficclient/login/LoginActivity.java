package com.mad.trafficclient.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mad.trafficclient.MainActivity;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.LoadingDialog;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity  {

	private UrlBean urlBean;
	private String urlHttp;
	private String urlPort = "8080";
	
	EditText etUserName, etUserPwd;
	Button btLogin, btReg, btNetSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initLiserter();
	}

	private void initView() {
		// TODO Auto-generated method stub
		etUserName = (EditText) findViewById(R.id.et_user_name);
		etUserPwd = (EditText) findViewById(R.id.et_user_pwd);
		btLogin = (Button) findViewById(R.id.bt_login);
		btReg = (Button) findViewById(R.id.bt_reg);
		btNetSetting = (Button) findViewById(R.id.bt_net_setting);

        urlBean = Util.loadSetting( LoginActivity.this );

	}
	
	private void initLiserter() {
		// TODO Auto-generated method stub
		btReg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegActivity.class);
				startActivity(intent);
			}
		});

		btLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String userName = etUserName.getText().toString();
				String userPwd = etUserPwd.getText().toString();

				LoadingDialog.showDialog( LoginActivity.this);
				JSONObject params = new JSONObject();
				try {
					params.put("UserName", userName);
                    params.put("UserPwd", userPwd);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("TAG", params.toString());

				//fujian
				String strUrl = "http://"+ urlBean.getUrl() +":" + urlBean.getPort() + "/api/v2/user_login";


				Log.d("TAG", strUrl);

				RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, strUrl, params, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stu
						Log.d("TAG", response.toString());
						LoadingDialog.disDialog();
						if ( response.optString("RESULT").equals("S")){
							Toast.makeText(getApplicationContext(), response.optString("ERRMSG"), Toast.LENGTH_LONG).show();

							Intent intent = new Intent(LoginActivity.this,	MainActivity.class);
							startActivity(intent);
							finish();
						}else if ( response.optString("RESULT").equals("F")) {
							Toast.makeText(getApplicationContext(), response.optString("ERRMSG"), Toast.LENGTH_LONG).show();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						LoadingDialog.disDialog();
                        Log.d("TAG volley error", error.toString());
						Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

					}
				});
				mQueue.add(jsonObjectRequest);
			}
		});
		
		btNetSetting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog urlSettingDialog = new Dialog(LoginActivity.this);
				urlSettingDialog.show();
				urlSettingDialog.setTitle("Setting");
				urlSettingDialog.getWindow().setContentView(R.layout.login_setting);
				final EditText edit_urlHttp = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_url);
				final EditText edit_urlPort = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_port);

				edit_urlHttp.setText( urlBean.getUrl() );
				edit_urlPort.setText( urlBean.getPort());
				Button save = (Button) urlSettingDialog.getWindow().findViewById(R.id.save);
				Button cancel = (Button) urlSettingDialog.getWindow().findViewById(R.id.cancel);
				save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						urlHttp = edit_urlHttp.getText().toString();
						urlPort = edit_urlPort.getText().toString();

						if ( urlHttp == null || urlHttp.equals("")   ) {
							Toast.makeText(LoginActivity.this,R.string.error_ip, Toast.LENGTH_LONG).show();
						} else {
							Util.saveSetting(urlHttp,urlPort,LoginActivity.this);
                            urlBean = Util.loadSetting( LoginActivity.this );
							urlSettingDialog.dismiss();
						}
					}
				});
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						urlSettingDialog.dismiss();
					}
				});
				urlSettingDialog.show();
			}
		});
	
	}

}
