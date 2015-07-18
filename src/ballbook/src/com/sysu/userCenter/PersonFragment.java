package com.sysu.userCenter;

import java.util.ArrayList;
import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;
import com.sysu.dataType.User;
import com.sysu.manager.CommandManager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class PersonFragment extends Fragment {

	private static PersonFragment mInstance = null;
	private View rootView;
	private ViewHolder mViewHolder;
	//储存用户id
    private int user_id;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_person, container,
				false);
		setViewHolder(new ViewHolder());
		return rootView;
	}
	private PersonFragment() {

	}

	public static PersonFragment getInstance() {
		if (mInstance == null)
			mInstance = new PersonFragment();
		return mInstance;
	}
	
	private class ViewHolder {
		private EditText username_et;
		private EditText name_et;
		private EditText phone_et;
		private Button edit_btn;
		private Button password_modify_btn;
		private boolean isModify;
		public ViewHolder() {
			bindView();
			bindListener();
			bindData();
		}

		private void bindView() {
			username_et = (EditText)rootView.findViewById(R.id.username_et);
			name_et = (EditText)rootView.findViewById(R.id.name_et);
			phone_et = (EditText)rootView.findViewById(R.id.phone_et);
			edit_btn = (Button)rootView.findViewById(R.id.edit_btn);
			password_modify_btn = (Button)rootView.findViewById(R.id.password_modify_btn);
		}

		private void bindData() {
			isModify = false;
			CommandManager.getInstance().getUser(PersonFragment.getInstance().getActivity(), rootView);
		}

		private void bindListener() {
			password_modify_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					onCreatedialog();		
				}				
			});
			edit_btn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(!isModify)
	        		{
	        			isModify = true;
		        		name_et.setFocusable(true);
		        		name_et.setFocusableInTouchMode(true);
		        		name_et.requestFocus();
		        		name_et.findFocus();
		        		phone_et.setFocusable(true);	
		        		phone_et.setFocusableInTouchMode(true);
		        		phone_et.requestFocus();
		        		phone_et.findFocus();
		        		edit_btn.setText("确定");
	        		} else {
	        			User user = new User(username_et.getText().toString(),
	        					name_et.getText().toString(),
	        					phone_et.getText().toString());
	        			CommandManager.getInstance().edit_user(PersonFragment.getInstance().getActivity(), rootView, user);	        			
	        		}
	        	}
	        });
		}
	}
	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

  
    public void onCreatedialog()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(PersonFragment.getInstance().getActivity());
    	LayoutInflater factory = LayoutInflater.from(PersonFragment.getInstance().getActivity());
    	final View contentView = factory.inflate(R.layout.password_modify, null);
    	builder.setTitle("修改密码");
    	builder.setView(contentView);
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
		    	EditText old_password_et =  (EditText)contentView.findViewById(R.id.old_password_et);
		    	EditText new_password1_et =  (EditText)contentView.findViewById(R.id.new_password1_et);
		    	EditText new_password2_et =  (EditText)contentView.findViewById(R.id.new_password2_et);
				String old_password = old_password_et.getText().toString();
				String new_password1 = new_password1_et.getText().toString();
				String new_password2 = new_password2_et.getText().toString();
				if(new_password1.equals(new_password2))
				{
					CommandManager.getInstance().edit_user_password(PersonFragment.getInstance().getActivity(), old_password, new_password1);
				} else {
					Toast.makeText(PersonFragment.this.getActivity(), "新密码错误（修改失败）", Toast.LENGTH_SHORT).show();
				}
			}		
    	});
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}		
    	});
    	builder.create().show();
    }
}
