package com.varun.Api;

import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailTableModel;
import com.varun.Model.MobileTableModel;
import com.varun.Model.PasswordTableModel;
public class MobileTableApi {
	private static final Logger logger=LoggerUtil.getLogger(MobileTableApi.class);
	private static MobileTableModel mobileObject=new MobileTableModel();
	private static OrmImp ormObj;
	private CriteriaBuilder cb=new CriteriaBuilder();
	
	public MobileTableApi() {
		ormObj=new OrmImp(mobileObject);
	}
	
	public void closeOrmConnection(){
		ormObj.close();
	}
	
    public Integer getIdByMobile(String mobileno){
         logger.log(Level.INFO,"method called");
    	 ormObj.SelectQuery("user_id").Where(cb.addEquals("mobileno",mobileno));
    	 @SuppressWarnings("unchecked")
		 List<MobileTableModel> l=(List<MobileTableModel>)(Object)ormObj.getSelect();
    	 return l.get(0).getUser_id();
    }
    
    @SuppressWarnings("unchecked")
	public boolean checkMobile(Integer mobileno){
        logger.log(Level.INFO,"method called");
   	    ormObj.SelectQuery("user_id").Where(cb.addEquals("mobileno",mobileno));
   	    List<MobileTableModel> l=(List<MobileTableModel>)(Object)ormObj.getSelect();
   	    if(l.size()>0) {
		  return true;
	    }
	    return false;
    }
    
    public boolean addMobile(Integer uid,Integer mobile){
        logger.log(Level.INFO,"method called");
    	mobileObject.setUser_id(uid);
    	mobileObject.setMobileno(BigInteger.valueOf(mobile));
    	Integer isInserted=ormObj.Insert(mobileObject);
    	if(isInserted!=null){
    		return true;
    	}
    	return false;
    }
    public List<MobileTableModel> getMobileById(Integer id){
    	 logger.log(Level.INFO,"method called");
         try{
		   	 CriteriaBuilder cb=new CriteriaBuilder();
		   	 ormObj.SelectQuery("mobileno").Where(cb.addEquals("user_id",id));
		   	 List<MobileTableModel> l=(List<MobileTableModel>)(Object)ormObj.getSelect();
		   	 return l;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
}
