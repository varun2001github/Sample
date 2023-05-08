package com.varun.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserModel.DbColumn;
import com.varun.Orm.CommonMethod;
import com.varun.Orm.Table;

@Table(name="user_pass")
public class PasswordModel implements CommonMethod{
	
    private static final Logger logger=Logger.getLogger(PasswordModel.class.getName());
	
	private HashMap<String,Object> map=new HashMap<String,Object>();
	
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
	public PasswordModel(DataObject ob){
		this.map=ob.getDataMap();
		setMapInVariables();
	}

	public PasswordModel(){

	}
	
	public PasswordModel(Integer uid,String pass_salt,String pass_hash){
    	this.user_id=uid;
    	this.pass_salt=pass_salt;
    	this.pass_hash=pass_hash;
    	this.created_time=System.currentTimeMillis();
    }
	
	private void setMapInVariables(){
		this.user_id=(Integer)map.get(DbColumn.user_id.name());
		this.pass_salt=(String)map.get(DbColumn.pass_salt.name());
		this.pass_hash=(String)map.get(DbColumn.pass_hash.name());
		this.pass_status=(Integer)map.get(DbColumn.pass_status.name());
		this.created_time=(Long)map.get(DbColumn.created_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","user_pass");
		map.put(DbColumn.user_id.name(),this.user_id);
		map.put(DbColumn.pass_salt.name(),this.pass_salt);
		map.put(DbColumn.pass_hash.name(),this.pass_hash);
		map.put(DbColumn.pass_status.name(),this.pass_status);
		map.put(DbColumn.created_time.name(),this.created_time);
	}

	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
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
	public String getPass_hash(){
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
