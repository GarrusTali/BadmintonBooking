package com.sysu.dataType;

import java.util.GregorianCalendar;

import android.R.integer;

public class Seller {	
	private String sellername, name, phone, address, introduction, password, id;
	private int number;
    public Seller()
    {
    }
    public Seller(String name, int number, String phone)
    {
    	this.setName(name);
    	this.setNumber(number);
    	this.setPhone(phone);
    }
	public Seller(String sellername,String name, String phone, String address, String introduction, String password,int number) 
	{
		this.setSellername(sellername);
		this.setName(name);
		this.setPhone(phone);
		this.setAddress(address);
		this.setIntroduction(introduction);
		this.setPassword(password);
		this.setNumber(number);
	}
	public String getSellername() {
		return sellername;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getIntroduction() {
		return introduction;
	}
	public String getPassword() {
		return password;
	}
	public int getNumber() {
		return number;
	}
	public String getId() {
		return id;
	}
	public void setSellername(String sellername) {
		this.sellername = sellername;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setId(String id) {
    	this.id = id;
	}
}
