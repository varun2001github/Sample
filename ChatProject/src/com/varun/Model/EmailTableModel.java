package com.varun.Model;

import java.util.HashMap;
import java.util.function.Function;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Filter.SessionAuthFilter;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.GroupInfoModel.DbColumn;
import com.varun.Orm.CommonMethod;
import com.varun.Orm.Table;

@Table(name="email")
public class EmailTableModel implements CommonMethod{
	
	private static final Logger logger=Logger.getLogger(EmailTableModel.class.getName());

	private HashMap<String,Object> map=new HashMap<String,Object>();
	
	private Integer user_id=null;
	
	private String emailid=null;
	
	private Integer is_primary=null;
	
	private Integer is_verified=null;
	
    private Long created_time=null;

	private Long modif_time=null;
	
	public static enum DbColumn{
		user_id,
		emailid,
		is_primary,
		is_verified,
		created_time,
		modif_time;
    }
  
	//constructor
	public EmailTableModel(DataObject ob){
		map=ob.getDataMap();
		setMapInVariables();
	}
	public EmailTableModel(){
		
	}
	
    public EmailTableModel(Integer user_id,String emailid,Integer is_primary,Integer is_verified){
    	this.user_id=user_id;
		this.emailid=emailid;
		this.is_primary=is_primary;
		this.is_verified=is_verified;
	}
	private void setMapInVariables(){
		this.user_id=(Integer)map.get(DbColumn.user_id.name());
		this.emailid=(String)map.get(DbColumn.emailid.name());
		this.is_primary=(Integer)map.get(DbColumn.is_primary.name());
		this.is_verified=(Integer)map.get(DbColumn.is_verified.name());
		this.created_time=(Long)map.get(DbColumn.created_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","email");
		map.put(DbColumn.user_id.name(),this.user_id);
		map.put(DbColumn.emailid.name(),this.emailid);
		map.put(DbColumn.is_primary.name(),this.is_primary);
		map.put(DbColumn.is_verified.name(),this.is_verified);
		map.put(DbColumn.created_time.name(),this.created_time);
		map.put(DbColumn.modif_time.name(),this.modif_time);
	}
//	
//	public HashMap<String,Object> getMapFromVars(){
//		setVariablesInMap();
//		return this.map;
//	}
//	
	public DataObject getDataObject(){
		setVariablesInMap();
		return new DataObject(map);
	}
	//
	
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
	public Integer getIs_primary(){
		return is_primary;
	}
	public void setIs_primary(Integer is_primary){
		this.is_primary = is_primary;
	}
	public Integer getIs_verified(){
		return is_verified;
	}
	public void setIs_verified(Integer is_verified){
		this.is_verified = is_verified;
	}
    
    public Long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Long created_time) {
		this.created_time = created_time;
	}

	public Long getModif_time() {
		return modif_time;
	}

	public void setModif_time(Long modif_time) {
		this.modif_time = modif_time;
	}
}
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
