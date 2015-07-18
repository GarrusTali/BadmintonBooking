package com.sysu.dataType;

import java.util.GregorianCalendar;
import java.util.List;

import android.R.integer;

public class Order {
	private String order_id, user_id, seller_id;
	private Seller seller;
	private String applyTime;
	private List<Position> positionList;
	public Order()
	{
		 
	}
    public Order(Seller seller, String applyTime, List<Position> positionList)
    {
    	//this.order_id = order_id;
    	//this.user_id = user_id;
    	//this.seller_id = seller_id;
    	this.seller = seller;
    	this.applyTime = applyTime;
    	this.positionList = positionList;
    }
    public String getOrder_id()
    {
    	return this.order_id;
    }
    public String getUser_id()
    {
    	return this.user_id;
    }
    public String getSeller_id()
    {
    	return this.seller_id;
    }
    public Seller getSeller()
    {
    	return this.seller;
    }
    public String getApplyTime()
    {
    	return this.applyTime;
    }
    public List<Position> getPositionList()
    {
    	return this.positionList;
    }
    public void setOrder_id(String order_id)
    {
    	this.order_id = order_id;
    }
    public void setUser_id(String user_id)
    {
    	this.user_id = user_id;
    }
    public void setSeller_id(String seller_id)
    {
    	this.seller_id = seller_id;
    }
    public void setSeller(Seller seller)
    {
    	this.seller = seller;
    }
    public void setApplyTime(String applyTime)
    {
    	this.applyTime = applyTime;
    }
    public void setPositionList(List<Position> positionList)
    {
    	this.positionList = positionList;
    }
}
