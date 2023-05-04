//$Id$
package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class groupMessageTableApi{
	private MessagesModel messageObj=null;
	private OrmImp ormObj=new OrmImp();
	private static final Logger logger=Logger.getLogger(groupMessageTableApi.class.getName());
	private CriteriaBuilder cb=new CriteriaBuilder();
	private static String Table=GroupMessagesModel.class.getAnnotation(Table.class).name();
	public groupMessageTableApi(){
	}
	public groupMessageTableApi(OrmImp obj){
		this.ormObj=obj;
	}
	public List<GroupMessagesModel> getGroupMsg(Integer groupid){
        logger.log(Level.INFO,"method called");
        try{
       	CriteriaBuilder cbNested=new CriteriaBuilder();
   		ormObj.SelectQuery("senderid","text","chattime").From(Table)
   		.Where(cb.addEquals("groupid",groupid));
   		
   		 
		List<DataObject> dataList=ormObj.getSelect();
   	    List<GroupMessagesModel> l=null;
   	    if(dataList.size()>0){
   	    	System.out.println("got");
          	l=new ArrayList<GroupMessagesModel>();
         	for(DataObject ob:dataList){
         		l.add(new GroupMessagesModel(ob));
         	}
   	    }
   	   	return l;
	   	}catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	}
		return null;
   }
	public boolean addGroupMessage(Integer senderid,Integer groupid,String text){
        logger.log(Level.INFO,"method called");
        GroupMessagesModel messageObj=new GroupMessagesModel();
        try{
        	messageObj.setSenderid(senderid);
    		messageObj.setGroupid(groupid);
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
   public static void main(String args[]){
	   groupMessageTableApi go=new groupMessageTableApi();
//	   List<GroupMessagesModel> ob=go.getGroupMsg(1,1);
//	   if(ob!=null) {
//		   for(GroupMessagesModel g:ob){
//			   System.out.println(g.getSenderid()+g.getText());
//		   } 
//	   }
	   go.addGroupMessage(1,1,"hel");
   }
}
