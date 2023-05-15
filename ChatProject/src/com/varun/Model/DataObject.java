package com.varun.Model;

import java.util.HashMap;

//Parent class for model
public class DataObject{
    private HashMap<String,Object> map=new HashMap<String,Object>();
    
    //constructor
    public DataObject(HashMap<String,Object> map){
    	this.map=map;
    }

	public HashMap<String, Object> getDataMap(){
		return map;
	}
	
}
