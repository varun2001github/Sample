package com.varun.Model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Api.PassTableApi;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="userinfo")
public class UserinfoTableModel{
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	@Column
    private Integer user_id=null;
	
	@Column
    private String user_name=null;
	
	@Column
    private String gender=null;
	
	@Column
    private String country=null;
    	
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
	
	//Table object getter setters
	public List<EmailTableModel> getEmailTableObj(){
    	logger.log(Level.INFO,"method"+this.emailTableObjs);
		if(this.emailTableObjs==null && this.user_id!=null){
			EmailTableApi EmailApiObj=new EmailTableApi();
			emailTableObjs=EmailApiObj.getEmailById(user_id);
			EmailApiObj.closeOrmConnection();
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
			MobileTableApi MobileApiObj=new MobileTableApi();
			mobileTableObjs=MobileApiObj.getMobileById(this.user_id);
			MobileApiObj.closeOrmConnection();
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
			PassTableApi PassApiObj=new PassTableApi();
			this.passTableObj=PassApiObj.getPassById(user_id);
			PassApiObj.closeOrmConnection();
		}
		return passTableObj;
	}
	
	public void setPassTableObj(PasswordTableModel passTableObj){
    	logger.log(Level.INFO,"method");
		this.passTableObj = passTableObj;
	}
}
