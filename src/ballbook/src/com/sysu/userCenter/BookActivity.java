package com.sysu.userCenter;

import java.util.ArrayList;
import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.dataType.ItemAdapter;
import com.sysu.dataType.Order;
import com.sysu.dataType.Seller;
import com.sysu.manager.CommandManager;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.NumberPicker.OnValueChangeListener;

public class BookActivity extends Activity {

	private ViewHolder mViewHolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.book);
		setViewHolder(new ViewHolder());
	}

	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

	private class ViewHolder {
		private NumberPicker timePicker;
		private GridView gridView;
		private TextView name;
		private TextView phone;
		private TextView address;
		private TextView introduction;
		private Button timechangeBtn;
		private Button submitBtn;
        
		private Boolean isChanging = false;
		
		public ViewHolder() {
			// 绑定控件
			bindView();
			//绑定数据
			bindData();
			// 添加监听器
			bindListener();
		}

		private void bindView() {
			timePicker = (NumberPicker)findViewById(R.id.timepicker);
            name = (TextView)findViewById(R.id.name);
            phone = (TextView)findViewById(R.id.phone);
            address = (TextView)findViewById(R.id.address);
            introduction = (TextView)findViewById(R.id.introduction);
            timechangeBtn = (Button)findViewById(R.id.timechangeBtn);
            submitBtn = (Button)findViewById(R.id.submit_btn);
            
		}
		private void bindData() {	
			//获取并显示商家信息
			final String id = BookActivity.this.getIntent().getExtras().getString("ID");
			CommandManager.getInstance().getSeller(BookActivity.this, id );
			timePicker.setMinValue(9);
			timePicker.setMaxValue(21);
			timePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			timePicker.setEnabled(false);
			//用于记录当前选择的场地号
			ChooseList.getInstance().chooseList.clear();
			CommandManager.getInstance().getBook_detail(BookActivity.this, id, 9);
		}
		private void bindListener() {
			timechangeBtn.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if(isChanging)
					{
						timechangeBtn.setText("重选");
						isChanging = false;
						timePicker.setEnabled(false);
						final String id = BookActivity.this.getIntent().getExtras().getString("ID");
						ChooseList.getInstance().chooseList.clear();
						CommandManager.getInstance().getBook_detail(BookActivity.this, id, timePicker.getValue());						
					} else {
						timechangeBtn.setText("确定");
						timePicker.setEnabled(true);
						isChanging = true;
						timePicker.setActivated(true);
					}
				}
			});
			submitBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(ChooseList.getInstance().chooseList.size() == 0)
					{
						Toast.makeText(BookActivity.this, "当前已选场地数为0", Toast.LENGTH_SHORT).show();	
					} else {
						StringBuffer s = new StringBuffer();
						List<Integer> list = ChooseList.getInstance().chooseList;		
						for(int i = 0; i < list.size(); i++)
						{
							s.append((list.get(i)).toString());
							if(i != list.size() - 1)
							   s.append(",");
						}				    
					    //向服务器提交申请订单
					    String stadium_id = BookActivity.this.getIntent().getExtras().getString("ID");
					    int time = timePicker.getValue();
					    String position = s.toString();
					    CommandManager.getInstance().sendOrder(BookActivity.this, stadium_id, time, position);	
					}		  				    
				}
			});
			
		}
	}
	
}
