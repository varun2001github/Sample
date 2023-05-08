package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.SessionModel.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;

public class SessionModelMap{
	
    private static final Logger logger=Logger.getLogger(SessionModelMap.class.getName());
	
	private HashMap<String,Object> map=new HashMap<String,Object>();

	private SessionModel sessionModel=SessionModel.getDefaultInstance();
    
    enum DbColumn{
    	user_id,
    	session_id,
    	Expiry;
	}

	//constructor
	public SessionModelMap(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	
	public SessionModelMap(){
	}
    	
	//private methods
	private void setMapInVariables(){
//		sessionModel=SessionModel.newBuilder()
//				     .setUserId((Integer)map.get(DbColumn.user_id.name()))
//				     .setSessionId((String)map.get(DbColumn.session_id.name()))
//				     .setExpiry((Long)map.get(DbColumn.Expiry.name())).build();
		//null check and set value
	    Builder sessionBuilder=SessionModel.newBuilder();
		FieldDescriptor field;
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()!=null){
				field=sessionBuilder.getDescriptorForType().findFieldByName(entry.getKey());
				sessionBuilder.setField(field,entry.getValue());
			}
		}
		sessionModel=sessionBuilder.build();
	}
	
	private void setVariablesInMap(){
		map.put("Table","session_info");
		map.put(DbColumn.user_id.name(),sessionModel.getUserId());
		map.put(DbColumn.session_id.name(),sessionModel.getSessionId());
		map.put(DbColumn.Expiry.name(),sessionModel.getExpiry());
	}

	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
	}

	public SessionModel getEmail(){
		return sessionModel;
	}
	
	public void setUser(SessionModel emailModel){
		this.sessionModel = emailModel;
	}
}
