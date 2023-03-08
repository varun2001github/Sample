package com.varun.Model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.PasswordTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="mobile")
public class MobileTableModel extends DataObject{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	
	private Integer user_id=null;
	
    private Long mobileno=null;
	
	private Integer is_primary=null;
	
	private Integer is_verified=1;

	private Long created_time=null;

	private Long modif_time=null;

	
	public static enum DbColumn{
		user_id,
		mobileno,
		is_primary,
		is_verified,
		created_time,
		modif_time;
    }
	
	//constructor
	public MobileTableModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
		// TODO Auto-generated constructor stub
	}
	public MobileTableModel(){
		super();
		// TODO Auto-generated constructor stub
	}
	/////
	
	private void setMapInVariables(){
		this.user_id=(Integer)super.map.get(DbColumn.user_id.name());
		this.mobileno=(Long)super.map.get(DbColumn.mobileno.name());
		this.is_primary=(Integer)super.map.get(DbColumn.is_primary.name());
		this.is_verified=(Integer)super.map.get(DbColumn.is_verified.name());
		this.created_time=(Long)super.map.get(DbColumn.created_time.name());
		this.modif_time=(Long)super.map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		super.map.put(DbColumn.user_id.name(),this.user_id);
		super.map.put(DbColumn.mobileno.name(),this.mobileno);
		super.map.put(DbColumn.is_primary.name(),this.is_primary);
		super.map.put(DbColumn.is_verified.name(),this.is_verified);
		super.map.put(DbColumn.created_time.name(),this.created_time);
		super.map.put(DbColumn.modif_time.name(),this.modif_time);

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

	public Integer getUser_id(){
		return user_id;
	}
	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}
	
	public Long getMobileno(){
		return mobileno;
	}
	public void setMobileno(Long mobileno){
		this.mobileno = mobileno;
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
