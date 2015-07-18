package com.sysu.dataType;

import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;
import com.sysu.login_register.LoginActivity;
import com.sysu.login_register.Register_type;
import com.sysu.userCenter.BookActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SellerAdapter extends BaseAdapter {

	private List<Seller> mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public SellerAdapter(Context context, List<Seller> list) {
		mContext = context;
		mList = list;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.seller_item, parent,
					false);
		}
		final Seller seller = (Seller) getItem(position);
		TextView name = (TextView)convertView.findViewById(R.id.name);
		TextView phone = (TextView)convertView.findViewById(R.id.phone);
		TextView address = (TextView)convertView.findViewById(R.id.address);
		//TextView start_time = (TextView)convertView.findViewById(R.id.start_time);
		//TextView end_time = (TextView)convertView.findViewById(R.id.end_time);
		TextView introduction = (TextView)convertView.findViewById(R.id.introduction);
		name.setText(seller.getName());
		phone.setText("联系方式：" + seller.getPhone());
		address.setText("地址："+ seller.getAddress());
		//start_time.setText("营业时间："+ seller.getStart_time().toString());
		//end_time.setText(seller.getEnd_time().toString());
		introduction.setText("简介："+seller.getIntroduction());
		Button book_btn = (Button)convertView.findViewById(R.id.book_btn);
		book_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
        		intent.setClass(mContext, BookActivity.class); 	
        		intent.putExtra("ID", seller.getId());
        		mContext.startActivity(intent); 
			}		
		});
		return convertView;
	}



}
