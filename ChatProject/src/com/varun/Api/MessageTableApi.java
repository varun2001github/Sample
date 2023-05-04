package com.varun.Api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class MessageTableApi{
	private MessagesModel messageObj=null;
	private OrmImp ormObj;
	private static final Logger logger=Logger.getLogger(MessageTableApi.class.getName());
	private CriteriaBuilder cb=new CriteriaBuilder();
	private static String Table=MessagesModel.class.getAnnotation(Table.class).name();

	public MessageTableApi(OrmImp obj){
		this.ormObj=obj;
	}

	public List<MessagesModel> getNormalMsg(Integer senderid,Integer recieverid){
         logger.log(Level.INFO,"method called");
         try{
        	 CriteriaBuilder cbNested=new CriteriaBuilder();
    		 ormObj.SelectQuery("senderid","text","chattime").From(Table)
    		 .Where(cb.addNestedCondition(cbNested.addEquals("senderid",senderid).AndEquals("recieverid",recieverid)))
    		 .Or(cb.addNestedCondition(cbNested.addEquals("senderid",recieverid).AndEquals("recieverid",senderid))).OrderBy("chattime");
    	
    	   	List<DataObject> dataList=ormObj.getSelect();
       	    List<MessagesModel> l=null;
       	    if(dataList.size()>0){
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
	public boolean addNormalMessage(Integer senderid,Integer recieverid,String text) {
        logger.log(Level.INFO,"method called");
        messageObj=new MessagesModel();
        try{
     		messageObj.setSenderid(senderid);
     		messageObj.setRecieverid(recieverid);
     		messageObj.setText(text);
     		messageObj.setChattime(System.currentTimeMillis());
     		Integer isInserted=ormObj.InsertQuery(messageObj.getDataObject()).Insert();
     		if(isInserted!=null) {
     			return true;
     		}
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
 		 return false;
	}

}
