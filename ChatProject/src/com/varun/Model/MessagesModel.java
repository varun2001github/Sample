package com.varun.Model;

import java.util.HashMap;
import java.util.logging.Logger;

public class MessagesModel extends DataObject{

    private static final Logger logger=Logger.getLogger(MessagesModel.class.getName());
	    
	private Integer chat_id=null;

	private Integer senderid=null;

	private Integer recieverid=null;
		
	private String text=null;
	
	private Long chattime=null;

	public static enum DbColumn{
		chat_id,
		senderid,
		recieverid,
		text,
		chattime,
    }
//	///// constructor
//	public MessagesModel(DataObject ob){
//		super(ob)
//		this.map=ob.getDataMap();
//		setMapInVariables();
//	}
	public MessagesModel(HashMap<String,Object> map){
		super(map);
		this.map=map;
		initialize(map);
	}
	
	public MessagesModel init(){
		initialize(super.map);
        return this;
	}
	
	public MessagesModel(){
		super();
	}
	
	private void initialize(HashMap<String,Object> map){
		this.chat_id=(Integer)map.get(DbColumn.chat_id.name());
		this.senderid=(Integer)map.get(DbColumn.senderid.name());
		this.recieverid=(Integer)map.get(DbColumn.recieverid.name());
		this.text=(String)map.get(DbColumn.text.name());
		this.chattime=(Long)map.get(DbColumn.chattime.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","messages");
		map.put(DbColumn.chat_id.name(),this.chat_id);
		map.put(DbColumn.senderid.name(),this.senderid);
		map.put(DbColumn.recieverid.name(),this.recieverid);
		map.put(DbColumn.text.name(),this.text);
		map.put(DbColumn.chattime.name(),this.chattime);
	}
	
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
    public Long getChattime(){
		return chattime;
	}

	public void setChattime(Long chattime){
		this.chattime = chattime;
	}
	public static void main(String[] args){
		DataObject ob=new DataObject();
		MessagesModel messages=(MessagesModel)ob;
	}
}
