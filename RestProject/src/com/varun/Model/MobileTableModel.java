package com.varun.Model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.PasswordTableModel.DbColumn;
import com.varun.Orm.Table;

@Table(name="mobile")
public class MobileTableModel{
	
	private static final Logger logger=LoggerUtil.getLogger(MobileTableModel.class);

	private HashMap<String,Object> map=new HashMap<String,Object>();
	
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
		this.map=ob.getDataMap();
		setMapInVariables();
	}
	public MobileTableModel(){

	}
	/////
	
	private void setMapInVariables(){
		this.user_id=(Integer)map.get(DbColumn.user_id.name());
		this.mobileno=(Long)map.get(DbColumn.mobileno.name());
		this.is_primary=(Integer)map.get(DbColumn.is_primary.name());
		this.is_verified=(Integer)map.get(DbColumn.is_verified.name());
		this.created_time=(Long)map.get(DbColumn.created_time.name());
		this.modif_time=(Long)map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		map.put("Table","mobile");
		map.put(DbColumn.user_id.name(),this.user_id);
		map.put(DbColumn.mobileno.name(),this.mobileno);
		map.put(DbColumn.is_primary.name(),this.is_primary);
		map.put(DbColumn.is_verified.name(),this.is_verified);
		map.put(DbColumn.created_time.name(),this.created_time);
		map.put(DbColumn.modif_time.name(),this.modif_time);

	}
    
	public DataObject getDataObject() {
		setVariablesInMap();
		return new DataObject(map);
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
