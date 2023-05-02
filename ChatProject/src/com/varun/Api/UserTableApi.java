package com.varun.Api;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.*;

@Path("/UserApi")
public class UserTableApi{
	//logger 
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	private static UserinfoTableModel userObject=null;
	private CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
	private static String Table=UserinfoTableModel.class.getAnnotation(Table.class).name();
	
    public UserTableApi(OrmImp ob){
		try {
			this.orm=ob;
   	    	logger.log(Level.INFO," constructor ");
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    @GET
    @Path("/getUser")
    @Produces(MediaType.TEXT_XML)
    public String getUser(){
    	return "varun";
    }
    
    public UserinfoTableModel getUserById(Integer id){
	    logger.log(Level.INFO,"id passed "+id);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",id));
    	
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
    	   return new UserinfoTableModel(dataList.get(0));
    	}
    	return null;
    }
    
    public boolean updateUserinfo(UserinfoTableModel oldObj,UserinfoTableModel newObj) throws JsonProcessingException{
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(oldObj.getDataObject(),newObj.getDataObject()).Where(c.addEquals("user_id",oldObj.getUser_id()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    public Integer addUser(String username){
    	userObject=new UserinfoTableModel();
    	userObject.setUser_name(username);
    	Integer id=orm.InsertQuery(userObject.getDataObject()).Insert();
	    logger.log(Level.INFO,"object inserted");
    	return id;
    }
    
    public List<UserinfoTableModel> fetchChatList(Integer uid){
    	orm.SelectQuery("user_id","user_name").From(Table).Where(c.addNotEquals("user_id",uid));
    	System.out.println(orm.getQuery());
    	List<DataObject> dataList=orm.getSelect();
    	List<UserinfoTableModel> l=null;
    	if(dataList.size()>0){
        	l=new ArrayList<UserinfoTableModel>();
        	for(DataObject ob:dataList){
        		l.add(new UserinfoTableModel(ob));
        	}
    	}
    	return l;
    }
}
