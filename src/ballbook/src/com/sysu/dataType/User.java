package com.sysu.dataType;

public class User {
    private String username, name, phone, password;
    private int id;
    public User(String username,String name, String phone)
    {
    	this.username = username;
    	this.name = name;
    	this.phone = phone;
    }
    public User(String username,String name, String phone, String password)
    {
    	this.username = username;
    	this.name = name;
    	this.phone = phone;
    	this.password = password;
    }
    public void setUsername(String username)
    {
    	this.username = username;
    }
    public void setName(String name)
    {
    	this.name = name;
    }
    public void setPhone(String phone)
    {
    	this.phone = phone;
    }
    public void setPassword(String password)
    {
    	this.password = password;
    }
    public void setId(int id)
    {
    	this.id = id;
    }
    public String getUsername()
    {
    	return username;
    }
    public String getName()
    {
    	return name;
    }
    public String getPhone()
    {
    	return phone;
    }
    public String getPassword()
    {
    	return password;
    }
    public int getId()
    {
    	return id;
    }
}
