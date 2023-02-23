package com.varun.Api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.GroupInfoModel;
import com.varun.Model.GroupMembersModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class GroupTableApi{
	private static GroupInfoModel grpObj=new GroupInfoModel();
	private static OrmImp ormObj;
	private static final Logger logger=LoggerUtil.getLogger(GroupTableApi.class);
	private CriteriaBuilder cb=new CriteriaBuilder();
    
	public void closeOrmConnection(){
		ormObj.close();
	}
	
	public GroupTableApi(){
		ormObj=new OrmImp(grpObj);
	}
	
	public List<GroupInfoModel> fetchChatList(Integer uid){
        logger.log(Level.INFO,"method called");
        try{
        	GroupMemberTableApi groupmemApi=new GroupMemberTableApi();
    		ormObj.SelectQuery("group_id","group_name").Where(cb.addIn("group_id",groupmemApi.getGroupOrmByUid(uid)));
        	@SuppressWarnings("unchecked")
			List<GroupInfoModel> l=(List<GroupInfoModel>)(Object)ormObj.getSelect();
        	return l;
	   	 }catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	 }
		return null;
    }
	public Integer addGroup(int adminid,String groupname){
        logger.log(Level.INFO,"method called");
        try{
        	grpObj.setGroup_name(groupname);
    		grpObj.setAdmin_id(adminid);
    		Integer grpId=ormObj.Insert(grpObj);
    		return grpId;
	   	}catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	}
		return null;
		
	}
}
