package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Model.DataObject;
import com.ProtoModel.UserModel.EmailModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.ProtoMapper;

public class EmailTableApi {
	private OrmImp ormObj=null;
	private static final Logger logger=Logger.getLogger(EmailTableApi.class.getName());
	private EmailModel emailObject=null;
	private String Table="email";

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
         
         if(email==null){
        	 return null;
         }else if(email.trim().isEmpty() || email.length()>30 || !email.matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$") ){
        	 return null;
         }
         
         try{
        	 CriteriaBuilder cb=new CriteriaBuilder();
        	 ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("emailid",email));
        	 List<DataObject> dataList=ormObj.getSelect();
         	 EmailModel emailModel=null;
         	 if(dataList.size()>0){
         		emailModel=(EmailModel)ProtoMapper.getProtoObject(EmailModel.newBuilder(), dataList.get(0));
            	return emailModel.getUserId();
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
    		emailObject=EmailModel.newBuilder()
    				.setUserId(uid)
    				.setEmailid(email).build();
        	Integer isInserted=ormObj.InsertQuery(ProtoMapper.getDataObject(emailObject)).Insert();
        	if(isInserted!=null){
        		return true;
        	}
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public boolean updateEmail(EmailModel oldEmail,EmailModel newEmail){
    	System.out.println("before updateEmail");
		logger.log(Level.INFO,"method called");
    	if(oldEmail==null || newEmail==null){
    		return false;
    	}else if(oldEmail.getEmailid()==null || oldEmail.getEmailid()=="" || newEmail.getEmailid()==null || newEmail.getEmailid()=="" || oldEmail.getUserId()<=0 ){
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
    	System.out.println("after updateEmail");
    	try{
    		logger.log(Level.INFO,"method called");
    		System.out.println("query creating");
       	    CriteriaBuilder cb=new CriteriaBuilder();
        	ormObj.UpdateQuery(ProtoMapper.getDataObject(oldEmail),ProtoMapper.getDataObject(newEmail))
        	.Where(cb.addEquals("emailid",oldEmail.getEmailid()))
        	.And(cb.addEquals("user_id",oldEmail.getUserId()));
            logger.log(Level.INFO,"Email up query"+ormObj.getQuery());
        	boolean isUpdated=ormObj.update();
        	return isUpdated;
    	 }catch(Exception e){
    		 e.printStackTrace();
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public List<EmailModel> getEmailById(Integer id){
    	 if(id==null || id<=0){
       	    return null;
         }
    	 logger.log(Level.INFO,"method called");
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("user_id","emailid").From(Table).Where(cb.addEquals("user_id",id));
	       	 List<DataObject> dataList=ormObj.getSelect();
        	 List<EmailModel> l=null;
        	 if(dataList.size()>0){
            	l=new ArrayList<EmailModel>();
            	for(DataObject object:dataList){
            		l.add((EmailModel)ProtoMapper.getProtoObject(EmailModel.newBuilder(),object));
            	}
        	 }
	       	 return l;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
	public static void main(String args[]){
		EmailTableApi ap=new EmailTableApi(new OrmImp());
		System.out.println(ap.getEmailById(1).get(0).getEmailid());
	}
}
