package com.varun.Api;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ProtoModel.UserModel.PasswordModel;
import com.varun.Model.DataObject;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.ProtoMapper;

public class PassTableApi{
	private static final Logger logger=Logger.getLogger(PassTableApi.class.getName());
	private static PasswordModel passObject=PasswordModel.getDefaultInstance();
	private static String Table="user_pass";
	private static CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
    
    public PassTableApi(OrmImp obj){
	   this.orm=obj;
    }
    
    public PasswordModel getPassById(Integer uid){
        logger.log(Level.INFO,"inside "+uid);
    	//query formation
    	try{
            logger.log(Level.INFO,"orm select query called");
    		orm.SelectQuery("pass_salt","pass_hash","created_time").From(Table)
        	.Where(c.addEquals("user_id",uid)).And(c.addEquals("pass_status",1));
        	List<DataObject> selectList=orm.getSelect();
            logger.log(Level.INFO,"got select list,list size="+selectList.size());
        	if(selectList.size()>0){
            	return (PasswordModel)ProtoMapper.getProtoObject(PasswordModel.newBuilder(),selectList.get(0));
        	}
    	}catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	}
    	return null;
    }
    
    public PasswordModel getPassByloginid(String loginid){
    	System.out.println(loginid);
        logger.log(Level.INFO,"inside "+loginid);
    	//query formation
    	try{

    		CriteriaBuilder criteria=new CriteriaBuilder();
            logger.log(Level.INFO,"orm select query called");
            
            orm.SelectQuery("user_pass.user_id","pass_salt","pass_hash","user_pass.created_time").From(Table);
            
    		if(loginid.matches("[0-9]+")){
            	orm.InnerJoin("mobile",criteria.joinCondition("user_pass.user_id","mobile.user_id"))
            	.Where(criteria.addEquals("pass_status",1))
            	.And(criteria.addEquals("mobileno",Integer.parseInt(loginid)));
            }else{
        		orm.InnerJoin("email",criteria.joinCondition("user_pass.user_id","email.user_id"))
            	.Where(criteria.addEquals("pass_status",1))
            	.And(criteria.addEquals("emailid",loginid));
            }
            
        	List<DataObject> selectList=orm.getSelect();
            logger.log(Level.INFO,"got select list,list size="+selectList.size());
        	if(selectList.size()>0){
            	return (PasswordModel)ProtoMapper.getProtoObject(PasswordModel.newBuilder(),selectList.get(0));
        	}
    	}catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	}
    	return null;
    }
    
    public List<PasswordModel> getAllPassById(Integer uid){
        logger.log(Level.INFO,"inside "+uid);
    	//query formation
    	try{
            logger.log(Level.INFO,"orm select query called");
    		orm.SelectAll().From(Table)
        	.Where(c.addEquals("user_id",uid));
        	List<DataObject> dataList=orm.getSelect();
        	List<PasswordModel> selectList=null;
        	if(dataList.size()>0) {
        		selectList=new ArrayList<PasswordModel>();
            	for(DataObject object:dataList){
            		selectList.add((PasswordModel)ProtoMapper.getProtoObject(PasswordModel.newBuilder(),object));
            	}
            	logger.log(Level.INFO,"got select lists,list size="+selectList.size());
                return selectList;
        	}
        }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	}
    	return null;
    }
    
    public boolean addPass(Integer userid,String passSalt,String passHash){
    	passObject=PasswordModel.newBuilder()
    			.setUserId(userid)
    			.setPassSalt(passSalt)
    			.setPassHash(passHash).build();
    	System.out.println("API :"+passObject.getUserId());
    	Integer returned=orm.InsertQuery(ProtoMapper.getDataObject(passObject)).Insert();
    	//if 0->inserted,if null->not inserted, or return gen id
    	if(returned!=null){
    		return true;
    	}
    	return false;
    }
    
    public boolean updatePassById(PasswordModel oldObj,PasswordModel newObj,Integer uid){
    	boolean returned=orm.UpdateQuery(ProtoMapper.getDataObject(oldObj),ProtoMapper.getDataObject(newObj))
    			         .Where(c.addEquals("user_id",uid)).update();
    	if(returned){
    		return true;
    	}
    	return false;
    }
}
