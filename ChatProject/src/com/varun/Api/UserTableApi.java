package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import com.ProtoModel.UserModel.UserinfoModel;
import com.varun.Model.DataObject;
import com.varun.Orm.*;

@Path("/UserApi")
public class UserTableApi{
	//logger 
	private static final Logger logger=Logger.getLogger(UserTableApi.class.getName());
	private static UserinfoModel userObject=null;
	private CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
	private static String Table="userinfo";
	
    public UserTableApi(OrmImp ob){
		try{
			this.orm=ob;
   	    	logger.log(Level.INFO,"constructor");
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public UserinfoModel getUser(Integer userid){
	    logger.log(Level.INFO,"id passed "+userid);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",userid));
    	
    	EmailTableApi emailApi=new EmailTableApi(orm);
		MobileTableApi mobileApi=new MobileTableApi(orm);
		
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
	    
		for(Map.Entry<String, Object> entry : dataList.get(0).getDataMap().entrySet()){
		    System.out.println(entry.getKey()+" = "+entry.getValue());
		}
		
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
   	       UserinfoModel user=(UserinfoModel)ProtoMapper.getProtoObject(UserinfoModel.newBuilder(),dataList.get(0));
   	       user=user.toBuilder().addAllMobileObj(mobileApi.getMobileById(userid))
		                     .addAllEmailObj(emailApi.getEmailById(userid)).build();
    	   return user;
    	}
    	return null;
    }
  //proto 
    public UserinfoModel getUserSample(Integer userid){
	    logger.log(Level.INFO,"id passed "+userid);

    	//query formation
    	orm.SelectAll().From(Table)
    	.Where(c.addEquals("user_id",userid));
    	
    	EmailTableApi emailApi=new EmailTableApi(orm);
		MobileTableApi mobileApi=new MobileTableApi(orm);
		
	    logger.log(Level.INFO," query generated ");
	    List<DataObject> dataList=orm.getSelect();
	    
		for(Map.Entry<String, Object> entry : dataList.get(0).getDataMap().entrySet()){
		    System.out.println(entry.getKey()+" = "+entry.getValue());
		}
    	if(dataList.size()>0){
   	       logger.log(Level.INFO,"select list available");
   	      
   	       UserinfoModel user=(UserinfoModel)ProtoMapper.getProtoObject(UserinfoModel.newBuilder(),dataList.get(0));
   	       user=user.toBuilder().addAllMobileObj(mobileApi.getMobileById(userid))
		                     .addAllEmailObj(emailApi.getEmailById(userid)).build();
    	   return user;
    	}
    	return null;
    }
    public List<UserinfoModel> fetchChatList(Integer uid){
    	orm.SelectQuery("user_id","user_name").From(Table).Where(c.addNotEquals("user_id",uid));
    	System.out.println(orm.getQuery());
    	List<DataObject> dataList=orm.getSelect();
    	List<UserinfoModel> l=null;
    	if(dataList.size()>0){
        	l=new ArrayList<UserinfoModel>();
        	for(DataObject ob:dataList){
        		l.add((UserinfoModel)ProtoMapper.getProtoObject(UserinfoModel.newBuilder(),ob));
        	}
    	}
    	return l;
    }
    
    public boolean updateUserinfo(UserinfoModel oldObj,UserinfoModel newObj){
	    logger.log(Level.INFO,"METHOD CALLED");
    	orm.UpdateQuery(ProtoMapper.getDataObject(oldObj),ProtoMapper.getDataObject(newObj))
    	.Where(c.addEquals("user_id",oldObj.getUserId()));
        boolean isUpdated=orm.update();
	    logger.log(Level.INFO,"object updated");
    	return isUpdated;
    }
    
    public Integer addUser(UserinfoModel userObject){
    	orm.InsertQuery(ProtoMapper.getDataObject(userObject));
    	System.out.println(orm.getQuery());
    	Integer id=orm.Insert();
	    logger.log(Level.INFO,"object inserted");
    	return id;
    }
    
    public static void main(String[] args){
    	OrmImp orm=new OrmImp();
    	UserTableApi ap=new UserTableApi(orm);
    	UserinfoModel userModel=ap.getUserSample(1);
    	System.out.println("Select list !?->> "+userModel.getUserId()+" , "+userModel.getUserName()+": "+userModel.getEmailObjList().size());

//    	List<UserinfoModel> userList=ap.fetchChatList(1);
//    	for(UserinfoModel userModel:userList){
//        	System.out.println("Select list -> "+userModel.getUserId()+" , "+userModel.getUserName());
//    	}
//    	orm.beginTransaction();
//    	UserinfoModel userModel= UserinfoModel.newBuilder().setUserName("varun").setCreatedTime(System.currentTimeMillis()).build();
//    	Integer isUpdated=ap.addUser(userModel);
//    	System.out.println(isUpdated);
//    	orm.close();
    }
}
