package com.sysu.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sysu.ballbook.R.string;

import android.os.AsyncTask;

public class NetWorkManager {
	
	private static NetWorkManager mInstance = null;	
	public static String session;
	public static String saeut;
	private NetWorkManager() {

	}

	public static NetWorkManager getInstance() {
		if (mInstance == null)
			mInstance = new NetWorkManager();
		return mInstance;
	}

	public void NetConnection(final SuccessCallback successCallback, final FailCallback failCallback, 
			final String url, final HttpMethod httpMethod,final String... kvs) {
		final StringBuffer paramsStr = new StringBuffer();
		for (int i = 0; i < kvs.length; i += 2) {
			paramsStr.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
		}

		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				try {
					URLConnection uc;
					switch (httpMethod) {
					// POST方法以输出流的方式写入服务器
					case POST:
						uc = new URL(url).openConnection();
						//设置cookie
						if(session != null && session.length() > 0)
							uc.setRequestProperty("Cookie", session);	
						
						// 允许输出
						uc.setDoOutput(true);
						// 设置编码风格CHARSET
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										"utf-8"));
						bw.write(paramsStr.toString());
						bw.flush(); 
						//获得cookie
						if(session == null)
						{
							Map<String,List<String>> mymap = uc.getHeaderFields();
							String[] cookie = mymap.get("Set-Cookie").toArray(new String[0]);
							session = cookie[0].split(";")[0];
						}
						
						break;
					// GET方法以url参数方式进行上传
					default:
						if(kvs.length != 0){
							uc = new URL(url + "?" + paramsStr.toString())
							.openConnection();
						} else {
							uc = new URL(url)
							.openConnection();
						}
						//设置cookie
						if(session != null && session.length() > 0)
							uc.setRequestProperty("Cookie", session);
						break;
					}

					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(), "utf-8"));
					String line = null;
					StringBuffer result = new StringBuffer();
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					return result.toString().split("<")[0];

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if(failCallback != null)
					{
						failCallback.onFail();
					}
				}
			}
		}.execute();
	}
	
	public static interface SuccessCallback {
		public void onSuccess(String result);
	}

	public static interface FailCallback {
		public void onFail();
	}
	public enum HttpMethod {
		GET, POST;
	}

}
