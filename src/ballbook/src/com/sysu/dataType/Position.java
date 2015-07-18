package com.sysu.dataType;

import java.util.List;

import android.R.integer;


public class Position {
	private String seller_id;
	private int position;
	private List<Time> timeList;
	Position(String seller_id, int position, List<Time> timeList){
		this.seller_id = seller_id;
		this.position = position;
		this.timeList = timeList;
	}
    public Position() {
		// TODO Auto-generated constructor stub
	}

	public void setSellerId(String seller_id){
    	this.seller_id = seller_id;
    }
    public void setPositon(int position){
    	this.position = position;
    }
    public void setTimeList(List<Time> timeList){
    	this.timeList = timeList;
    }
    public String getSellerId(){
    	return seller_id;
    }
    public int getPosition(){
    	return position;
    }
    public List<Time> getTimeList(){
    	return timeList;
    }
}
