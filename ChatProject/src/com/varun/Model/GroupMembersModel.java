package com.varun.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailModel.DbColumn;
import com.varun.Orm.Table;

public class GroupMembersModel{
	
	private static final Logger logger=Logger.getLogger(GroupMembersModel.class.getName());
	
	private HashMap<String,Object> map=new HashMap<String,Object>();

    Integer member_id=null;
	
 	Integer group_id=null;
 	
 	Long created_time=null;

	Long modif_time=null;

	public GroupMembersModel() {
	}

	public static enum DbColumn{
		member_id,
		group_id,
		created_time,
		modif_time;
	}
	
	///// constructor
	public GroupMembersModel(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	
	private void setMapInVariables(){
		this.member_id=(Integer)map.get(DbColumn.member_id.name());
		this.group_id=(Integer)map.get(DbColumn.group_id.name());
		this.created_time=(Long)map.get(DbColumn.created_time.name());
		this.modif_time=(Long)map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","group_members");
		map.put(DbColumn.member_id.name(),this.member_id);
		map.put(DbColumn.group_id.name(),this.group_id);
		map.put(DbColumn.created_time.name(),this.created_time);
		map.put(DbColumn.modif_time.name(),this.modif_time);
	}
    
	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
	}
	//

	
    public Integer getMember_id() {
		return member_id;
	}
	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
 	public Long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Long created_time) {
		this.created_time = created_time;
	}

	public Long getModif_time() {
		return modif_time;
	}

	public void setModif_time(Long modif_time) {
		this.modif_time = modif_time;
	}
}
