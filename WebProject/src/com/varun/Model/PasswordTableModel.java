package com.varun.Model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="user_pass")
public class PasswordTableModel {
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	
	@Column
	private Integer user_id=null;
	
	@Column
    private String pass_salt=null;
	
	@Column
    private String pass_hash=null;
	
	@Column
    private Integer pass_status=null;
	
	@Column
    private BigInteger created_time=null;
    
    public PasswordTableModel(){
    }
    public PasswordTableModel(Integer user_id,String pass_salt,String pass_hash,Integer pass_status){
    	this.user_id=user_id;
    	this.pass_salt=pass_salt;
    	this.pass_hash=pass_hash;
    	this.pass_status=pass_status;
    }
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
	public BigInteger getCreated_time(){
		return created_time;
	}
	public void setCreated_time(BigInteger created_time){
		this.created_time = created_time;
	}
}
