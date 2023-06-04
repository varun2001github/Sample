package com.varun.Model;

import java.util.HashMap;
import java.util.List;


//Parent class for model
public class DataObject{
    protected HashMap<String,Object> map=new HashMap<String,Object>();
    
    //constructor
    
    public DataObject(HashMap<String,Object> map){
    	this.map=map;
    }
    
    public DataObject(){
    }

	public HashMap<String, Object> getDataMap(){
		return map;
	}
	
//	public static void main(String[] args){
//		DataObject obj=new GroupInfoModel();
//	}
	

}
