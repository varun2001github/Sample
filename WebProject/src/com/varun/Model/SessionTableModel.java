package com.varun.Model;

import java.math.BigInteger;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="session_info")
public class SessionTableModel {
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	@Column
    private Integer user_id=null;
	
	@Column
	private String session_id=null;
	
	@Column
    private BigInteger Expiry=null;
    
    public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public BigInteger getExpiry() {
		return Expiry;
	}
	public void setExpiry(BigInteger expiry) {
		Expiry = expiry;
	}

}
