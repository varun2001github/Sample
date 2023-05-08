package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.EmailModel;
import com.ProtoModel.UserModel.EmailModel.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;
import com.varun.Orm.Table;

public class EmailModelMap{
	
	private static final Logger logger=Logger.getLogger(EmailModelMap.class.getName());

	private HashMap<String,Object> map=new HashMap<String,Object>();
	
	private EmailModel emailModel=EmailModel.getDefaultInstance();
	
	public enum DbColumn{
		user_id,
		emailid,
		is_primary,
		is_verified,
		created_time,
		modif_time;
    }
  
	//constructor
	public EmailModelMap(DataObject ob){
		map=ob.getDataMap();
		setMapInVariables();
	}
	
	public EmailModelMap(){
		
	}
    
	private void setMapInVariables(){
//		emailModel=EmailModel.newBuilder()
//				.setUserId((Integer)map.get(DbColumn.user_id.name()))
//				.setEmailid((String)map.get(DbColumn.emailid.name()))
//				.setIsPrimary((Integer)map.get(DbColumn.is_primary.name()))
//				.setIsVerified((Integer)map.get(DbColumn.is_verified.name()))
//				.setCreatedTime((Long)map.get(DbColumn.created_time.name())).build();
		
		//null check and set value
	    Builder emailBuilder=EmailModel.newBuilder();
		FieldDescriptor field;
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()!=null){
				field=emailBuilder.getDescriptorForType().findFieldByName(entry.getKey());
				emailBuilder.setField(field,entry.getValue());
			}
		}
		emailModel=emailBuilder.build();
	}
	
	private void setVariablesInMap(){
		map.put("Table","email");
		map.put(DbColumn.user_id.name(),emailModel.getUserId());
		map.put(DbColumn.emailid.name(),emailModel.getEmailid());
		map.put(DbColumn.is_primary.name(),emailModel.getIsPrimary());
		map.put(DbColumn.is_verified.name(),emailModel.getIsVerified());
		map.put(DbColumn.created_time.name(),emailModel.getCreatedTime());
		map.put(DbColumn.modif_time.name(),emailModel.getModifTime());
	}
	
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
	}
	
	public EmailModel getEmail(){
		return emailModel;
	}
	
	public void setUser(EmailModel emailModel){
		this.emailModel = emailModel;
	}
}

