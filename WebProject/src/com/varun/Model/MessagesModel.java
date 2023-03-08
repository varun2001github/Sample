package com.varun.Model;

import java.util.HashMap;
import java.util.function.Function;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.MobileTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="messages")
public class MessagesModel extends DataObject{

	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
    
	private Integer chat_id=null;

	private Integer senderid=null;

	private Integer recieverid=null;
	
	private Integer group_id=null;
	
	private String text=null;
	
	private Long chattime=null;

	public static enum DbColumn{
		chat_id,
		senderid,
		recieverid,
		text,
		chattime,
		group_id;
    }
	/////
	public MessagesModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
		// TODO Auto-generated constructor stub
	}
	public MessagesModel(){
		super();
		// TODO Auto-generated constructor stub
	}
	private void setMapInVariables(){
		HashMap<String,Object> map=super.getDataMap();
		this.chat_id=(Integer)map.get(DbColumn.chat_id.name());
		this.senderid=(Integer)map.get(DbColumn.senderid.name());
		this.recieverid=(Integer)map.get(DbColumn.recieverid.name());
		this.group_id=(Integer)map.get(DbColumn.group_id.name());
		this.text=(String)map.get(DbColumn.text.name());
		this.chattime=(Long)map.get(DbColumn.chattime.name());
	}
	
	private void setVariablesInMap(){
		HashMap<String,Object> map=new HashMap<String,Object>();
		super.map.put(DbColumn.chat_id.name(),this.chat_id);
		super.map.put(DbColumn.senderid.name(),this.senderid);
		super.map.put(DbColumn.recieverid.name(),this.recieverid);
		super.map.put(DbColumn.group_id.name(),this.group_id);
		super.map.put(DbColumn.text.name(),this.text);
		super.map.put(DbColumn.chattime.name(),this.chattime);
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

	public Integer getChat_id() {
		return chat_id;
	}

	public void setChat_id(Integer chat_id) {
		this.chat_id = chat_id;
	}
	
	public Integer getSenderid(){
		return senderid;
	}
    
	public void setSenderid(Integer senderid){
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
    public Long getChattime() {
		return chattime;
	}

	public void setChattime(Long chattime) {
		this.chattime = chattime;
	}
}
