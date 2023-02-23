package com.varun.Api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailTableModel;
import com.varun.Model.GroupInfoModel;
import com.varun.Model.GroupMembersModel;
import com.varun.Model.MobileTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class GroupMemberTableApi {
	private static OrmImp ormObj;
	private static final Logger logger=LoggerUtil.getLogger(GroupMemberTableApi.class);
	private static GroupMembersModel memberObj=new GroupMembersModel();
	private CriteriaBuilder cb=new CriteriaBuilder();

	public GroupMemberTableApi(){
		try{
			ormObj=new OrmImp(memberObj);
		}catch (Exception e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"constructor",e);
		}
	}
	public void closeOrmConnection(){
		ormObj.close();
	}
	
    public OrmImp getGroupOrmByUid(Integer uid){
	   	 try{
	   		 logger.log(Level.INFO,"method called");
	    	 ormObj.SelectQuery("group_id").Where(cb.addEquals("member_id",uid));
	    	 return ormObj;
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
         return null;
    }
    
    public void addGroupMember(Integer adminid,Integer groupId,String[] members){
        try{
            logger.log(Level.INFO,"method called");
        	memberObj.setGroup_id(groupId);
    		memberObj.setMember_id(adminid);
    		ormObj.Insert(memberObj);
    		for(String memberId:members){
    			memberObj.setMember_id(Integer.parseInt(memberId));
    			ormObj.Insert(memberObj);
    		}
       	 }catch(Exception e){
    	        logger.log(Level.WARNING,"unexpected",e);
       	 }
	}
}
