//$Id$
package com.varun.Model;

import java.util.HashMap;
import java.util.logging.Logger;

public class GroupMessagesModel extends DataObject{
	
    private static final Logger logger=Logger.getLogger(GroupMessagesModel.class.getName());
	    
	private Integer groupChatid=null;

	private Integer senderid=null;

	private Integer groupid=null;
		
	private String text=null;
	
	private Long chattime=null;

	public static enum DbColumn{
		groupchat_id,
		senderid,
		groupid,
		text,
		chattime,
    }
	///// constructor

	public GroupMessagesModel(HashMap<String,Object> map){
		this.map=map;
		initialize(map);
	}
	
	public GroupMessagesModel(){
	}
	
	public GroupMessagesModel init(){
		initialize(super.getDataMap());
        return this;
	}
	
	private void initialize(HashMap<String,Object> map){
		this.groupChatid=(Integer)map.get(DbColumn.groupchat_id.name());
		this.senderid=(Integer)map.get(DbColumn.senderid.name());
		this.groupid=(Integer)map.get(DbColumn.groupid.name());
		this.text=(String)map.get(DbColumn.text.name());
		this.chattime=(Long)map.get(DbColumn.chattime.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","group_messages");
		map.put(DbColumn.groupchat_id.name(),this.groupChatid);
		map.put(DbColumn.senderid.name(),this.senderid);
		map.put(DbColumn.groupid.name(),this.groupid);
		map.put(DbColumn.text.name(),this.text);
		map.put(DbColumn.chattime.name(),this.chattime);
	}
	
	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
	}
	//

	public Integer getGroupchat_id() {
		return groupChatid;
	}

	public void setGroupchat_id(Integer chat_id){
		this.groupChatid = chat_id;
	}
	
	public Integer getSenderid(){
		return senderid;
	}
    
	public void setSenderid(Integer senderid){
		this.senderid = senderid;
	}
	
	public Integer getGroupid(){
		return groupid;
	}
	
	public void setGroupid(Integer groupid){
		this.groupid = groupid;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
    public Long getChattime() {
		return chattime;
	}

	public void setChattime(Long chattime){
		this.chattime = chattime;
	}
}
