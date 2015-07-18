package com.sysu.login_register;

import com.sysu.ballbook.R;
import com.sysu.dataType.User;
import com.sysu.manager.CommandManager;
import com.sysu.userCenter.PersonFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_user extends Activity {

	private ViewHolder mViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_user);
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
        private EditText name_et;
        private EditText phone_et;
        private EditText password1_et;
        private EditText password2_et;
        private Button register_btn;
		public ViewHolder() {
			// °ó¶¨¿Ø¼þ
			bindView();
			// Ìí¼Ó¼àÌýÆ÷
			bindListener();
		}

		private void bindView() {
            username_et = (EditText)findViewById(R.id.username_et);
            name_et = (EditText)findViewById(R.id.name_et);
            phone_et = (EditText)findViewById(R.id.phone_et);
            password1_et = (EditText)findViewById(R.id.password1_et);
            password2_et = (EditText)findViewById(R.id.password2_et);
            register_btn = (Button)findViewById(R.id.register_btn);
            register_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        	}
	        });
		}

		private void bindListener() {
			register_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(password1_et.getText().toString().
	        				equals(password2_et.getText().toString()))
	        		{
	        			User user = new User(username_et.getText().toString(),
	        					name_et.getText().toString(),
	        					phone_et.getText().toString(),
	        					password1_et.getText().toString());

	        			CommandManager.getInstance().register_user(Register_user.this, user); 
	        		} else {
	        			//±¨´í£ºÃÜÂë´íÎó
	        			Toast.makeText(Register_user.this, "ÃÜÂë´íÎó", Toast.LENGTH_SHORT).show();
	        		}
	        	}
	        });
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Register_user.this, Register_type.class);
		startActivity(intent);
		Register_user.this.finish();	
	}
}
