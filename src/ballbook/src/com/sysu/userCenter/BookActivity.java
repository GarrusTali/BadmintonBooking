package com.sysu.userCenter;

import java.util.ArrayList;
import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.dataType.ItemAdapter;
import com.sysu.dataType.Seller;
import com.sysu.dataType.Unit;
import com.sysu.manager.CommandManager;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class BookActivity extends Activity {

	private ViewHolder mViewHolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		private GridView gridView;
		private TextView name;
		private TextView phone;
		private TextView address;
		private TextView introduction;
	    //记录当前选择场次
		private TextView msg; 	
		
		public ViewHolder() {
			// 绑定控件
			bindView();
			//绑定数据
			bindData();
			// 添加监听器
			//bindListener();
		}

		private void bindView() {
            gridView = (GridView)findViewById(R.id.gridView);
            name = (TextView)findViewById(R.id.name);
            phone = (TextView)findViewById(R.id.phone);
            address = (TextView)findViewById(R.id.address);
            introduction = (TextView)findViewById(R.id.introduction);
            msg = (TextView)findViewById(R.id.msg_tv);
		}
		private void bindData() {	
			String id = BookActivity.this.getIntent().getExtras().getString("ID");
			CommandManager.getInstance().getOrderDetail(BookActivity.this, id );
			//显示特定商家的信息
			//wait
		}
		private void bindListener() {
			
		}
	}
	
}
