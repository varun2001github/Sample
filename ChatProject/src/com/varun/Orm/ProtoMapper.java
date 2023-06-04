//$Id$
package com.varun.Orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ProtoModel.UserModel.Email;
import com.ProtoModel.UserModel.Session;
import com.ProtoModel.UserModel.User;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Field;
import com.google.protobuf.Message;
import com.varun.Model.DataObject;

public class ProtoMapper{
	
    public static HashMap<String, Object> tableObjMap=getTableMap();
    
    public static HashMap<String, Object> getTableMap() {
    	HashMap<String, Object> tableObjMap=new HashMap<String, Object>();
    	tableObjMap.put("User","userinfo");
    	tableObjMap.put("Session","session_info");
    	tableObjMap.put("Password","user_pass");
    	tableObjMap.put("Email","email");
    	tableObjMap.put("Mobile","mobile");
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
		for(Map.Entry<Descriptors.FieldDescriptor,Object> entry : builder.getAllFields().entrySet()){
			if(!entry.getKey().getType().equals(Type.MESSAGE)){
			    dataMap.put(entry.getKey().getName(),entry.getValue());
			}
		}
		return new DataObject(dataMap);
	}
	
    public static void main(String args[]){
    	Object obj=new Object();
    	
//    	HashMap<String, Object> h=new HashMap<String, Object>();
//    	h.put("user_id",1);
//    	h.put("session_id","");
//    	Message message=setProtoMap(SessionModel.getDescriptor(),new DataObject(h));
    	List<Email> l=new ArrayList<Email>();
    	l.add(Email.newBuilder().setEmailid("email123").build());
    	User sessionObject=User.newBuilder().setUserId(1).setCountry("india").addAllEmailObject(l).build();
    	DataObject dataobj=getDataObject(sessionObject);
		for(Map.Entry<String,Object> entry : dataobj.getDataMap().entrySet()){
			System.out.println("Recieved map="+entry.getKey()+"-"+entry.getValue());
		}
//      System.out.println("set map in proto :"+sessionObject.getUserId()+sessionObject.getSessionId());
    }
}






