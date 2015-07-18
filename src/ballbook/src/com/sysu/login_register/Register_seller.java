package com.sysu.login_register;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sysu.ballbook.R;
import com.sysu.dataType.Seller;
import com.sysu.manager.CommandManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

public class Register_seller extends Activity {

	private ViewHolder mViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_seller);
		setViewHolder(new ViewHolder());
	}

	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

	private class ViewHolder {
		private EditText sellername_et;
		private EditText name_et;
		private EditText phone_et;
		private EditText address_et;
		private EditText introduction_et;
		private EditText ball_park_number_et;
        private Button register_btn;
        private EditText password1_et;
        private EditText password2_et;
		public ViewHolder() {
			// �󶨿ؼ�
			bindView();
			// ��Ӽ�����
			bindListener();
		}

		private void bindView() {
			sellername_et = (EditText)findViewById(R.id.sellername_et);
			name_et = (EditText)findViewById(R.id.name_et);
			phone_et = (EditText)findViewById(R.id.phone_et);
			address_et = (EditText)findViewById(R.id.address_et);
			introduction_et = (EditText)findViewById(R.id.introduction_et);
			ball_park_number_et = (EditText)findViewById(R.id.ball_park_number_et);
			password1_et = (EditText)findViewById(R.id.password1_et);
            password2_et = (EditText)findViewById(R.id.password2_et);
			register_btn = (Button)findViewById(R.id.register_btn);
		}

		private void bindListener() {
			register_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(password1_et.getText().toString().
	        				equals(password2_et.getText().toString()))
	        		{
	        			if(ball_park_number_et.getText().toString().matches("\\d+"))
	        			{
		        			Seller seller = new Seller(sellername_et.getText().toString(),
		        					name_et.getText().toString(),
		        					phone_et.getText().toString(),
		        					address_et.getText().toString(),
		        					introduction_et.getText().toString(),
		        					password1_et.getText().toString(),
		        					Integer.parseInt(ball_park_number_et.getText().toString()));	
                            CommandManager.getInstance().register_seller(Register_seller.this, seller);
	        			} else {
	        				//����:��������Ϊ���ڵ���0������
	        				Toast.makeText(Register_seller.this, "��������Ϊ����0������", Toast.LENGTH_SHORT).show();
	        			}	
	        		} else {
	        			//�����������
	        			Toast.makeText(Register_seller.this, "�������", Toast.LENGTH_SHORT).show();
	        		}
	        	}
	        });
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Register_seller.this, Register_type.class);
		startActivity(intent);
		Register_seller.this.finish();	
	}
}
