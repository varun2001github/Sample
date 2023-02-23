package com.varun.Api;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Controller.Register;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailTableModel;
import com.varun.Model.PasswordTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class EmailTableApi {
	private static OrmImp ormObj;
	private static final Logger logger=LoggerUtil.getLogger(EmailTableApi.class);
	private static EmailTableModel emailObject=new EmailTableModel();
	
	public EmailTableApi(){
		try {
			ormObj=new OrmImp(emailObject);
		}catch (Exception e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"constructor",e);
		}
	}
	
	public void closeOrmConnection(){
		ormObj.close();
	}
	
    public Integer getIdByEmail(String email){
         logger.log(Level.INFO,"method called");
         try{
        	 CriteriaBuilder cb=new CriteriaBuilder();
        	 ormObj.SelectQuery("user_id").Where(cb.addEquals("emailid",email));
        	 @SuppressWarnings("unchecked")
			 List<EmailTableModel> l=(List<EmailTableModel>)(Object)ormObj.getSelect();
        	 return l.get(0).getUser_id();
     	 }catch(Exception e){
 	        logger.log(Level.WARNING,"unexpected",e);
     	 }
    	return null;
    }
    
    public boolean checkEmail(String email){
    	 try{
    		logger.log(Level.INFO,"method called");
       	    CriteriaBuilder cb=new CriteriaBuilder();
       	    ormObj.SelectQuery("user_id").Where(cb.addEquals("emailid",email));
       	    @SuppressWarnings("unchecked")
			List<EmailTableModel> l=(List<EmailTableModel>)(Object)ormObj.getSelect();
       	    if(l.size()>0){
       		   return true;
       	    }
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public boolean addEmail(Integer uid,String email){
    	try{
    		logger.log(Level.INFO,"method called");
        	emailObject.setUser_id(uid);
        	emailObject.setEmailid(email);
        	Integer isInserted=ormObj.Insert(emailObject);
        	if(isInserted!=null){
        		return true;
        	}
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public List<EmailTableModel> getEmailById(Integer id){
    	 logger.log(Level.INFO,"method called");
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("emailid").Where(cb.addEquals("user_id",id));
	       	 @SuppressWarnings("unchecked")
			 List<EmailTableModel> l=(List<EmailTableModel>)(Object)ormObj.getSelect();
	       	 return l;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
}
