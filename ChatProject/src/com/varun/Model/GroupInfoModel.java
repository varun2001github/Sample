package com.varun.Model;

import java.util.HashMap;
import java.util.logging.Logger;

public class GroupInfoModel extends DataObject{
	
	private static final Logger logger=Logger.getLogger(GroupInfoModel.class.getName());
	    
    Integer group_id=null;
	
	String group_name=null;
	
    Integer admin_id=null;
    
	Long created_time=null;
    
    Long modif_time=null;
    
	public static enum DbColumn{
		group_id,
		group_name,
		admin_id,
		created_time,
		modif_time;
    }
	
/////

	public GroupInfoModel(HashMap<String,Object> map){
		this.map=map;
		initialize(map);
	}
	
	public GroupInfoModel(){
	}
	
	public GroupInfoModel init(){
		initialize(super.getDataMap());
        return this;
	}
	public void initialize(HashMap<String,Object> map){
		this.group_id=(Integer)map.get(DbColumn.group_id.name());
		this.group_name=(String)map.get(DbColumn.group_name.name());
		this.admin_id=(Integer)map.get(DbColumn.admin_id.name());
		this.created_time=(Long)map.get(DbColumn.created_time.name());
		this.modif_time=(Long)map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","group_info");
		map.put(DbColumn.group_id.name(),this.group_id);
		map.put(DbColumn.group_name.name(),this.group_name);
		map.put(DbColumn.admin_id.name(),this.admin_id);
	    map.put(DbColumn.created_time.name(),this.created_time);
		map.put(DbColumn.modif_time.name(),this.modif_time);
	}
    
//	public HashMap<String,Object> getMapFromVars(){
//		setVariablesInMap();
//		return map;
//	}
	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
	}

	public Integer getGroup_id(){
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name(){
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Integer getAdmin_id(){
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
    public Long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Long created_time) {
		this.created_time = created_time;
	}

	public Long getModif_time(){
		return modif_time;
	}

	public void setModif_time(Long modif_time) {
		this.modif_time = modif_time;
	}
	   public static void main(String args[]){
		   DataObject ob=new DataObject();
		   GroupInfoModel g=(GroupInfoModel)ob;
		   System.out.println(g instanceof DataObject);
		   System.out.println(ob instanceof GroupInfoModel);
	   }
}
