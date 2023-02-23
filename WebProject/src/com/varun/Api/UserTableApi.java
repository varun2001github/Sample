package com.varun.Api;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.*;


public class UserTableApi {
	//logger 
	private static final Logger logger=LoggerUtil.getLogger(UserTableApi.class);
	private static UserinfoTableModel userObject=new UserinfoTableModel();
	private CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
	
    public UserTableApi(){
   	    try{
			orm=new OrmImp(userObject);
   	    	logger.log(Level.INFO," constructor ");
		}catch (Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public void closeOrmConnection(){
    	orm.close();
	}
    
    public UserTableApi(UserinfoTableModel userObject){
   	    try{
   	    	UserTableApi.userObject=userObject;
			orm=new OrmImp(userObject);
   	    	logger.log(Level.INFO," constructor ");
		}catch (Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public UserinfoTableModel getUserById(Integer id){
    	
    	//query formation
    	orm.SelectQuery("user_id","user_name")
    	.Where(c.addEquals("user_id",id));
	    logger.log(Level.INFO," query generated ");
    	List<UserinfoTableModel> selectList=(List<UserinfoTableModel>)(Object)orm.getSelect();
    	if(selectList.size()>0){
   	       logger.log(Level.INFO,"select list available");
    	   return selectList.get(0);
    	}
    	return null;
    }
    
    public boolean updateUserinfo(UserinfoTableModel obj){
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(obj).Where(c.addEquals("user_id",obj.getUser_id()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    public Integer addUser(String username){
    	UserinfoTableModel obj=new UserinfoTableModel();
	    logger.log(Level.INFO,"METHOD CALLED");
    	obj.setUser_name(username);
    	Integer id=orm.Insert(obj);
	    logger.log(Level.INFO,"object inserted");
    	return id;
    }
    public List<UserinfoTableModel> fetchChatList(Integer uid){
    	orm.SelectQuery("user_id","user_name").Where(c.addNotEquals("user_id",uid));
    	List<UserinfoTableModel> l=(List<UserinfoTableModel>)(Object)orm.getSelect();
    	return l;
    }
}
