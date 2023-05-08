package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.PasswordModel;
import com.ProtoModel.UserModel.PasswordModel.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;
import com.varun.Orm.CommonMethod;
import com.varun.Orm.Table;

public class PasswordModelMap implements CommonMethod{
	
    private static final Logger logger=Logger.getLogger(PasswordModelMap.class.getName());
	
	private HashMap<String,Object> map=new HashMap<String,Object>();
	
	private PasswordModel passwordModel=PasswordModel.getDefaultInstance();
    
	public static enum DbColumn{
		user_id,
		pass_salt,
		pass_hash,
		pass_status,
		created_time;
	}    
	
	//constructor
	public PasswordModelMap(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}

	public PasswordModelMap(){
	}
	
	private void setMapInVariables(){
//		passwordModel=PasswordModel.newBuilder().setUserId((Integer)map.get(DbColumn.user_id.name()))
//				.setPassSalt((String)map.get(DbColumn.pass_salt.name()))
//				.setPassHash((String)map.get(DbColumn.pass_hash.name()))
//				.setPassStatus((Integer)map.get(DbColumn.pass_status.name()))
//				.setCreatedTime((Long)map.get(DbColumn.created_time.name())).build();
		
		//null check and set value
	    Builder passwordBuilder=PasswordModel.newBuilder();
		FieldDescriptor field;
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()!=null){
				field=passwordBuilder.getDescriptorForType().findFieldByName(entry.getKey());
				passwordBuilder.setField(field,entry.getValue());
			}
		}
		passwordModel=passwordBuilder.build();
	}
	
	private void setVariablesInMap(){
		map.put("Table","user_pass");
		map.put(DbColumn.user_id.name(),passwordModel.getUserId());
		map.put(DbColumn.pass_salt.name(),passwordModel.getPassSalt());
		map.put(DbColumn.pass_hash.name(),passwordModel.getPassHash());
		map.put(DbColumn.pass_status.name(),passwordModel.getPassStatus());
		map.put(DbColumn.created_time.name(),passwordModel.getCreatedTime());
	}

	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
	}
	//
	public PasswordModel getPasswordModel(){
		return passwordModel;
	}

	public void setPasswordModel(PasswordModel passwordModel){
		this.passwordModel = passwordModel;
	}
	
}
