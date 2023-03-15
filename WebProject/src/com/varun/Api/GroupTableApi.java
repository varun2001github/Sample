package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Model.GroupInfoModel;
import com.varun.Model.GroupMembersModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class GroupTableApi{
	private GroupInfoModel grpObj=null;
	private static OrmImp ormObj;
	private static Logger logger=LoggerUtil.getLogger(GroupTableApi.class);
	private CriteriaBuilder cb=new CriteriaBuilder();
	private String Table=GroupInfoModel.class.getAnnotation(Table.class).name();
	
	public GroupTableApi(OrmImp obj){
		ormObj=obj;
	}
    public void closeOrmConnection(){
    	ormObj.close();
	}
	public List<GroupInfoModel> fetchChatList(Integer uid){
        logger.log(Level.INFO,"method called");
        try{
        	GroupMemberTableApi groupmemApi=new GroupMemberTableApi(new OrmImp());
    		ormObj.SelectQuery("group_id","group_name").From(Table).Where(cb.addIn("group_id",groupmemApi.getGroupOrmByUid(uid)));
            System.out.println(ormObj.getQuery());
	       	List<DataObject> dataList=ormObj.getSelect();
	       	List<GroupInfoModel> l=null;
	       	if(dataList.size()>0) {
	           	l=new ArrayList<GroupInfoModel>();
	           	for(DataObject ob:dataList){
	           		l.add(new GroupInfoModel(ob));
	           	}
	       	}
	        return l;
		 }catch(Exception e){
			     logger.log(Level.WARNING,"unexpected",e);
		 }
		 return null;
    }
	public Integer addGroup(int adminid,String groupname){
        logger.log(Level.INFO,"method called");
        grpObj=new GroupInfoModel();
        try{
        	grpObj.setGroup_name(groupname);
    		grpObj.setAdmin_id(adminid);
    		Integer grpId=ormObj.Insert(grpObj.getDataObject());
    		return grpId;
	   	}catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	}
		return null;
		
	}
}
