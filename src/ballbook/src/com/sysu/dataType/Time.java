package com.sysu.dataType;

import android.R.integer;

public class Time{
	private int time;
	private int empty;
	Time(int time, int empty){
		this.time = time;
		this.empty = empty;
	}
	public Time() {
		// TODO Auto-generated constructor stub
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	public void setEmpty(int empty)
	{
		this.empty = empty;
	}
	public int getTime()
	{
		return this.time;
	}
	public int getEmpty()
	{
		return this.empty;
	}
}
