package com.varun.Api;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.PasswordTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;


public class PassTableApi {
	private static final Logger logger=LoggerUtil.getLogger(PassTableApi.class);
	private static PasswordTableModel passObject=new PasswordTableModel();
	private static CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
    
   public PassTableApi() {
	   orm=new OrmImp(passObject);
   }
	
    public void closeOrmConnection(){
    	orm.close();
        logger.log(Level.INFO,"DB con closed");
    }

    public PasswordTableModel getPassById(Integer uid){
    	System.out.println(uid);
        logger.log(Level.INFO,"inside "+uid);
    	//query formation
    	try{
            logger.log(Level.INFO,"orm select query called");
    		orm.SelectQuery("pass_salt","pass_hash","created_time")
        	.Where(c.addEquals("user_id",uid)).And(c.addEquals("pass_status",1));
        	List<PasswordTableModel> selectList=(List<PasswordTableModel>)(Object)orm.getSelect();
            logger.log(Level.INFO,"got select list,list size="+selectList.size());
        	if(selectList.size()>0){
            	return selectList.get(0);
        	}
    	}catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	}
    	return null;
    }
    public List<PasswordTableModel> getAllPassById(Integer uid){
        logger.log(Level.INFO,"inside "+uid);
    	//query formation
    	try{
            logger.log(Level.INFO,"orm select query called");
    		orm.SelectQuery("pass_salt","pass_hash","pass_status","created_time")
        	.Where(c.addEquals("user_id",uid));
        	List<PasswordTableModel> selectList=(List<PasswordTableModel>)(Object)orm.getSelect();
            logger.log(Level.INFO,"got select lists,list size="+selectList.size());
        	if(selectList.size()>0){
            	return selectList;
        	}
    	}catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	}
    	return null;
    }
    public boolean addPass(Integer userid,String passSalt,String passHash){
    	PasswordTableModel passObj=new PasswordTableModel(userid,passSalt,passHash,1);
    	Integer returned=orm.Insert(passObj);
    	//if 0->inserted,if null->not inserted, or return gen id
    	if(returned==0){
    		return true;
    	}
    	return false;
    }
    public boolean updatePassById(PasswordTableModel obj,Integer uid){
    	boolean returned=orm.UpdateQuery(obj).Where(c.addEquals("user_id",uid)).update();
    	if(returned){
    		return true;
    	}
    	return false;
    }
}
