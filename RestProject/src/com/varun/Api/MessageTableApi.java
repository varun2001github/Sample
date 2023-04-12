package com.varun.Api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

@Path("/MessageApi")
public class MessageTableApi{
	private MessagesModel messageObj=null;
	private OrmImp ormObj=new OrmImp();
	private static final Logger logger=LoggerUtil.getLogger(MessageTableApi.class);
	private CriteriaBuilder cb=new CriteriaBuilder();
	private static String Table=MessagesModel.class.getAnnotation(Table.class).name();

	public MessageTableApi(OrmImp obj){
		this.ormObj=obj;
	}
	
	public MessageTableApi(){
	}
    
	@GET
    @Path("/GetMessage/detail")
    @Produces(MediaType.APPLICATION_JSON)
	public String getMsg(@QueryParam("senderid") Integer senderid, @QueryParam("recieverid")Integer recieverid){
		 System.out.println("in");
         logger.log(Level.INFO,"method called");
         try{
        	 CriteriaBuilder cbNested=new CriteriaBuilder();
         	 JSONArray ar=new JSONArray();
    		 ormObj.SelectQuery("senderid","text","chattime").From(Table)
    		 .Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("recieverid",recieverid)))
    		 .Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("recieverid",senderid)));
    	    
    	   	List<DataObject> dataList=ormObj.getSelect();
       	    List<MessagesModel> l=null;
       	    if(dataList!=null){
       	    	System.out.println();
             	l=new ArrayList<MessagesModel>();
            	for(DataObject ob:dataList){
            		ar.put(ob.getDataMap());
            	}
       	    }
    	   	return ar.toString();
 	   	 }catch(Exception e){
 	   		 e.printStackTrace();
 		     logger.log(Level.WARNING,"unexpected",e);
 	   	 }
 		 return null;
		 
   }
	
	public List<MessagesModel> getNormalMsg(Integer senderid,Integer recieverid){
        logger.log(Level.INFO,"method called");
        try{
       	 CriteriaBuilder cbNested=new CriteriaBuilder();
   		 ormObj.SelectQuery("senderid","text","chattime").From(Table)
   		 .Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("recieverid",recieverid)))
   		 .Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("recieverid",senderid)));
   	
   	   	List<DataObject> dataList=ormObj.getSelect();
      	    List<MessagesModel> l=null;
      	    if(dataList.size()>0) {
            	l=new ArrayList<MessagesModel>();
           	for(DataObject ob:dataList){
           		l.add(new MessagesModel(ob));
           	}
      	    }
   	   	return l;
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
		 return null;
		 
  }
	public List<MessagesModel> getGroupMsg(Integer senderid,Integer recieverid){
         logger.log(Level.INFO,"method called");
         try{
        	CriteriaBuilder cbNested=new CriteriaBuilder();
    		ormObj.SelectQuery("senderid","text","chattime").From(Table)
    		.Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("group_id",recieverid)))
    		.Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("group_id",senderid)));
    		 
		    List<DataObject> dataList=ormObj.getSelect();
    	    List<MessagesModel> l=null;
    	    if(dataList.size()>0) {
	          	l=new ArrayList<MessagesModel>();
	         	for(DataObject ob:dataList){
	         		l.add(new MessagesModel(ob));
	         	}
    	    }
    	   	return l;
 	   	 }catch(Exception e){
 		     logger.log(Level.WARNING,"unexpected",e);
 	   	 }
 		 return null;
	}
	
	@POST
    @Path("/SendMessage/detail")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String sendMessage(String body){
//		public String sendMessage(@QueryParam("senderid") Integer senderid, @QueryParam("recieverid") Integer recieverid, @QueryParam("text") String text){
        logger.log(Level.INFO,"method called");
        messageObj=new MessagesModel();
        JSONObject object=new JSONObject(body);
        System.out.println("-"+body+"-"+object.getInt("senderid"));
//        try{
//     		messageObj.setSenderid(object.getInt("senderid"));
//     		messageObj.setRecieverid(object.getInt("recieverid"));
//     		messageObj.setText(object.getString("text"));
//     		messageObj.setChattime(System.currentTimeMillis());
//     		Integer isInserted=ormObj.Insert(messageObj.getDataObject());
//     		if(isInserted!=null){
//     			return object.put("status","Sent").toString();
//     		}
//	   	 }catch(Exception e){
//		     logger.log(Level.WARNING,"unexpected",e);
//	   	 }
 		 return object.put("status","Not Sent").toString();
	}
	
	public boolean addNormalMessage(Integer senderid, Integer recieverid,String text){
        logger.log(Level.INFO,"method called");
        messageObj=new MessagesModel();
        try{ 
     		messageObj.setSenderid(senderid);
     		messageObj.setRecieverid(recieverid);
     		messageObj.setText(text);
     		messageObj.setChattime(System.currentTimeMillis());
     		Integer isInserted=ormObj.Insert(messageObj.getDataObject());
     		if(isInserted!=null){
     			return true;
     		}
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
 		 return false;
	}
	public boolean addGroupMessage(Integer senderid,Integer groupid,String text){
        logger.log(Level.INFO,"method called");
        messageObj=new MessagesModel();
        try{
        	messageObj.setSenderid(senderid);
    		messageObj.setGroup_id(groupid);
    		messageObj.setText(text);
    		messageObj.setChattime(System.currentTimeMillis());
    		Integer isInserted=ormObj.Insert(messageObj.getDataObject());
    		if(isInserted!=null){
    			return true;
    		}
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
 		 return false;
	}
}
