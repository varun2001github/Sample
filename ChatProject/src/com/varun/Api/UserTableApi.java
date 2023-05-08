package com.varun.Api;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.ProtoModel.UserModel.UserinfoModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailModel;
import com.varun.Model.UserModel;
import com.varun.Orm.*;
import com.varun.OrmMap.UserModelMap;

@Path("/UserApi")
public class UserTableApi{
	//logger 
	private static final Logger logger=Logger.getLogger(UserTableApi.class.getName());
	private static UserModel userObject=null;
	private CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
	private static String Table=UserModel.class.getAnnotation(Table.class).name();
	
    public UserTableApi(OrmImp ob){
		try {
			this.orm=ob;
   	    	logger.log(Level.INFO," constructor ");
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public UserModel getUserById(Integer id){
	    logger.log(Level.INFO,"id passed "+id);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",id));
    	
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
    	   return new UserModel(dataList.get(0));
    	}
    	return null;
    }
    
    // X
    public boolean updateUserinfo(UserModel oldObj,UserModel newObj) throws JsonProcessingException{
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(oldObj.getDataObject(),newObj.getDataObject()).Where(c.addEquals("user_id",oldObj.getUser_id()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    public Integer addUser(String username){
    	userObject=new UserModel();
    	userObject.setUser_name(username);
    	Integer id=orm.InsertQuery(userObject.getDataObject()).Insert();
	    logger.log(Level.INFO,"object inserted");
    	return id;
    }
    
    public List<UserModel> fetchChatList(Integer uid){
    	orm.SelectQuery("user_id","user_name").From(Table).Where(c.addNotEquals("user_id",uid));
    	System.out.println(orm.getQuery());
    	List<DataObject> dataList=orm.getSelect();
    	List<UserModel> l=null;
    	if(dataList.size()>0){
        	l=new ArrayList<UserModel>();
        	for(DataObject ob:dataList){
        		l.add(new UserModel(ob));
        	}
    	}
    	return l;
    }
    
    //proto 
    public UserinfoModel getUser(Integer id){
	    logger.log(Level.INFO,"id passed "+id);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",id));
    	
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
		for (Map.Entry<String, Object> entry : dataList.get(0).getDataMap().entrySet()){
		    System.out.println(entry.getKey()+" = "+entry.getValue());
		}
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
    	   return new UserModelMap(dataList.get(0)).getUser();
    	}
    	return null;
    }
    
    public List<UserinfoModel> fetchChatListByUid(Integer uid){
    	orm.SelectQuery("user_id","user_name").From(Table).Where(c.addNotEquals("user_id",uid));
    	System.out.println(orm.getQuery());
    	List<DataObject> dataList=orm.getSelect();
    	List<UserinfoModel> l=null;
    	if(dataList.size()>0){
        	l=new ArrayList<UserinfoModel>();
        	for(DataObject ob:dataList){
        		l.add(new UserModelMap(ob).getUser());
        	}
    	}
    	return l;
    }
    
    public boolean updateUserinfo(UserinfoModel oldObj,UserinfoModel newObj){
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(new UserModelMap(oldObj).getDataObject(),new UserModelMap(newObj).getDataObject())
    	.Where(c.addEquals("user_id",oldObj.getUserId()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    public Integer addUser(UserinfoModel userObject){
    	orm.InsertQuery(new UserModelMap(userObject).getDataObject());
    	System.out.println(orm.getQuery());
    	Integer id=orm.Insert();
	    logger.log(Level.INFO,"object inserted");
    	return id;
    }
    
    public static void main(String[] args){
    	OrmImp orm=new OrmImp();
    	UserTableApi ap=new UserTableApi(orm);
    	List<UserinfoModel> userList=ap.fetchChatListByUid(1);
    	for(UserinfoModel userModel:userList){
        	System.out.println("Select list -> "+userModel.getUserId()+" , "+userModel.getUserName());
    	}
    	orm.beginTransaction();
    	UserinfoModel userModel= UserinfoModel.newBuilder().setUserName("varun").setCreatedTime(System.currentTimeMillis()).build();
    	Integer isUpdated=ap.addUser(userModel);
    	System.out.println(isUpdated);
    	orm.close();
    }
}
