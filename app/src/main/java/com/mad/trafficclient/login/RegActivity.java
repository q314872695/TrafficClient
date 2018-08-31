package com.mad.trafficclient.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mad.trafficclient.R;


public class RegActivity extends Activity {

	EditText etUserName, etUserPwd, etUserPwdSecond;
	Button btReg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		initView();
		initLsenter();
	}

	private void initView() {
		etUserName = (EditText) findViewById(R.id.et_user_name);
		etUserPwd = (EditText) findViewById(R.id.et_user_pwd);
		etUserPwdSecond = (EditText) findViewById(R.id.et_user_pwd_second);
		btReg = (Button) findViewById(R.id.bt_reg);
	}

	private void initLsenter() {
		btReg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
