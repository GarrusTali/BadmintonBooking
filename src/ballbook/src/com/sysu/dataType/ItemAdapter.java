package com.sysu.dataType;


import java.util.ArrayList;
import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.manager.CommandManager;
import com.sysu.userCenter.BookActivity;
import com.sysu.userCenter.ChooseList;




import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.style.UpdateAppearance;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends BaseAdapter {

	private Context mContext;
	private List<Integer> positionList;
	private LayoutInflater mLayoutInflater;
	
	public ItemAdapter(Context context, List<Integer> list) {
		mContext = context;
		positionList = list;	
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return positionList.size();
	}

	@Override
	public Object getItem(int position) {
		return positionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.position_item, parent,
					false);
		}
		//场地预订标志
		final Integer isbook = (Integer)getItem(position);
		final Integer position_ = position;
        Button unit_btn = (Button)convertView.findViewById(R.id.unit_btn);
        if(isbook == 0 || ChooseList.getInstance().current_hour > ChooseList.getInstance().ask_hour)
        {
        	unit_btn.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
        	unit_btn.setClickable(false);
        } else {
        	unit_btn.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            unit_btn.setOnClickListener(new OnClickListener() {	
    			@Override
    			public void onClick(View v) {
    				if(positionList.get(position_) == 1)
    				{
    					if(ChooseList.getInstance().chooseList.size() < 4)
    					{
        					ChooseList.getInstance().chooseList.add(position_);//添加到已选列表
        					positionList.set(position_, 0 );
        				    v.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
    					} else {
        				    Toast toast = Toast.makeText(mContext,"亲~最多只能订4个场地",Toast.LENGTH_SHORT);
        				    toast.setGravity(Gravity.CENTER, 0, 0);
        				    toast.show();
						}
    				} else {
    					ChooseList.getInstance().chooseList.remove(position_);
    					positionList.set(position_, 1);
    				    v.setBackgroundColor(mContext.getResources().getColor(R.color.green));
    				    //Toast toast = Toast.makeText(mContext,"取消"+position_+"号场地",Toast.LENGTH_SHORT);
    				    //toast.setGravity(Gravity.CENTER, 0, 0);
    				    //toast.show();
    				}
    				
    			}
    		});
        }
        unit_btn.setText(""+ position);
		return convertView;
	}
	
	public String toTime(int time)
	{
		return (time + 9 + ":00 - ") + (time+10+":00");
	}
}
