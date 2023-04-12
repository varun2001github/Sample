package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

@Path("/EmailApi")
public class EmailTableApi {
	private OrmImp ormObj=new OrmImp();
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
	public EmailTableApi(){
		
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
         	 if(dataList.size()>0){
             	l=new ArrayList<EmailTableModel>();
             	for(DataObject ob:dataList){
             		l.add(new EmailTableModel(ob));
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
    
    @GET
    @Path("/GetEmail/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmailTableModel> getEmailById(@PathParam("num") Integer id){
    	 logger.log(Level.INFO,"method called");
    	 System.out.println("new in");
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
    
    public List<DataObject> getEmailByUid( Integer id){
   	 logger.log(Level.INFO,"method called");
        try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("emailid").From(Table).Where(cb.addEquals("user_id",id));
	       	 List<DataObject> dataList=ormObj.getSelect();
       	     return dataList;
   	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
   	 }
	return null;
   }
    public List<DataObject> getAllEmail(){
   	 logger.log(Level.INFO,"method called");
        try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("emailid").From(Table);
	       	 List<DataObject> dataList=ormObj.getSelect();
       		 System.out.println("avl");
           	 return dataList;
   	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
   	 }
	 return null;
   }
    
//    @GET
//    @Path("/GetEmail/{num}")
//    @Produces(MediaType.APPLICATION_JSON)
    public String getEmailbi(@PathParam("num") Integer id){
    	 logger.log(Level.INFO,"method called");
    	 System.out.println("in");
    	 JSONArray ar=new JSONArray();
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectAll();
	       	 ormObj.From(Table);
	       	 ormObj.Where(cb.addEquals("user_id",id));
	       	 List<DataObject> dataList=ormObj.getSelect();
	       	 if(dataList!=null){
	       		if(dataList.size()>0){
	        		System.out.println("present");
	            	for(DataObject ob:dataList){
	            		ar.put(ob.getDataMap());
	            	}
	        	 }
	       	 }
        	 return ar.toString();
    	 }catch(Exception e){
    		System.out.println("ex");
    		e.printStackTrace();
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
    
    public static void main(String[] args){
    	EmailTableApi a=new EmailTableApi();
    	System.out.println(a.getEmailbi(1));
    }
}
