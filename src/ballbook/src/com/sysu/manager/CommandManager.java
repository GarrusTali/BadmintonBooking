package com.sysu.manager;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.sysu.ballbook.R;
import com.sysu.dataType.ItemAdapter;
import com.sysu.dataType.Order;
import com.sysu.dataType.OrderAdapter;
import com.sysu.dataType.Seller;
import com.sysu.dataType.SellerAdapter;
import com.sysu.dataType.User;
import com.sysu.login_register.LoginActivity;
import com.sysu.login_register.Register_user;
import com.sysu.userCenter.BookActivity;
import com.sysu.userCenter.ChooseList;
import com.sysu.userCenter.MainActivity;
import com.sysu.userCenter.PersonFragment;

public class CommandManager {
	private static CommandManager mInstance = null;
    //private String ip = "http://172.18.34.94:8000";
	private String ip = "http://ballbook.sinaapp.com";
	private CommandManager() {

	}

	static public CommandManager getInstance() {
		if (mInstance == null)
			mInstance = new CommandManager();
		return mInstance;
	}
	public void login(final Context mContext , final String username,final  String password)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在登录······");
		final AlertDialog mDialog = builder.create();
		mDialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	 
				mDialog.cancel();
				if(!result.equals("login falied!!"))
        		{					
					SharedPreferences sp = mContext.getSharedPreferences("userIfo", Context.MODE_PRIVATE);
        			if(sp.getBoolean("ISCHECK", true))
        			{
	        			Editor editor = sp.edit();
        				editor.putString("USER_NAME", username);
        				editor.putString("PASSWORD", password);
        				editor.commit();
        			} else {
        				Editor editor = sp.edit();
        				editor.putString("USER_NAME", "");
        				editor.putString("PASSWORD", "");
        				editor.commit();
        			}
    				Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
	        		Intent intent = new Intent();
	        		//根据登录的用户类型来进入相应的个人中心，后期加
	        		intent.setClass(mContext, MainActivity.class); 		
	        		mContext.startActivity(intent);
	        		((Activity) mContext).finish();		      		
        		} else {
        			//输出错误信息
        			Toast.makeText(mContext, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
        		}
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				mDialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_LONG).show();		
			}
		};
		final String url = ip+"/login/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		mDialog.show();
		manager.NetConnection(successCallback,failCallback, url, httpMethod, "username",username,"password",password);	
	}
	
	public void register_user(final Context mContext, User user)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在注册······");
		final AlertDialog mDialog = builder.create();
		mDialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){

			@Override
			public void onSuccess(String result) {
				mDialog.cancel();
				// TODO Auto-generated method stub	
				if(result.equals("regist success!!"))
    			{
					Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
	        		Intent intent = new Intent();
	        		intent.setClass(mContext, LoginActivity.class);   		
	        		mContext.startActivity(intent);
	        		((Activity) mContext).finish();	
    			} else if(result.equals("username exists")) {
    				//报错：用户名已存在
    				Toast.makeText(mContext, "该用户名已存在", Toast.LENGTH_SHORT).show();
    			} else if(result.equals("empty data exist")) {
    				//报错：存在未填的属性
    				Toast.makeText(mContext, "存在未填的属性", Toast.LENGTH_SHORT).show();
    			} else if(result.equals("password diff")) {
    				//报错：存在未填的属性
    				Toast.makeText(mContext, "密码与确认密码不相同", Toast.LENGTH_SHORT).show();
    			} else {
    				//报错
    				Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
    			}   
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				mDialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};
		final String url = ip+"/regist/ordinary/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		mDialog.show();
		manager.NetConnection(successCallback,failCallback,url, httpMethod, 
				             "username",user.getUsername(),
				             "realname",user.getName(),
				             "phonenumber",user.getPhone(),
				             "password",user.getPassword(),
				             "confirm_password",user.getPassword());	
	}
	
	public void register_seller(final Context mContext, Seller seller)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在注册······");
		final AlertDialog mDialog = builder.create();
		mDialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){

			@Override
			public void onSuccess(String result) {
				mDialog.cancel();
				// TODO Auto-generated method stub	
				if(result.equals("regist success!!"))
    			{
					Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
	        		Intent intent = new Intent();
	        		intent.setClass(mContext, LoginActivity.class); 		
	        		mContext.startActivity(intent);
	        		((Activity) mContext).finish();	
    			} else if(result.equals("username exists")){
    				//报错：用户名已存在
    				Toast.makeText(mContext, "该用户名已存在！！", Toast.LENGTH_SHORT).show();
    			} else if(result.equals("empty data exist")) {
    				//报错：存在未填的属性
    				Toast.makeText(mContext, "存在未填的属性", Toast.LENGTH_SHORT).show();
    			} else if(result.equals("password diff")) {
    				//报错：存在未填的属性
    				Toast.makeText(mContext, "密码与确认密码不相同", Toast.LENGTH_SHORT).show();
    			} else if(result.equals("too many courts")) {
    				//报错：存在未填的属性
    				Toast.makeText(mContext, "球场数不能超过20个", Toast.LENGTH_SHORT).show();
    			}
    			
    			else {
    				//报错
    				Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
    			}        
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				mDialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};
		final String url = ip+"/regist/administrator/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		mDialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod, 
				             "username",seller.getSellername(),
				             "sname",seller.getName(),
				             "phonenumber",seller.getPhone(),
				             "address",seller.getAddress(),
				             "description",seller.getIntroduction(),
				             "counter",String.valueOf(seller.getNumber()),
				             "password",seller.getPassword(),
				             "confirm_password",seller.getPassword());	

	}
	
	public void getUser(final Context mContext, final View mView)
	{/*
		//测试用例
		final EditText username_et = (EditText)mView.findViewById(R.id.username_et);
		final EditText name_et = (EditText)mView.findViewById(R.id.name_et);
		final EditText phone_et = (EditText)mView.findViewById(R.id.phone_et);
		String result = "{username:chenbojun,realname:chenbojun,phonenumber:123}";
		JSONObject mJson;
		try {
			mJson = new JSONObject(result);
			username_et.setText(mJson.getString("username"));
			name_et.setText(mJson.getString("realname"));
			phone_et.setText(mJson.getString("phonenumber"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在获取个人信息······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		final EditText username_et = (EditText)mView.findViewById(R.id.username_et);
		final EditText name_et = (EditText)mView.findViewById(R.id.name_et);
		final EditText phone_et = (EditText)mView.findViewById(R.id.phone_et);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
				try {
					dialog.cancel();
					JSONObject mJson = new JSONObject(result);
					username_et.setText(mJson.getString("username"));
					name_et.setText(mJson.getString("realname"));
					phone_et.setText(mJson.getString("phonenumber"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/modifyinfo/ordinary/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.GET;
		dialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod);	
		
	}
	
	public void edit_user(final Context mContext, final View mView, User user)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在修改个人信息······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		final EditText name_et = (EditText)mView.findViewById(R.id.name_et);
		final EditText phone_et = (EditText)mView.findViewById(R.id.phone_et);
		final Button edit_btn = (Button)mView.findViewById(R.id.edit_btn);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                if(result.equals("modify success!!")){
                	name_et.setFocusable(false);
	        		phone_et.setFocusable(false);
	        		//尚未解决，isModify来自PersonFragment
    				//isModify = false;
    				edit_btn.setText("编辑");
    				Toast.makeText(mContext, "个人信息修改成功", Toast.LENGTH_SHORT).show();
                } else if(result.equals("empty data exist")){
                	Toast.makeText(mContext, "存在未填的属性", Toast.LENGTH_SHORT).show();
                } else {
    				//报错
    				Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
    			}     
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/modifyinfo/ordinary/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		manager.NetConnection(successCallback, failCallback, url, httpMethod, 
				             "realname",user.getName(),
				             "phonenumber",user.getPhone());	

	}
	public void edit_user_password(final Context mContext, String old_password, String new_password, String confirm_pass)
	{
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                if(result.equals("modify success!!")){
    				Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_SHORT).show();
                } else if(result.equals("password diff")){
                	Toast.makeText(mContext, "新密码和确认密码不一致", Toast.LENGTH_SHORT).show();
                } else if(result.equals("old password incorrect")){
                	Toast.makeText(mContext, "旧密码错误", Toast.LENGTH_SHORT).show();
                } else if(result.equals("no newpass")){
                	Toast.makeText(mContext, "新密码未填", Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
                }
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/password_modify/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		manager.NetConnection(successCallback, failCallback, url, httpMethod, 
				             "oldpass",old_password,
				             "newpass",new_password,
				             "confirm_password",confirm_pass);	
	}
	
	public void getSellers(final Context mContext, final ListView sellerList)
	{
		//测试用例
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在获取商家列表······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                dialog.cancel();
                try {
					dialog.cancel();
					JSONArray mJsonArray = new JSONArray(result);
                    List<Seller> sellers = new ArrayList<Seller>();
                    for(int i = 0; i < mJsonArray.length(); i++)
                    {             	
                    	//sellers.add(new Seller(mJsonArray.getJSONObject(i).getString()))
                    	Seller seller = new Seller();
                    	JSONObject seller_JsonObject = mJsonArray.getJSONObject(i);
                    	seller.setId(seller_JsonObject.getString("id"));
                    	seller.setName(seller_JsonObject.getString("sname"));
                    	seller.setPhone(seller_JsonObject.getString("phonenumber"));
                    	seller.setAddress(seller_JsonObject.getString("address"));
                    	seller.setNumber(Integer.parseInt(seller_JsonObject.getString("counter")));
                    	seller.setIntroduction(seller_JsonObject.getString("description"));
                    	sellers.add(seller);
                    }
                    SellerAdapter sellerAdapter = new SellerAdapter(mContext, sellers);
        			sellerList.setAdapter(sellerAdapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/booked/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.GET;
		dialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod);	
	}

	public void getOrders(final Context mContext, final ListView orderList)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在获取订单列表······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                dialog.cancel();
                try {
					dialog.cancel();
                    JSONArray mJsonArray = new JSONArray(result);
                    List<Order> orders = new ArrayList<Order>();
                    for(int i = 0; i < mJsonArray.length(); i++)
                    {             	
                    	Order order = new Order();
                    	JSONObject order_JsonObject = mJsonArray.getJSONObject(i);                   	
                    	order.setSeller_name(order_JsonObject.getString("sname"));
                    	JSONObject timeObject = order_JsonObject.getJSONObject("time");
                    	
                    	order.setApplyTime(timeObject.getInt("hour"));
                        order.setApplyDate(timeObject.getInt("year") + 
                        		"-" + timeObject.getInt("month")+ 
                        		"-" + timeObject.getInt("day"));
                    	ArrayList< Integer> positionList = new ArrayList<Integer>(); 
                    	JSONArray positionList_JsonArray = order_JsonObject.getJSONArray("position");
                    	for(int j = 0; j < positionList_JsonArray.length(); j++)
                    	{
                    		positionList.add(positionList_JsonArray.getInt(j));
                    	}
                    	order.setPositionList(positionList);
                    	orders.add(order);
                    }
                    OrderAdapter orderAdapter = new OrderAdapter(mContext, orders);
        			orderList.setAdapter(orderAdapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/booking/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.GET;
		dialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod);	
	}	
	
	public void getSeller(final Context mContext, final String id)
	{
		final TextView name = (TextView)((Activity)mContext).findViewById(R.id.name);
		final TextView phone = (TextView)((Activity)mContext).findViewById(R.id.phone);
		final TextView address = (TextView)((Activity)mContext).findViewById(R.id.address);
		final TextView introduction = (TextView)((Activity)mContext).findViewById(R.id.introduction);	
		final NumberPicker timePicker = (NumberPicker)((Activity)mContext).findViewById(R.id.timepicker);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在获取商家信息······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                dialog.cancel();
                try {
					dialog.cancel();
					JSONObject mJson = new JSONObject(result);
					//显示商家信息
					name.setText(mJson.getString("sname"));
					phone.setText("联系方式："+mJson.getString("phonenumber"));
					address.setText("地址："+mJson.getString("address"));
					introduction.setText("简介："+mJson.getString("description"));	
					ChooseList.getInstance().current_hour = mJson.getInt("hour");	
					//~~~~~~~~
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/booked/detail/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		dialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod,"stadium_id", id);		
	}
	
	public void getBook_detail(final Context mContext, final String id, final Integer time)
	{
		//记录用于申请的时间（用于与后台返回的时间比较 早于后台规定就不能订）
		ChooseList.getInstance().ask_hour = time;
		final GridView gridView = (GridView)((Activity)mContext).findViewById(R.id.gridView);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在获取场地信息······");
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	
                dialog.cancel();
                try {
					dialog.cancel();
					JSONArray mJson = new JSONArray(result);
					List<Integer> posiList = new ArrayList<Integer>();
					for(int i = 0; i < mJson.length(); i++)
					{
						posiList.add((Integer)mJson.get(i));
					}
					gridView.setAdapter(new ItemAdapter(mContext, posiList));			
				} catch (JSONException e) {
			 		// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				dialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_SHORT).show();		
			}
		};	
		final String url = ip+"/booked/detail/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		dialog.show();
		manager.NetConnection(successCallback, failCallback, url, httpMethod,"stadium_id", id, "time", time.toString());		
	}
	public void sendOrder(final Context mContext, String id, int time, String position)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("当前状态：");
		builder.setMessage("正在提交订单······");
		final AlertDialog mDialog = builder.create();
		mDialog.setCancelable(false);
		NetWorkManager.SuccessCallback successCallback = new NetWorkManager.SuccessCallback(){
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub	 
				mDialog.cancel();
				Toast.makeText(mContext, NetWorkManager.session, Toast.LENGTH_LONG).show();
				if(result.equals("order accepted"))
        		{	
					Toast.makeText(mContext, "订单提交成功！！", Toast.LENGTH_SHORT).show();
	        		Intent intent = new Intent();
	        		//根据登录的用户类型来进入相应的个人中心，后期加
	        		intent.setClass(mContext, MainActivity.class); 		
	        		mContext.startActivity(intent);
	        		((Activity) mContext).finish();		      		
        		} else {
        			//输出错误信息
        			Toast.makeText(mContext, "订单提交失败！！", Toast.LENGTH_SHORT).show();
        		}
			}		
		};
		NetWorkManager.FailCallback failCallback = new NetWorkManager.FailCallback() {	
			@Override
			public void onFail() {
				mDialog.cancel();
				Toast.makeText(mContext, "通过网络获取信息时出现异常！！", Toast.LENGTH_LONG).show();		
			}
		};	
		final String url = ip+"/booking/";
		NetWorkManager manager = NetWorkManager.getInstance();
		NetWorkManager.HttpMethod httpMethod = NetWorkManager.HttpMethod.POST;
		mDialog.show();
		manager.NetConnection(successCallback,failCallback, url, httpMethod, "stadium_id", id, "time", ""+time, "position", position);
	}
	
}
	
