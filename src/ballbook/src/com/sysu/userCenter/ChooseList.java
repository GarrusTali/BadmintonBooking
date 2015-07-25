package com.sysu.userCenter;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;


public class ChooseList {
    static ChooseList data = null;
    public int current_hour;
    public int ask_hour;
    public ChooseList()
    {
    	chooseList = new ArrayList<Integer>();
    	current_hour = -1;
    	ask_hour = -1;
    }
    public static ChooseList getInstance() {
		if(data == null)
		{
			data = new ChooseList();
		}
		return data;
	}
	public List<Integer> chooseList ;
}
