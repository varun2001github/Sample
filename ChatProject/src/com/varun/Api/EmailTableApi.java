package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class EmailTableApi {
	private OrmImp ormObj=null;
	private static final Logger logger=LoggerUtil.getLogger(EmailTableApi.class);
	private EmailTableModel emailObject=new EmailTableModel();
	private String Table=EmailTableModel.class.getAnnotation(Table.class).name();

	public EmailTableApi(OrmImp ob){
		try {
			this.ormObj=ob;
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
         
         if(email==null) {
        	 return null;
         }else if(email.trim().isEmpty() || email.length()>30 || !email.matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$") ) {
        	 return null;
         }
         
         try{
        	 CriteriaBuilder cb=new CriteriaBuilder();
        	 ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("emailid",email));
        	 List<DataObject> dataList=ormObj.getSelect();
         	 List<EmailTableModel> l=null;
         	 if(dataList.size()>0){
             	l=new ArrayList<EmailTableModel>();
             	for(DataObject ob:dataList){
             		l.add(new EmailTableModel(ob));
             	}
            	return l.get(0).getUser_id();
         	 }
     	 }catch(Exception e){
 	        logger.log(Level.WARNING,"unexpected",e);
     	 }
    	return null;
    }
    
    public Boolean checkEmail(String email){
    	
    	 if(email==null){
    		return false;
    	 }else if(email.trim().isEmpty() || email.length()>30 || !email.matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$")){
    		 return false;
    	 }
    	 
    	 try{
    		logger.log(Level.INFO,"method called");
       	    CriteriaBuilder cb=new CriteriaBuilder();
       	    ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("emailid",email));
        	List<DataObject> dataList=ormObj.getSelect();
       	    if(dataList.size()>0){
       		   return true;
       	    }
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public Boolean addEmail(Integer uid,String email){
    	
    	if(email==null || uid==null || uid<=0){
    		 return false;
    	}else if(email.trim().isEmpty() || email.length()>30 || uid<=0 ||
    		    !email.matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$")){
    		 return false;
    	}
    	
    	try{
    		logger.log(Level.INFO,"method called");
    		emailObject=new EmailTableModel();
        	emailObject.setUser_id(uid);
        	emailObject.setEmailid(email);
        	Integer isInserted=ormObj.InsertQuery(emailObject.getDataObject()).Insert();
        	if(isInserted!=null){
        		return true;
        	}
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public boolean updateEmail(EmailTableModel oldEmail,EmailTableModel newEmail){
    	if(oldEmail==null || newEmail==null){
    		return false;
    	}else if(oldEmail.getEmailid()==null|| newEmail.getEmailid()==null || oldEmail.getUser_id()==null || oldEmail.getUser_id()<=0 ){
    		 return false;
    	}else if(oldEmail.getEmailid().length()>30 ||
    			 newEmail.getEmailid().length()>30 ||
    			 oldEmail.getEmailid().trim().isEmpty() || 
    			 newEmail.getEmailid().trim().isEmpty() ||
    			 !oldEmail.getEmailid().matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$") ||
    			 !newEmail.getEmailid().matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$")) {
    		//max length in DB column is 100
    		return false;
    	}
    	
    	try{
    		logger.log(Level.INFO,"method called");
       	    CriteriaBuilder cb=new CriteriaBuilder();
        	ormObj.UpdateQuery(oldEmail.getDataObject(),newEmail.getDataObject())
        	.Where(cb.addEquals("emailid",oldEmail.getEmailid()))
        	.And(cb.addEquals("user_id",oldEmail.getUser_id()));
        	
        	boolean isUpdated=ormObj.update();
        	return isUpdated;
    	 }catch(Exception e){
    		 e.printStackTrace();
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public List<EmailTableModel> getEmailById(Integer id){
    	 
    	 if(id==null || id<=0){
       	    return null;
         }
    	
    	 logger.log(Level.INFO,"method called");
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("emailid").From(Table).Where(cb.addEquals("user_id",id));
	       	 List<DataObject> dataList=ormObj.getSelect();
        	 List<EmailTableModel> l=null;
        	 if(dataList.size()>0){
            	l=new ArrayList<EmailTableModel>();
            	for(DataObject ob:dataList){
            		l.add(new EmailTableModel(ob));
            	}
        	 }
	       	 return l;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
}
