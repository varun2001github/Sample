package com.varun.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.User;
import com.varun.Api.Interface.EmailApi;
import com.varun.Api.Interface.MobileApi;
import com.varun.Api.Interface.UserApi;
import com.varun.Model.DataObject;
import com.varun.Orm.*;

public class UserApiImpl implements UserApi{
	//logger 
	private static final Logger logger=Logger.getLogger(UserApiImpl.class.getName());
	private static User userObject=null;
	private CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
	private static String Table="userinfo";
	private int ormFlag=0; 
	
    public UserApiImpl(OrmImp ob){
    	ormFlag=1;
		try{
			this.orm=ob;
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public UserApiImpl(){
		try{
			this.orm=new OrmImp();
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public String getString() {
    	return "varun";
    }
    
    @Override
	public User getUser(Integer userid){
	    logger.log(Level.INFO,"id passed "+userid);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",userid));
    	
    	EmailApi emailApiImpl=new EmailApiImpl(orm);
		MobileApi mobileApi=new MobileApiImpl(orm);
		
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
	    
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
   	       User user=(User)ProtoMapper.getProtoObject(User.newBuilder(),dataList.get(0));
   	       user=user.toBuilder().addAllMobileObject(mobileApi.getMobileById(userid))
		                     .addAllEmailObject(emailApiImpl.getEmailById(userid)).build();
   	       
    	   return user;
    	}
    	return null;
    }
    
    @Override
    public User getUser(String emailId){
    	EmailApi emailApiImpl=new EmailApiImpl();
    	Integer userId=emailApiImpl.getIdByEmail(emailId);
    	logger.log(Level.INFO,"id passed "+userId);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",userId));
    	
		MobileApi mobileApi=new MobileApiImpl(orm);
		
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
	    
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
   	       User user=(User)ProtoMapper.getProtoObject(User.newBuilder(),dataList.get(0));
   	       user=user.toBuilder().addAllMobileObject(mobileApi.getMobileById(userId))
		                     .addAllEmailObject(emailApiImpl.getEmailById(userId)).build();
   	       
    	   return user;
    	}
    	
    	return null;
    }
        
    @Override
	public List<User> fetchChatList(Integer uid){
    	orm.SelectQuery("user_id","user_name").From(Table).Where(c.addNotEquals("user_id",uid));
    	System.out.println(orm.getQuery());
    	List<DataObject> dataList=orm.getSelect();
    	List<User> l=null;
    	if(dataList.size()>0){
        	l=new ArrayList<User>();
        	for(DataObject ob:dataList){
        		l.add((User)ProtoMapper.getProtoObject(User.newBuilder(),ob));
        	}
    	}
    	return l;
    }
    
    @Override
    public HashMap<Integer, User> getUsers(List<Integer> userIds){
    	HashMap<Integer, User> result = new HashMap<Integer, User>();
    	for(Integer userid : userIds){
    		orm.SelectAll().From(Table)
        	.Where(c.addEquals("user_id",userid));
        	
        	EmailApi emailApiImpl=new EmailApiImpl(orm);
    		MobileApi mobileApi=new MobileApiImpl(orm);
    		
    	    logger.log(Level.INFO," query generated");
    	    List<DataObject> dataList=orm.getSelect();
    	    
        	if(dataList.size()>0){
       	       logger.log(Level.INFO,"select list available");
       	       User user=(User)ProtoMapper.getProtoObject(User.newBuilder(),dataList.get(0));
       	       user=user.toBuilder().addAllMobileObject(mobileApi.getMobileById(userid))
    		                     .addAllEmailObject(emailApiImpl.getEmailById(userid)).build();
       	       
        	   result.put(userid, user);
        	}
        	result.put(userid, null);
    	}
    	return result.isEmpty()? null : result;
    }
    
    @Override
	public boolean updateUser(User oldObj,User newObj){
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(ProtoMapper.getDataObject(oldObj),ProtoMapper.getDataObject(newObj))
    	.Where(c.addEquals("user_id",oldObj.getUserId()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    @Override
	public Integer addUser(User userObject){
    	orm.InsertQuery(ProtoMapper.getDataObject(userObject));
    	Integer id=orm.Insert();
    	return id;
    }
    
    public static void main(String[] args){
    	OrmImp orm=new OrmImp();
    	UserApi ap=new UserApiImpl(orm);
    	User userModel=ap.getUser(1);
    	System.out.println("Select list !?->> "+userModel.getUserId()+" , "+userModel.getUserName()+": "+userModel.getEmailObjectList().size());
    }
}
