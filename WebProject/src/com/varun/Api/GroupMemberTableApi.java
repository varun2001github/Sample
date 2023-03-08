package com.varun.Api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.GroupMembersModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class GroupMemberTableApi {
	private OrmImp ormObj;
	private static final Logger logger=LoggerUtil.getLogger(GroupMemberTableApi.class);
	private GroupMembersModel memberObj=null;
	private CriteriaBuilder cb=new CriteriaBuilder();
	private String Table=GroupMembersModel.class.getAnnotation(Table.class).name();

	public GroupMemberTableApi(OrmImp obj){
		try{
			this.ormObj=obj;
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
	    	 ormObj.SelectQuery("group_id").From(Table).Where(cb.addEquals("member_id",uid));
	    	 return ormObj;
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
         return null;
    }
    
    public void addGroupMember(Integer adminid,Integer groupId,String[] members){
        try{
        	memberObj=new GroupMembersModel();
            logger.log(Level.INFO,"method called");
        	memberObj.setGroup_id(groupId);
    		memberObj.setMember_id(adminid);
    		ormObj.Insert(memberObj.getDataObject());
    		for(String memberId:members){
    			memberObj.setMember_id(Integer.parseInt(memberId));
    			ormObj.Insert(memberObj.getDataObject());
    		}
       	 }catch(Exception e){
    	        logger.log(Level.WARNING,"unexpected",e);
       	 }
	}
}
