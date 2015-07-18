package com.sysu.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sysu.ballbook.R.string;

import android.os.AsyncTask;

public class NetWorkManager {
	
	private static NetWorkManager mInstance = null;	
	public static String sCookie;
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
					// POST������������ķ�ʽд�������
					case POST:
						uc = new URL(url).openConnection();
						//����cookie
						if(sCookie != null && sCookie.length() > 0)
							uc.setRequestProperty("Cookie", sCookie);	
						
						// �������
						uc.setDoOutput(true);
						// ���ñ�����CHARSET
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										"utf-8"));
						bw.write(paramsStr.toString());
						bw.flush();
						//���cookie
						String cookie = uc.getHeaderField("Cookie");
						if(cookie != null && cookie.length() > 0)
							sCookie = cookie;
						break;
					// GET������url������ʽ�����ϴ�
					default:
						if(kvs.length != 0){
							uc = new URL(url + "?" + paramsStr.toString())
							.openConnection();
						} else {
							uc = new URL(url)
							.openConnection();
						}
						//����cookie
						if(sCookie != null && sCookie.length() > 0)
							uc.setRequestProperty("Cookie", sCookie);
						break;
					}

					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(), "utf-8"));
					String line = null;
					StringBuffer result = new StringBuffer();
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					return result.toString();

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
