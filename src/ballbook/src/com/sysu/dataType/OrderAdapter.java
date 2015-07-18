package com.sysu.dataType;

import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;
import com.sysu.ballbook.R.string;
import com.sysu.login_register.LoginActivity;
import com.sysu.login_register.Register_type;
import com.sysu.userCenter.BookActivity;

import android.R.integer;
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

public class OrderAdapter extends BaseAdapter {

	private List<Order> mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public OrderAdapter(Context context, List<Order> orderList) {
		mContext = context;
		mList = orderList;
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
			convertView = mLayoutInflater.inflate(R.layout.order_item, parent,
					false);
		}
		Order order = (Order)getItem(position);
		TextView sellerName = (TextView)convertView.findViewById(R.id.name);
		TextView applyTime = (TextView)convertView.findViewById(R.id.applyTime);
		TextView positionMsg = (TextView)convertView.findViewById(R.id.positionList);
		sellerName.setText(order.getSeller().getName());
		applyTime.setText(order.getApplyTime());
		StringBuffer s = null;
		List<Position> positionList = order.getPositionList();	
		for(int i = 0; i < positionList.size(); i++)
		{
			List<Time> timeList = positionList.get(i).getTimeList();
			for(int j = 0; j < timeList.size(); j++)
			{
				Time time = timeList.get(j);
			    s.append("³¡ºÅ: "+i+" Ê±¼ä¶Î: " + toTime(time.getTime()) + "\n"); 
			}
		}
		positionMsg.setText(s.toString());
		return convertView;
	}
    
	public String toTime(int time)
	{
		return (time + 9 + ":00 - ") + (time+10+":00");
	}


}
