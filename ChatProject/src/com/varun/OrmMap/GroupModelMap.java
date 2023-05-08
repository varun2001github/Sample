package com.varun.OrmMap;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.ProtoModel.UserModel.GroupInfoModel;
import com.ProtoModel.UserModel.GroupInfoModel.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.varun.Model.DataObject;

public class GroupModelMap{
	
	private static final Logger logger=Logger.getLogger(GroupModelMap.class.getName());
	
	private HashMap<String,Object> map=new HashMap<String,Object>();
    
	private GroupInfoModel groupInfoModel=GroupInfoModel.getDefaultInstance();

	public static enum DbColumn{
		group_id,
		group_name,
		admin_id,
		created_time,
		modif_time;
    }
	
/////
	public GroupModelMap(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	public GroupModelMap(){
	}
	
	private void setMapInVariables(){
//		groupInfoModel=GroupInfoModel.newBuilder()
//				.setGroupId((Integer)map.get(DbColumn.group_id.name()))
//				.setGroupName((String)map.get(DbColumn.group_name.name()))
//				.setAdminId((Integer)map.get(DbColumn.admin_id.name()))
//				.setCreatedTime((Long)map.get(DbColumn.created_time.name()))
//				.setModifTime((Long)map.get(DbColumn.modif_time.name())).build();
		
		//null check and set value
	    Builder groupBuilder=GroupInfoModel.newBuilder();
		FieldDescriptor field;
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()!=null){
				field=groupBuilder.getDescriptorForType().findFieldByName(entry.getKey());
				groupBuilder.setField(field,entry.getValue());
			}
		}
		groupInfoModel=groupBuilder.build();
				
	}
	
	private void setVariablesInMap(){
		map.put("Table","group_info");
		map.put(DbColumn.group_id.name(),groupInfoModel.getGroupId());
		map.put(DbColumn.group_name.name(),groupInfoModel.getGroupName());
		map.put(DbColumn.admin_id.name(),groupInfoModel.getAdminId());
	    map.put(DbColumn.created_time.name(),groupInfoModel.getCreatedTime());
		map.put(DbColumn.modif_time.name(),groupInfoModel.getModifTime());
	}
    
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
	}

	public GroupInfoModel getGroupInfoModel(){
		return groupInfoModel;
	}
	
	public void setGroupInfoModel(GroupInfoModel groupInfoModel) {
		this.groupInfoModel = groupInfoModel;
	}
	
}
