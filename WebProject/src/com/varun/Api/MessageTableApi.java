package com.varun.Api;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class MessageTableApi {
	private static MessagesModel messageObj=new MessagesModel();
	private static OrmImp ormObj;
	private static final Logger logger=LoggerUtil.getLogger(MessageTableApi.class);
	private CriteriaBuilder cb=new CriteriaBuilder();
	
	public MessageTableApi(){
		ormObj=new OrmImp(messageObj);
	}
//     public List<messages> get
	public void closeOrmConnection(){
  		 ormObj.close();
	}
	public List<MessagesModel> getNormalMsg(Integer senderid,Integer recieverid){
         logger.log(Level.INFO,"method called");
         try{
        	 CriteriaBuilder cbNested=new CriteriaBuilder();
    		 ormObj.SelectQuery("senderid","text","chattime")
    		 .Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("recieverid",recieverid)))
    		 .Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("recieverid",senderid)));
    	   	 @SuppressWarnings("unchecked")
			 List<MessagesModel> l=(List<MessagesModel>)(Object)ormObj.getSelect();
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
    		 ormObj.SelectQuery("senderid","text","chattime")
    		 .Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("group_id",recieverid)))
    		 .Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("group_id",senderid)));
    	   	 @SuppressWarnings("unchecked")
			 List<MessagesModel> l=(List<MessagesModel>)(Object)ormObj.getSelect();
    	   	 return l;
 	   	 }catch(Exception e){
 		     logger.log(Level.WARNING,"unexpected",e);
 	   	 }
 		 return null;
		
    }
	public boolean addNormalMessage(Integer senderid,Integer recieverid,String text) {
        logger.log(Level.INFO,"method called");
        try{
     		messageObj.setSenderid(senderid);
     		messageObj.setRecieverid(recieverid);
     		messageObj.setText(text);
     		messageObj.setChattime(BigInteger.valueOf(System.currentTimeMillis()));
     		Integer isInserted=ormObj.Insert(messageObj);
     		if(isInserted!=null) {
     			return true;
     		}
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
 		 return false;
	}
	public boolean addGroupMessage(Integer senderid,Integer groupid,String text) {
        logger.log(Level.INFO,"method called");
        try{
        	messageObj.setSenderid(senderid);
    		messageObj.setGroup_id(groupid);
    		messageObj.setText(text);
    		messageObj.setChattime(BigInteger.valueOf(System.currentTimeMillis()));
    		Integer isInserted=ormObj.Insert(messageObj);
    		if(isInserted!=null) {
    			return true;
    		}
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
 		 return false;
	}
}
