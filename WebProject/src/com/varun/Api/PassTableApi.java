package com.varun.Api;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Model.PasswordTableModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;


public class PassTableApi {
	private static final Logger logger=LoggerUtil.getLogger(PassTableApi.class);
	private static PasswordTableModel passObject=null;
	private static String Table=PasswordTableModel.class.getAnnotation(Table.class).name();
	private static CriteriaBuilder c=new CriteriaBuilder();
	private OrmImp orm;
    
    public PassTableApi(OrmImp obj) {
	   this.orm=obj;
    }
    
    public PasswordTableModel getPassById(Integer uid){
    	System.out.println(uid);
        logger.log(Level.INFO,"inside "+uid);
    	//query formation
    	try{
            logger.log(Level.INFO,"orm select query called");
    		orm.SelectQuery("pass_salt","pass_hash","created_time").From(Table)
        	.Where(c.addEquals("user_id",uid)).And(c.addEquals("pass_status",1));
        	List<DataObject> selectList=orm.getSelect();
            logger.log(Level.INFO,"got select list,list size="+selectList.size());
        	if(selectList.size()>0){
            	return new PasswordTableModel(selectList.get(0));
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
    		orm.SelectAll().From(Table)
        	.Where(c.addEquals("user_id",uid));
        	List<DataObject> dataList=orm.getSelect();
        	List<PasswordTableModel> selectList=null;
        	if(dataList.size()>0) {
        		selectList=new ArrayList<PasswordTableModel>();
            	for(DataObject ob:dataList){
            		selectList.add(new PasswordTableModel(ob));
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
    	passObject=new PasswordTableModel(userid,passSalt,passHash);
    	System.out.println("API :"+passObject.getUser_id());
    	Integer returned=orm.InsertQuery(passObject.getDataObject()).Insert();
    	//if 0->inserted,if null->not inserted, or return gen id
    	if(returned!=null){
    		return true;
    	}
    	return false;
    }
    public boolean updatePassById(PasswordTableModel oldObj,PasswordTableModel newObj,Integer uid) throws JsonProcessingException{
    	boolean returned=orm.UpdateQuery(oldObj.getDataObject(),newObj.getDataObject()).Where(c.addEquals("user_id",uid)).update();
    	if(returned){
    		return true;
    	}
    	return false;
    }
}
