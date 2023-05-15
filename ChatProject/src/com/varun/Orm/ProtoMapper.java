//$Id$
package com.varun.Orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ProtoModel.UserModel.EmailModel;
import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.UserinfoModel;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Message;
import com.varun.Model.DataObject;

public class ProtoMapper{
	
    public static HashMap<String, Object> tableObjMap=getTableMap();
    
    public static HashMap<String, Object> getTableMap() {
    	HashMap<String, Object> tableObjMap=new HashMap<String, Object>();
    	tableObjMap.put("UserinfoModel","userinfo");
    	tableObjMap.put("SessionModel","session_info");
    	tableObjMap.put("PasswordModel","user_pass");
    	tableObjMap.put("EmailModel","email");
    	tableObjMap.put("MobileModel","mobile");
		return tableObjMap;
    }
    
	public static Object getProtoObject(Message.Builder builder,DataObject ob){
		HashMap<String, Object> h=ob.getDataMap();
		FieldDescriptor field;
		for(Map.Entry<String, Object> entry : h.entrySet()){
			if(entry.getValue()!=null){
				field=builder.getDescriptorForType().findFieldByName(entry.getKey());
				try{
					builder.setField(field,entry.getValue());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return builder.build();
	}
	
	public static DataObject getDataObject(Message message){
		Message.Builder builder=message.toBuilder();
		HashMap<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("Table",tableObjMap.get(builder.getDescriptorForType().getName()));
		for(Map.Entry<Descriptors.FieldDescriptor,Object> entry : builder.getAllFields().entrySet()) {
			if(!entry.getKey().getType().equals(Type.MESSAGE)){
			    dataMap.put(entry.getKey().getName(),entry.getValue());
			}
		}
		return new DataObject(dataMap);
	}
	
    public static void main(String args[]){
//    	HashMap<String, Object> h=new HashMap<String, Object>();
//    	h.put("user_id",1);
//    	h.put("session_id","");
//    	Message message=setProtoMap(SessionModel.getDescriptor(),new DataObject(h));
    	List<EmailModel> l=new ArrayList<EmailModel>();
    	l.add(EmailModel.newBuilder().setEmailid("email123").build());
    	UserinfoModel sessionObject=UserinfoModel.newBuilder().setUserId(1).setCountry("india").addAllEmailObj(l).build();
    	DataObject dataobj=getDataObject(sessionObject);
		for(Map.Entry<String,Object> entry : dataobj.getDataMap().entrySet()){
			System.out.println("Recieved map="+entry.getKey()+"-"+entry.getValue());
		}
//      System.out.println("set map in proto :"+sessionObject.getUserId()+sessionObject.getSessionId());
    }
}






