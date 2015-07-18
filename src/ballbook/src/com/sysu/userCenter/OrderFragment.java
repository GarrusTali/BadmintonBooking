package com.sysu.userCenter;


import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;
import com.sysu.dataType.Order;
import com.sysu.dataType.OrderAdapter;
import com.sysu.dataType.Seller;
import com.sysu.dataType.SellerAdapter;
import com.sysu.manager.CommandManager;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class OrderFragment extends Fragment {
	
	private static OrderFragment mInstance = null;
	private View rootView;
    private ViewHolder mViewHolder;
	private OrderFragment() {

	}

	public static OrderFragment getInstance() {
		if (mInstance == null)
			mInstance = new OrderFragment();
		return mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order, container,
				false);
        setViewHolder(new ViewHolder());
		return rootView;
	}
	private class ViewHolder {
        private ListView orderList; 
        private List<Order> orders;
		public ViewHolder() {
			bindView();
			bindListener();
			bindData();
		}

		private void bindView() {
			orderList = (ListView)rootView.findViewById(R.id.orderList);
		}

		private void bindData() {
			CommandManager.getInstance().getOrders(getActivity(), orderList);
		}

		private void bindListener() {
		}
	}
	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

}
