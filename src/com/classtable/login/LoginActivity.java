package com.classtable.login;

import java.util.TreeSet;

import com.classtable.format2.CourseObject;
import com.classtable.login.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button logoin;
	private EditText userNameView;
	private EditText passwordView;
	private CheckBox isRememberPasswordView;
	private boolean isRememberPassword;
	private String password;
	private String userName;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		Toast.makeText(getApplicationContext(), "请初始化个人信息", 2000).show();
		SharedPreferences preferences = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		logoin = (Button) findViewById(R.id.login_btn_login);
		userNameView = (EditText) findViewById(R.id.login_edit_account);
		userNameView.setText(preferences.getString("userName", ""));
		passwordView = (EditText) findViewById(R.id.login_edit_pwd);
		isRememberPasswordView = (CheckBox) findViewById(R.id.login_cb_savepwd);

		logoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userName = userNameView.getText().toString().trim();
				password = passwordView.getText().toString().trim();
				isRememberPassword = isRememberPasswordView.isChecked();
				if(userName.equals("")){
					Toast.makeText(LoginActivity.this, "请输入学号", 2000).show();
				}else if(password.equals("")){
					Toast.makeText(LoginActivity.this, "请输入密码", 2000).show();
				}else{
					
					Intent toTable = new Intent();
					toTable.putExtra("userName", userName);
					toTable.putExtra("password", password);
					toTable.putExtra("isRememberPassword", isRememberPassword);
					toTable.setClass(LoginActivity.this, NetConnecting.class);
					startActivity(toTable);
					finish();
				}
			}
		});
	}

}
