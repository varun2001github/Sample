package com.varun.Model;

import java.util.HashMap;

//Parent class for model
public class DataObject{
    protected HashMap<String,Object> map=new HashMap<String,Object>();
    
    //constructor
    public DataObject(HashMap<String,Object> map){
    	this.map=map;
    }
    
//	public DataObject(){}
//	
//	//methods
//    protected void updateMap(HashMap<String,Object> map){
//    	this.map=map;
//    }
    
    protected DataObject getDataObjectRef(){
		return this;
	}

	public HashMap<String, Object> getDataMap(){
		return map;
	}
	
}
