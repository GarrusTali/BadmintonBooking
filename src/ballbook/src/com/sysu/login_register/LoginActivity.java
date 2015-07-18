package com.sysu.login_register;

import com.sysu.ballbook.R;
import com.sysu.manager.CommandManager;
import com.sysu.userCenter.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity {

	private ViewHolder mViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		setViewHolder(new ViewHolder());
	}

	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

	private class ViewHolder {
        private EditText username_et;
        private EditText password_et;
        private CheckBox isRemenber_cb;
        private Button login_btn;
        private Button register_btn;
        private Button password_search_btn;
        private SharedPreferences sp;
		public ViewHolder() {
			// 绑定控件
			bindView();
			//绑定数据
			bindData();
			// 添加监听器
			bindListener();
		}

		private void bindView() {
			username_et = (EditText)findViewById(R.id.username_et);
			password_et = (EditText)findViewById(R.id.password_et);
			isRemenber_cb = (CheckBox)findViewById(R.id.isremenber_cb);
			login_btn = (Button)findViewById(R.id.login_btn);
			register_btn = (Button)findViewById(R.id.register_btn);
			password_search_btn = (Button)findViewById(R.id.password_search_btn);
		}
		private void bindData() {
			sp = getSharedPreferences("userIfo", Context.MODE_PRIVATE);
			if(sp.getBoolean("ISCHECK", false))
			{
				username_et.setText(sp.getString("USER_NAME", ""));
				password_et.setText(sp.getString("PASSWORD", "")); 
			}    	
		}
		private void bindListener() {
			isRemenber_cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	        	public void onCheckedChanged(CompoundButton arg0,boolean arg1){
	        		if(isRemenber_cb.isChecked()){
	        			sp.edit().putBoolean("ISCHECK", true).commit();
	        		} else {
	        			sp.edit().putBoolean("ISCHECK", false).commit();
	        		}
	        	}
	        });
			
			login_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		CommandManager.getInstance().login(LoginActivity.this, username_et.getText().toString(),password_et.getText().toString());
	        	}
	        });
			register_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		intent.setClass(LoginActivity.this, Register_type.class); 		
	        		startActivity(intent);
	        		LoginActivity.this.finish();
	        	}
	        });
			password_search_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){

	        	}
	        });
		}
	}
	
}
