//$Id$
package com.varun.Model;

import java.util.HashMap;

import com.varun.Model.DataObject;
import com.varun.Model.EmailModel.DbColumn;

public class AuditModel{

	private Integer user_id=null;
	private String table_name=null;
	private String action_type=null;
	private String old_values=null;
	private String new_values=null;
	private String session_id=null;
	private String ip_address=null;
	private Long audit_time=null;
    
    private HashMap<String,Object> map=new HashMap<String,Object>();

    public static enum DbColumn{
    	user_id,
		table_name,
		action_type,
		old_values,
		new_values,
		session_id,
		ip_address,
		audit_time;
    }
  
	//constructor
	public AuditModel(DataObject ob){
		map=ob.getDataMap();
		setMapInVariables();
	}
	
	public AuditModel(AuditModel copyAudit){
		this.user_id=copyAudit.user_id;
		this.session_id=copyAudit.session_id;
		this.ip_address=copyAudit.ip_address;
	}
	
	public AuditModel(){
		
	}
	
	private void setMapInVariables(){
		this.user_id =(Integer)map.get(DbColumn.user_id.name());
		this.table_name =(String)map.get(DbColumn.table_name.name());
		this.action_type=(String)map.get(DbColumn.action_type.name());
		this.old_values=(String)map.get(DbColumn.new_values.name());
		this.new_values=(String)map.get(DbColumn.old_values.name());
		this.session_id =(String)map.get(DbColumn.session_id.name());
		this.ip_address =(String)map.get(DbColumn.ip_address.name());
		this.audit_time =(Long)map.get(DbColumn.audit_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","audit_table");
		map.put(DbColumn.user_id.name(),this.user_id);
		map.put(DbColumn.table_name.name(),this.table_name);
		map.put(DbColumn.action_type.name(),this.action_type);
		map.put(DbColumn.old_values.name(),this.old_values);
		map.put(DbColumn.new_values.name(),this.new_values);
		map.put(DbColumn.session_id.name(),this.session_id);
		map.put(DbColumn.ip_address.name(),this.ip_address);
		map.put(DbColumn.audit_time.name(),this.audit_time);
	}
//	public HashMap<String,Object> getMapFromVars(){
//		setVariablesInMap();
//		return this.map;
//	}
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
    }
	
    public void setAudits(String tableName,String action_type,String old_value,String new_value,Long audit_time){
	   this.table_name=tableName;
	   this.action_type=action_type;
	   this.old_values=old_value;
	   this.new_values=new_value;
	   this.audit_time=audit_time;
    }
    
    public AuditModel(Integer user_id,String session_id,String ip_address){
       this.user_id=user_id;
       this.session_id=session_id;
	   this.ip_address=ip_address;
    }

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getOld_values() {
		return old_values;
	}

	public void setOld_value(String old_values) {
		this.old_values = old_values;
	}

	public String getNew_value() {
		return new_values;
	}

	public void setNew_value(String new_values) {
		this.new_values = new_values;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Long getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Long audit_time) {
		this.audit_time = audit_time;
	}
}
