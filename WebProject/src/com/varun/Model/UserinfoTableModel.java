package com.varun.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Api.PassTableApi;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailTableModel.DbColumn;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;



@Table(name="userinfo")
public class UserinfoTableModel extends DataObject{
	
	//constructor
	public UserinfoTableModel(DataObject ob){
		super(ob.getDataMap());
		setMapInVariables();
		// TODO Auto-generated constructor stub
	}
	public UserinfoTableModel(){
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserinfoTableModel(UserinfoTableModel copyObj){
		super();
		this.user_id = copyObj.getUser_id();
		this.user_name = copyObj.getUser_name();
		this.gender = copyObj.getGender();;
		this.country =copyObj.getCountry();
		this.picfile = copyObj.getPicfile();;
		this.created_time =copyObj.getCreated_time();
		this.modif_time = copyObj.getModif_time();
		this.passTableObj = copyObj.getPassTableObj();
		this.emailTableObjs = copyObj.getEmailTableObj();
		this.mobileTableObjs = copyObj.getMobileTableObj();
	}
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

    private Integer user_id=null;
	
    private String user_name=null;
	
    private String gender=null;
	
    private String country=null;

	private String picfile=null;
    
    private Long created_time=null;
    
    private Long modif_time=null;
    
    private static Map<DbColumn, Function<UserinfoTableModel,Object>> methodMap=null;

    public enum DbColumn {
    	user_id(UserinfoTableModel::getUser_id),
    	user_name(UserinfoTableModel::getUser_name),
    	gender(UserinfoTableModel::getGender),
    	country(UserinfoTableModel::getCountry),
    	picfile(UserinfoTableModel::getPicfile),
    	created_time(UserinfoTableModel::getCreated_time),
    	modif_time(UserinfoTableModel::getModif_time);
    	Function<UserinfoTableModel,Object> ob;
		private DbColumn(Function<UserinfoTableModel,Object> ob){
			this.ob=ob;
		}
		public Function<UserinfoTableModel,Object> getValFromRef(){
			return this.ob;
		}
	}
	
	/////
	
	private void setMapInVariables(){
		this.user_id=(Integer)super.map.get(DbColumn.user_id.name());
		this.user_name=(String)super.map.get(DbColumn.user_name.name());
		this.gender=(String)super.map.get(DbColumn.gender.name());
		this.country=(String)super.map.get(DbColumn.country.name());
		this.picfile=(String)super.map.get(DbColumn.picfile.name());
		this.created_time=(Long)super.map.get(DbColumn.created_time.name());
		this.modif_time=(Long)super.map.get(DbColumn.modif_time.name());
	}
	
	private void setVariablesInMap(){
		super.map.put(DbColumn.user_id.name(), this.user_id);
		super.map.put(DbColumn.user_name.name(), this.user_name);
		super.map.put(DbColumn.gender.name(), this.gender);
		super.map.put(DbColumn.country.name(), this.country);
		super.map.put(DbColumn.picfile.name(), this.picfile);
		super.map.put(DbColumn.created_time.name(), this.created_time);
		super.map.put(DbColumn.modif_time.name(), this.modif_time);
	}
	
	public DataObject getDataObject(){
		setVariablesInMap();
		return super.getDataObjectRef();
	}
	
//	public void updateDataObject(DataObject obj){
//		super.updateMap(obj.getDataMap());
//		setMapInVariables();
//	}
    
	public HashMap<String,Object> getMapFromVars(){
		setVariablesInMap();
		return super.map;
	}

    //for using in insert,update
	public Object getColumnVal(DbColumn column){
	    return methodMap.get(column).apply(this);
	}
    	
    private PasswordTableModel passTableObj=null;
    
	private List<EmailTableModel> emailTableObjs=null;
	
    private List<MobileTableModel> mobileTableObjs=null;
    
    
    //column variables getter setters
    public Integer getUser_id(){
    	logger.log(Level.INFO,"method"+this.user_id);
		return this.user_id;
	}
    
	public void setUser_id(Integer user_id){
    	logger.log(Level.INFO,"method");
		this.user_id = user_id;
	}
	
	public String getUser_name(){
    	logger.log(Level.INFO,"method"+user_name);
		return user_name;
	}
	
	public void setUser_name(String user_name){
		System.out.println(user_name);
    	logger.log(Level.INFO,"method");
		this.user_name = user_name;
	}
	
	public String getGender(){
    	logger.log(Level.INFO,"method");
		return gender;
	}
	
	public void setGender(String gender){
    	logger.log(Level.INFO,"method");
		this.gender = gender;
	}
	
	public String getCountry(){
    	logger.log(Level.INFO,"method");
		return country;
	}
	
	public void setCountry(String country){
    	logger.log(Level.INFO,"method");
		this.country = country;
	}
	
    public String getPicfile() {
		return picfile;
	}

	public void setPicfile(String picfile) {
		this.picfile = picfile;
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
		
	//Table object getter setters
	public List<EmailTableModel> getEmailTableObj(){
    	logger.log(Level.INFO,"method"+this.emailTableObjs);
		if(this.emailTableObjs==null && this.user_id!=null){
			EmailTableApi EmailApiObj=new EmailTableApi(new OrmImp());
			emailTableObjs=EmailApiObj.getEmailById(user_id);
		}
		return emailTableObjs;
	}
	
	public void setEmailTableObj(List<EmailTableModel> emailTableObjs){
    	logger.log(Level.INFO,"method");
		this.emailTableObjs = emailTableObjs;
	}
	
	public List<MobileTableModel> getMobileTableObj(){
    	logger.log(Level.INFO,"method"+this.mobileTableObjs);
		if(this.mobileTableObjs==null && this.user_id!=null){
			MobileTableApi MobileApiObj=new MobileTableApi(new OrmImp());
			mobileTableObjs=MobileApiObj.getMobileById(this.user_id);
		}
		return mobileTableObjs;
	}
	
	public void setMobileTableObj(List<MobileTableModel> mobileTableObjs){
    	logger.log(Level.INFO,"method");
		this.mobileTableObjs = mobileTableObjs;
	}
	
	public PasswordTableModel getPassTableObj(){
    	logger.log(Level.INFO,"method");
		if(this.passTableObj==null  && this.user_id!=null){
			PassTableApi PassApiObj=new PassTableApi(new OrmImp());
			this.passTableObj=PassApiObj.getPassById(user_id);
		}
		return passTableObj;
	}
	
	public void setPassTableObj(PasswordTableModel passTableObj){
    	logger.log(Level.INFO,"method");
		this.passTableObj = passTableObj;
	}
}
