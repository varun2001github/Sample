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
         try{
        	 CriteriaBuilder cb=new CriteriaBuilder();
        	 ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("emailid",email));
        	 List<DataObject> dataList=ormObj.getSelect();
         	 List<EmailTableModel> l=null;
         	 if(dataList.size()>0) {
             	l=new ArrayList<EmailTableModel>();
             	for(DataObject ob:dataList){
             		l.add(new EmailTableModel(ob.getDataMap()));
             	}
         	 }
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
    
    public boolean addEmail(Integer uid,String email){
    	try{
    		logger.log(Level.INFO,"method called");
    		emailObject=new EmailTableModel();
        	emailObject.setUser_id(uid);
        	emailObject.setEmailid(email);
        	Integer isInserted=ormObj.Insert(emailObject.getDataObject());
        	if(isInserted!=null){
        		return true;
        	}
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public boolean updateEmail(Integer userid,String oldEmail,String newEmail){
    	System.out.println("em upd");
    	try{
    		logger.log(Level.INFO,"method called");
       	    CriteriaBuilder cb=new CriteriaBuilder();
    		emailObject=new EmailTableModel();
        	emailObject.setEmailid(newEmail);
        	ormObj.UpdateQuery(emailObject.getDataObject())
        	.Where(cb.addEquals("emailid",oldEmail))
        	.And(cb.addEquals("user_id",userid));
        	
        	boolean isUpdated=ormObj.update();
        	return isUpdated;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public List<EmailTableModel> getEmailById(Integer id){
    	 logger.log(Level.INFO,"method called");
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("emailid").From(Table).Where(cb.addEquals("user_id",id));
	       	 List<DataObject> dataList=ormObj.getSelect();
        	 List<EmailTableModel> l=null;
        	 if(dataList.size()>0){
            	l=new ArrayList<EmailTableModel>();
            	for(DataObject ob:dataList){
            		l.add(new EmailTableModel(ob.getDataMap()));
            	}
        	 }
	       	 return l;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
}
