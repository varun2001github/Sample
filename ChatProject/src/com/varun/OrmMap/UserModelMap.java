package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.*;
import com.ProtoModel.UserModel.UserinfoModel;
import com.ProtoModel.UserModel.UserinfoModel.Builder;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;
import com.varun.Model.UserModel;

public class UserModelMap {
	 private static final Logger logger=Logger.getLogger(UserModel.class.getName());
		
	 private HashMap<String,Object> map=new HashMap<String,Object>();

	 private UserinfoModel userModel=UserinfoModel.getDefaultInstance();

   	 public enum DbColumn{
	    	user_id,
	    	user_name,
	    	gender,
	    	country,
	    	picfile,
	    	created_time;
	 }

	public UserModelMap(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	
	public UserModelMap(UserinfoModel userModel){
		this.userModel=userModel;
		setVariablesInMap();
	}
	
	public DataObject getDataObject(){
		return new DataObject(this.map);
	}
	
	private void setMapInVariables(){
		//direct set value throws exception
		
//		userModel=UserinfoModel.newBuilder()
//				.setUserId((Integer)map.get(DbColumn.user_id.name()))
//		        .setUserName((String)map.get(DbColumn.user_name.name()))
//		        .setGender((String)map.get(DbColumn.gender.name()))
//		        .setCountry((String)map.get(DbColumn.country.name()))
//		        .setPicfile((String)map.get(DbColumn.picfile.name()))
//		        .setCreatedTime((Long)map.get(DbColumn.created_time.name()))
//		        .setModifTime((Long)map.get(DbColumn.modif_time.name())).build();
		
		
		    //null check and set value
		    Builder userBuilder=UserinfoModel.newBuilder();
			FieldDescriptor field;
			for (Map.Entry<String, Object> entry : map.entrySet()){
				if(entry.getValue()!=null){
					field=userBuilder.getDescriptorForType().findFieldByName(entry.getKey());
					userBuilder.setField(field,entry.getValue());
				}
			}
			userModel=userBuilder.build();
			
//		    userBuilder.getDescriptorForType().findFieldByName(name)
//			if(map.get(DbColumn.user_id.name())!=null) userBuilder.setUserId((Integer)map.get(DbColumn.user_id.name()));
//			if(map.get(DbColumn.user_name.name())!=null) userBuilder.setUserName((String)map.get(DbColumn.user_name.name()));
//		    userModel=	userBuilder.build();
	}
	
	private void setVariablesInMap(){
		map.put("Table","userinfo");
		map.put(DbColumn.user_id.name(),userModel.getUserId());
		map.put(DbColumn.user_name.name(),userModel.getUserName());
		map.put(DbColumn.gender.name(), userModel.getGender());
		map.put(DbColumn.country.name(),userModel.getCountry());
		map.put(DbColumn.picfile.name(),userModel.getPicfile());
		map.put(DbColumn.created_time.name(),userModel.getCreatedTime());
	}
	
	public UserinfoModel getUser(){
		return userModel;
	}

	public void setUser(UserinfoModel userModel){
		this.userModel = userModel;
	}
}
