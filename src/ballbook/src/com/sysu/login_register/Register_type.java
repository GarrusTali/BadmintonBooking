package com.sysu.login_register;

import com.sysu.ballbook.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Register_type extends Activity {

	private ViewHolder mViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_type);
		setViewHolder(new ViewHolder());
	}

	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

	private class ViewHolder {
        private Button register_btn;
        private RadioButton isUser_rBtn;

		public ViewHolder() {
			// °ó¶¨¿Ø¼þ
			bindView();
			// Ìí¼Ó¼àÌýÆ÷
			bindListener();
		}

		private void bindView() {
			register_btn = (Button)findViewById(R.id.register_btn);
			isUser_rBtn = (RadioButton)findViewById(R.id.isUser_rBtn);
		}

		private void bindListener() {
			register_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		if(isUser_rBtn.isChecked())
	        		    intent.setClass(Register_type.this, Register_user.class);
	        		else
	        			intent.setClass(Register_type.this, Register_seller.class);
	        		//intent.putExtra("CMD", 1);    		
	        		startActivity(intent);
	        		Register_type.this.finish();
	        	}
	        });
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Register_type.this, LoginActivity.class);
		startActivity(intent);
		Register_type.this.finish();	
	}
}
