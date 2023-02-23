package com.varun.Model;

import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="email")
public class EmailTableModel{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	@Column
	private Integer user_id=null;
	
	@Column
	private String emailid=null;
	
	@Column
	private boolean is_primary;
	
	@Column
	private boolean is_verified;
	
    public String getEmailid(){
		return emailid;
	}
	public void setEmailid(String emailid){
		this.emailid = emailid;
	}
    public Integer getUser_id(){
		return user_id;
	}
	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}
	public boolean isIs_primary(){
		return is_primary;
	}
	public void setIs_primary(boolean is_primary){
		this.is_primary = is_primary;
	}
	public boolean isIs_verified(){
		return is_verified;
	}
	public void setIs_verified(boolean is_verified){
		this.is_verified = is_verified;
	}
}
