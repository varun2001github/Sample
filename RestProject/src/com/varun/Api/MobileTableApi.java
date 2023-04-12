package com.varun.Api;

import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

import java.math.BigInteger;
import java.sql.SQLException;
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
import com.varun.Model.MobileTableModel;
import com.varun.Model.PasswordTableModel;
import com.varun.Model.UserinfoTableModel;

@Path("/MobileApi")
public class MobileTableApi{
	private static final Logger logger=LoggerUtil.getLogger(MobileTableApi.class);
	private OrmImp ormObj=new OrmImp();
	private CriteriaBuilder cb=new CriteriaBuilder();
	private static MobileTableModel mobileObject=null;
	private static String Table=MobileTableModel.class.getAnnotation(Table.class).name();

	public MobileTableApi(OrmImp obj){
		this.ormObj=obj;
	}
	public MobileTableApi(){
	}
    public Integer getIdByMobile(String mobileno){
         logger.log(Level.INFO,"method called");
    	 ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("mobileno",mobileno));
    	 List<DataObject> dataList=ormObj.getSelect();
    	 return (Integer) dataList.get(0).getDataMap().get("user_id");
    }
    
	public boolean checkMobile(Long mobileno){
        logger.log(Level.INFO,"method called");
   	    ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("mobileno",mobileno));
   	    List<DataObject> l=ormObj.getSelect();
   	    if(l.size()>0){
		  return true;
	    }
	    return false;
    }
    
    public boolean addMobile(Integer uid,Long mobile){
        logger.log(Level.INFO,"method called");
        MobileTableModel mobileObject=new MobileTableModel();
    	mobileObject.setUser_id(uid);
    	mobileObject.setMobileno(mobile);
    	System.out.println("mob ins pass"+mobileObject.getCreated_time());
    	Integer isInserted=ormObj.Insert(mobileObject.getDataObject());
    	if(isInserted!=null){
    		return true;
    	}
    	return false;
    }
    
    public boolean updateMobile(Integer userid,Long oldMobileno,Long  newMobileno){
    	System.out.println("mob upd");
    	try{
       	    CriteriaBuilder cb=new CriteriaBuilder();
    		logger.log(Level.INFO,"method called");
            mobileObject=new MobileTableModel();
    		mobileObject.setMobileno(newMobileno);
        	ormObj.UpdateQuery(mobileObject.getDataObject()).Where(cb.addEquals("mobileno",oldMobileno)).And(cb.addEquals("user_id",userid));
        	boolean isInserted=ormObj.update();
        	return isInserted;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    public List<MobileTableModel> getMobileById(Integer id){
    	 logger.log(Level.INFO,"method called");
         try{
		   	CriteriaBuilder cb=new CriteriaBuilder();
		   	ormObj.SelectQuery("mobileno").From(Table).Where(cb.addEquals("user_id",id));
		   	List<DataObject> dataList=ormObj.getSelect();
	    	List<MobileTableModel> ModelList=null;
	    	if(dataList.size()>0) {
	    		ModelList=new ArrayList<MobileTableModel>();
	        	for(DataObject ob:dataList){
	        		ModelList.add(new MobileTableModel(ob));
	        	}
	    	}		
	    	return ModelList;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		return null;
    }
    
    @GET
    @Path("/GetMobile/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMobileByI(@PathParam("num") Integer id){
    	 logger.log(Level.INFO,"method called");
    	 System.out.println("in");
    	 JSONArray ar=new JSONArray();
         try{
	       	 CriteriaBuilder cb=new CriteriaBuilder();
	       	 ormObj.SelectQuery("mobileno").From(Table).Where(cb.addEquals("user_id",id));
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
}
