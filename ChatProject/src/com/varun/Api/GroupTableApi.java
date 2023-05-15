package com.varun.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Model.DataObject;
import com.varun.Model.GroupInfoModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class GroupTableApi{
	private GroupInfoModel grpObj=null;
	private static OrmImp ormObj;
	private static final Logger logger=Logger.getLogger(GroupTableApi.class.getName());
	private CriteriaBuilder cb=new CriteriaBuilder();
	private String Table="group_info";
	
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
	       	if(dataList.size()>0){
	           	l=new ArrayList<GroupInfoModel>();
	           	for(DataObject object:dataList){
	           		l.add(new GroupInfoModel(object.getDataMap()));
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
        DataObject ob=new DataObject();
        GroupInfoModel gm=(GroupInfoModel)ob;
        try{
        	grpObj.setGroup_name(groupname);
    		grpObj.setAdmin_id(adminid);
    		Integer grpId=ormObj.InsertQuery(grpObj.getDataObject()).Insert();
    		return grpId;
	   	}catch(Exception e){
		     logger.log(Level.WARNING,"unexpected",e);
	   	}
		return null;
		
	}
	 public static void main(String args[]){
		  DataObject obj=new DataObject();
		  GroupInfoModel g;

		  try {
			  g=(GroupInfoModel)obj;
		  }catch(Exception e) {
			  
		  }
		  System.out.println(obj instanceof GroupInfoModel);
		  
//		  GroupTableApi go=new GroupTableApi(new OrmImp());
//		   List<GroupInfoModel> ob=go.fetchChatList(1);
//		   if(ob!=null) {
//			   for(GroupInfoModel g:ob){
//				   System.out.println(g.getGroup_name()+g.getGroup_id());
//			   } 
//		   }
	  }
	
}
