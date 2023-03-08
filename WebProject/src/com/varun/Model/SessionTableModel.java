package com.varun.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="session_info")
public class SessionTableModel extends DataObject{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

    private Integer user_id=null;
	
	private String session_id=null;
	
    private Long Expiry=null;
    
    enum DbColumn{
    	user_id,
    	session_id,
    	Expiry;
	}

	//constructor
	public SessionTableModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
	}
	
	public SessionTableModel(){
		super();
	}
    	
	//private methods
	private void setMapInVariables(){
		HashMap<String,Object> map=super.getDataMap();
		this.user_id=(Integer)map.get(DbColumn.user_id.name());
		this.session_id=(String)map.get(DbColumn.session_id.name());
		this.Expiry=(Long)map.get(DbColumn.Expiry.name());
	}
	
	private void setVariablesInMap(){
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put(DbColumn.user_id.name(),this.user_id);
		map.put(DbColumn.session_id.name(),this.session_id);
		map.put(DbColumn.Expiry.name(),this.Expiry);
		super.updateMap(map);
	}
	
	//get data from super class 
	
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
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Long getExpiry() {
		return Expiry;
	}
	public void setExpiry(Long expiry) {
		Expiry = expiry;
	}
}
//enum DbColumn{
//	user_id(SessionTableModel::getUser_id),
//	session_id(SessionTableModel::getSession_id),
//	Expiry(SessionTableModel::getExpiry);
//	
//	Function<SessionTableModel,Object> ob;
//	private DbColumn(Function<SessionTableModel,Object> ob){
//		this.ob=ob;
//	}
//	public Function<SessionTableModel,Object> getValFromRef(){
//		return this.ob;
//	}
//}
//private void setVariablesInMap(){
//	if(super.map!=null){
//		for(DbColumn c:DbColumn.values()){
//			//if getter methods result not null
//			if(c.getValFromRef().apply(this)!=null){
//				//put in parents hashmap
//				super.map.put(c.name(),c.getValFromRef().apply(this));
//			}
//		}	
//	}
//}