package com.sysu.dataType;


import java.util.ArrayList;
import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.manager.CommandManager;
import com.sysu.userCenter.BookActivity;




import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends BaseAdapter {

	private List<Unit> mList;
	private Context mContext;
	private List<Position> positionList;
	private TextView msg;
	private Button submit_btn;
	private LayoutInflater mLayoutInflater;
	
	public ItemAdapter(Context context, List<Unit> list) {
		mContext = context;
		mList = list;
		positionList = new ArrayList<Position>();		
		msg = (TextView)((Activity)mContext).findViewById(R.id.msg_tv);	
		submit_btn = (Button)((Activity)mContext).findViewById(R.id.submit_btn);
		submit_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                String id = ((Activity)mContext).getIntent().getExtras().getString("ID");
				CommandManager.getInstance().sendOrder(mContext, id, positionList);
			}
		});
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
			convertView = mLayoutInflater.inflate(R.layout.unit_item, parent,
					false);
		}
		final Unit unit = (Unit) getItem(position);
        TextView unit_btn = (TextView)convertView.findViewById(R.id.unit_btn);
        if(!unit.isEmpty())
        {
        	unit_btn.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
        	unit_btn.setClickable(false);
        } else {
        	unit_btn.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            unit_btn.setOnClickListener(new OnClickListener() {	
    			@Override
    			public void onClick(View v) {
    				if(unit.isEmpty())
    				{
    					add(unit);
    					unit.setEmpty(1);
    				    v.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
    					Toast.makeText(mContext, "申请预订：  场地:"+unit.getId()+"号场     时间"+unit.getTime() +":00~" + (unit.getTime()+1) + ":00", Toast.LENGTH_SHORT).show();
    				} else {
    					cancel(unit);
    					unit.setEmpty(0);
    				    v.setBackgroundColor(mContext.getResources().getColor(R.color.green));
    					Toast.makeText(mContext, "取消预订：  场地:"+unit.getId()+"号场     时间"+unit.getTime() +":00~" + (unit.getTime()+1) + ":00", Toast.LENGTH_SHORT).show();	
    				}
    				
    			}
    		});
        }
		return convertView;
	}
	
	public void add(Unit unit)
	{
		for(int i = 0; i < positionList.size(); i++)
		{
			if(positionList.get(i).getPosition() == unit.getId())
			{
				positionList.get(i).getTimeList().add(new Time(unit.getTime(),unit.getEmpty()));
				return ;
			}
		}
		Position position = new Position();
		position.setPositon(unit.getId());
		position.getTimeList().add(new Time(unit.getTime(),unit.getEmpty()));
		positionList.add(position);
	}
    public void cancel(Unit unit)
    {
		for(int i = 0; i < positionList.size(); i++)
		{
			if(positionList.get(i).getPosition() == unit.getId())
			{
				List<Time> timeList = positionList.get(i).getTimeList();
				for(int j = 0; j < timeList.size(); j++)
				{
					if(timeList.get(j).getTime() == unit.getTime())
					{
						positionList.get(i).getTimeList().remove(j);
						if(positionList.get(i).getTimeList().size() == 0)
						{
							positionList.remove(i);
						}
					}
				}
			}
		}	
    }
    public void UpdateMsg()
    {
    	StringBuffer s = null;
    	for(int i = 0; i < positionList.size(); i++)
    	{
    		s.append(positionList.get(i).getPosition() + "号场:\n");
    		List<Time> timeList = positionList.get(i).getTimeList(); 		
    		for(int j = 0; j < timeList.size(); j++)
    		{
    			s.append(toTime(timeList.get(j).getTime())+ " ");
    		}
    		s.append("\n");
    	}
    	msg.setText(s.toString());
    }
	public String toTime(int time)
	{
		return (time + 9 + ":00 - ") + (time+10+":00");
	}
}
