package com.varun.Model;

import java.util.HashMap;
import java.util.List;

import com.ProtoModel.UserModel.UserinfoModel;
import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.PasswordModel;
import com.ProtoModel.UserModel.EmailModel;
import com.ProtoModel.UserModel.MobileModel;

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
