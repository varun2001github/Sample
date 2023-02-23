package com.varun.Model;

import java.math.BigInteger;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="messages")
public class MessagesModel {

	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	@Column
	private Integer senderid=null;

	@Column
	private Integer recieverid=null;
	
	@Column
	private Integer group_id=null;
	
	@Column
	private String text=null;
	
	@Column
	private BigInteger chattime=null;

	public Integer getSenderid(){
		return senderid;
	}
    
	public void setSenderid(Integer senderid) {
		this.senderid = senderid;
	}
	
	public Integer getRecieverid(){
		return recieverid;
	}
	
	public void setRecieverid(Integer recieverid){
		this.recieverid = recieverid;
	}
	
	public Integer getGroup_id() {
		return group_id;
	}
	
	public void setGroup_id(Integer group_id){
		this.group_id = group_id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
    public BigInteger getChattime() {
		return chattime;
	}

	public void setChattime(BigInteger chattime) {
		this.chattime = chattime;
	}
}
