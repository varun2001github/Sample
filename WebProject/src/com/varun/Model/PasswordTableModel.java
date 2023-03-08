package com.varun.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="user_pass")
public class PasswordTableModel extends DataObject{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	
	private Integer user_id=null;
	
    private String pass_salt=null;
	
    private String pass_hash=null;
	
    private Integer pass_status=null;
	
    private Long created_time=null;
    
	public static enum DbColumn{
		user_id,
		pass_salt,
		pass_hash,
		pass_status,
		created_time;
	}    
	
	//constructor
	public PasswordTableModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
		// TODO Auto-generated constructor stub
	}

	public PasswordTableModel(){
		super();
		// TODO Auto-generated constructor stub
	}
	public PasswordTableModel(Integer uid,String pass_salt,String pass_hash){
    	this.user_id=uid;
    	this.pass_salt=pass_salt;
    	this.pass_hash=pass_hash;
    	this.created_time=System.currentTimeMillis();
    }
	
	private void setMapInVariables(){
		this.user_id=(Integer)super.map.get(DbColumn.user_id.name());
		this.pass_salt=(String)super.map.get(DbColumn.pass_salt.name());
		this.pass_hash=(String)super.map.get(DbColumn.pass_hash.name());
		this.pass_status=(Integer)super.map.get(DbColumn.pass_status.name());
		this.created_time=(Long)super.map.get(DbColumn.created_time.name());
	}
	
	private void setVariablesInMap(){
		super.map.put(DbColumn.user_id.name(),this.user_id);
		super.map.put(DbColumn.pass_salt.name(),this.pass_salt);
		super.map.put(DbColumn.pass_hash.name(),this.pass_hash);
		super.map.put(DbColumn.pass_status.name(),this.pass_status);
		super.map.put(DbColumn.created_time.name(),this.created_time);
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
	
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getPass_salt() {
		return pass_salt;
	}
	public void setPass_salt(String pass_salt) {
		this.pass_salt = pass_salt;
	}
	public String getPass_hash() {
		return pass_hash;
	}
	public void setPass_hash(String pass_hash){
		this.pass_hash = pass_hash;
	}
	public Integer getPass_status(){
		return pass_status;
	}
	public void setPass_status(Integer pass_status){
		this.pass_status = pass_status;
	}
	public Long getCreated_time(){
		return created_time;
	}
	public void setCreated_time(Long created_time){
		this.created_time = created_time;
	}
}
