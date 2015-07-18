package com.sysu.userCenter;

import com.sysu.ballbook.R;
import com.sysu.ballbook.R.drawable;
import com.sysu.ballbook.R.id;
import com.sysu.ballbook.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends Activity {

	private ViewHolder mViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setViewHolder(new ViewHolder());
		getFragmentManager().beginTransaction()
				.add(R.id.fragment_layout, BookFragment.getInstance()).commit();
	}

	public ViewHolder getViewHolder() {
		return mViewHolder;
	}

	public void setViewHolder(ViewHolder mViewHolder) {
		this.mViewHolder = mViewHolder;
	}

	private class ViewHolder {
		private Button[] tabButton = { null, null, null };
		private int[] buttonId = { R.id.btn_book, R.id.btn_order,
				R.id.btn_person };

		public ViewHolder() {
			// 绑定控件
			bindView();
			// 添加监听器
			bindListener();
		}

		private void bindView() {
			for (int i = 0; i < tabButton.length; i++) {
				tabButton[i] = (Button) findViewById(buttonId[i]);
			}
		}

		private void bindListener() {
			tabButton[0].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectTab(v);
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_layout,
									BookFragment.getInstance()).commit();
				}
			});

			tabButton[1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectTab(v);
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_layout,
									OrderFragment.getInstance()).commit();
				}
			});

			tabButton[2].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectTab(v);
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_layout,
									PersonFragment.getInstance()).commit();
				}
			});
		}

		// 选择相应的tab，并改变每个tab的颜色
		private void selectTab(View view) {
			for (int i = 0; i < tabButton.length; i++) {
				tabButton[i].setBackgroundDrawable(getResources().getDrawable(
						R.drawable.tab_notselected_background));
			}
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.tab_selected_background));
		}
	}
}
