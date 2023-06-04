package com.varun.Api;

import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.ProtoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.Interface.MobileApi;
import com.varun.Model.DataObject;
import com.ProtoModel.UserModel.Mobile;

public class MobileApiImpl implements MobileApi{
	private static final Logger logger=Logger.getLogger(MobileApiImpl.class.getName());
	private OrmImp ormObj;
	private CriteriaBuilder cb=new CriteriaBuilder();
	private static Mobile mobileObject=null;
	private static String Table="mobile";

	public MobileApiImpl(OrmImp obj){
		this.ormObj=obj;
	}
    
	public MobileApiImpl(){
		this.ormObj=new OrmImp();
	}
	
    @Override
	public Integer getIdByMobile(String mobileno){
         logger.log(Level.INFO,"method called");
    	 ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("mobileno",mobileno));
    	 List<DataObject> dataList=ormObj.getSelect();
    	 mobileObject=(Mobile)ProtoMapper.getProtoObject(Mobile.newBuilder(),dataList.get(0));
    	 return (Integer) mobileObject.getUserId();
    }
    
	@Override
	public boolean checkMobile(Long mobileno){
        logger.log(Level.INFO,"method called");
   	    ormObj.SelectQuery("user_id").From(Table).Where(cb.addEquals("mobileno",mobileno));
   	    List<DataObject> l=ormObj.getSelect();
   	    if(l.size()>0) {
		  return true;
	    }
	    return false;
    }
    
    @Override
	public boolean addMobile(Integer uid,Long mobile){
        logger.log(Level.INFO,"method called");
        Mobile mobileObject=Mobile.newBuilder().setUserId(uid).setMobileno(Integer.parseInt(""+mobile)).build();
//    	mobileObject.setUser_id(uid);
//    	mobileObject.setMobileno(mobile);
    	System.out.println("mob ins pass"+mobileObject.getCreatedTime());
    	Integer isInserted=ormObj.InsertQuery(ProtoMapper.getDataObject(mobileObject)).Insert();
    	if(isInserted!=null){
    		return true;
    	}
    	return false;
    }
    
    @Override
	public boolean updateMobile(Mobile oldMobileObject,Mobile  newMobileObject){
    	System.out.println("mob upd");
    	try{
       	    CriteriaBuilder cb=new CriteriaBuilder();
    		logger.log(Level.INFO,"method called");

        	ormObj.UpdateQuery(ProtoMapper.getDataObject(oldMobileObject),ProtoMapper.getDataObject(newMobileObject))
        	.Where(cb.addEquals("mobileno",oldMobileObject.getMobileno()))
        	.And(cb.addEquals("user_id",oldMobileObject.getUserId()));
        	boolean isInserted=ormObj.update();
        	return isInserted;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
         return false;
    }
    
    @Override
	public List<Mobile> getMobileById(Integer id){
    	 logger.log(Level.INFO,"method called");
         try{
		   	CriteriaBuilder cb=new CriteriaBuilder();
		   	ormObj.SelectQuery("user_id","mobileno").From(Table).Where(cb.addEquals("user_id",id));
		   	List<DataObject> dataList=ormObj.getSelect();
	    	List<Mobile> ModelList=null;
	    	if(dataList.size()>0){
	    		ModelList=new ArrayList<Mobile>();
	        	for(DataObject ob:dataList){
	        		ModelList.add((Mobile)ProtoMapper.getProtoObject(Mobile.newBuilder(),ob));
	        	}
	    	}		
	    	return ModelList;
    	 }catch(Exception e){
	        logger.log(Level.WARNING,"unexpected",e);
    	 }
		 return null;
    }
}
