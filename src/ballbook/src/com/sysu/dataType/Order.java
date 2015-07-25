package com.sysu.dataType;

import java.util.GregorianCalendar;
import java.util.List;

import android.R.integer;

public class Order {
	private String seller_id;
	private String sname;
	private String address;
	private Integer applyTime;
	private String applyDate;
	private List<Integer> positionList;
	public Order()
	{
		 
	}
    public Order(String seller_id, Integer applyTime, List<Integer> positionList)
    {
    	this.seller_id = seller_id;
    	this.applyTime = applyTime;
    	this.positionList = positionList;
    }

    public String getSeller_id()
    {
    	return this.seller_id;
    }
    public String getSeller_name()
    {
    	return this.sname;
    }
    public String getAddress()
    {
    	return this.address;
    }
    public Integer getApplyTime()
    {
    	return this.applyTime;
    }
    public String getApplyDate()
    {
    	return this.applyDate;
    }
    public List<Integer> getPositionList()
    {
    	return this.positionList;
    }

    public void setSeller_id(String seller_id)
    {
    	this.seller_id = seller_id;
    }
    public void setSeller_name(String sname)
    {
    	this.sname = sname;
    }
    public void setAddress(String address)
    {
    	this.address = address;
    }
    public void setApplyDate(String applyDate)
    {
    	this.applyDate = applyDate;
    }
    public void setApplyTime(Integer applyTime)
    {
    	this.applyTime = applyTime;
    }
    public void setPositionList(List<Integer> positionList)
    {
    	this.positionList = positionList;
    }
}
