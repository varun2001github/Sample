package com.varun.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="group_members")
public class GroupMembersModel extends DataObject{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

    Integer member_id=null;
	
 	Integer group_id=null;
 	
 	Long created_time=null;

	Long modif_time=null;

	public static enum DbColumn{
		member_id,
		group_id,
		created_time,
		modif_time;
	}
	
	/////
	public GroupMembersModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
	}
	public GroupMembersModel(){
		super();
	}
	private void setMapInVariables(){
		this.member_id=(Integer)super.map.get(DbColumn.member_id.name());
		this.group_id=(Integer)super.map.get(DbColumn.group_id.name());
		this.created_time=(Long)super.map.get(DbColumn.created_time.name());
		this.modif_time=(Long)super.map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		super.map.put(DbColumn.member_id.name(),this.member_id);
		super.map.put(DbColumn.group_id.name(),this.group_id);
		super.map.put(DbColumn.created_time.name(),this.created_time);
		super.map.put(DbColumn.modif_time.name(),this.modif_time);
	}
	
	public DataObject getDataObject(){
		setVariablesInMap();
		return super.getDataObjectRef();
	}
	
	public void updateDataObject(DataObject obj){
		super.updateMap(obj.getDataMap());
		setMapInVariables();
	}
    
	public HashMap<String,Object> getMapFromVars(){
		setVariablesInMap();
		return super.map;
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
