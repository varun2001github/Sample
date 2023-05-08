package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.MobileModel;
import com.ProtoModel.UserModel.MobileModel.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;

public class MobileModelMap{
	
    private static final Logger logger=Logger.getLogger(MobileModelMap.class.getName());

	private HashMap<String,Object> map=new HashMap<String,Object>();
	
	private MobileModel mobileModel=MobileModel.getDefaultInstance();
	
	public static enum DbColumn{
		user_id,
		mobileno,
		is_primary,
		is_verified,
		created_time,
		modif_time;
    }
	
	//constructor
	public MobileModelMap(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	
	public MobileModelMap(){
	}
	
	private void setMapInVariables(){
//		mobileModel=MobileModel.newBuilder()
//				.setUserId((Integer)map.get(DbColumn.user_id.name()))
//				.setMobileno((Integer)map.get(DbColumn.mobileno.name()))
//				.setIsPrimary((Integer)map.get(DbColumn.is_primary.name()))
//				.setIsVerified((Integer)map.get(DbColumn.is_verified.name()))
//				.setCreatedTime((Long)map.get(DbColumn.created_time.name()))
//				.setModifTime((Long)map.get(DbColumn.modif_time.name())).build();
		
		//null check and set value
	    Builder mobileBuilder=MobileModel.newBuilder();
		FieldDescriptor field;
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()!=null){
				field=mobileBuilder.getDescriptorForType().findFieldByName(entry.getKey());
				mobileBuilder.setField(field,entry.getValue());
			}
		}
		mobileModel=mobileBuilder.build();
	}
	
	private void setVariablesInMap(){
		map.put("Table","mobile");
		map.put(DbColumn.user_id.name(),mobileModel.getUserId());
		map.put(DbColumn.mobileno.name(),mobileModel.getMobileno());
		map.put(DbColumn.is_primary.name(),mobileModel.getIsPrimary());
		map.put(DbColumn.is_verified.name(),mobileModel.getIsVerified());
		map.put(DbColumn.created_time.name(),mobileModel.getCreatedTime());
		map.put(DbColumn.modif_time.name(),mobileModel.getModifTime());
	}
    
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
	}
	
}
