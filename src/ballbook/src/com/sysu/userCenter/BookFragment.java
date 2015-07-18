package com.sysu.userCenter;


import java.util.List;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;
import com.sysu.dataType.Seller;
import com.sysu.dataType.SellerAdapter;
import com.sysu.manager.CommandManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BookFragment extends Fragment {
	
	private static BookFragment mInstance = null;
	private View rootView;
    private ViewHolder mViewHolder;
    private List<Seller> sellers;
	private BookFragment() {

	}

	public static BookFragment getInstance() {
		if (mInstance == null)
			mInstance = new BookFragment();
		return mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_book, container,
				false);
        setViewHolder(new ViewHolder());
		return rootView;
	}
	private class ViewHolder {
        private ListView sellerList; 
		public ViewHolder() {
			bindView();
			bindListener();
			bindData();
		}

		private void bindView() {
			sellerList = (ListView)rootView.findViewById(R.id.sellerList);
		}

		private void bindData() {
			CommandManager.getInstance().getSellers(getActivity(), sellerList);
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
