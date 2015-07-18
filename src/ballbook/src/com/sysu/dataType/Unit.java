package com.sysu.dataType;

public class Unit {
	private int id;
	private int time;
	private int empty;
	public Unit(int id, int time, int empty) {
		this.id = id;
		this.time = time;
		this.empty = empty;
	}
	public int getId()
	{
		return this.id;
	}
	public int getTime()
	{
		return this.time;
	}
	public int getEmpty()
	{
		return this.empty;
	}	
	public boolean isEmpty()
	{
		if(empty == 0)
			return true;
		else
			return false;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setTime(int Time)
	{
		this.time = time;
	}
	public void setEmpty(int empty)
	{
		this.empty = empty;
	}
}
