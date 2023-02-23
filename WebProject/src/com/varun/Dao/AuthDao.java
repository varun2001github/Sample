package com.varun.Dao;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Api.PassTableApi;
import com.varun.Api.SessionTableApi;
import com.varun.Api.UserTableApi;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.PasswordTableModel;
import com.varun.Model.UserinfoTableModel;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class AuthDao{
	private Argon2 argon2 = (Argon2) Argon2Factory.create(Argon2Types.ARGON2i);
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	public UserinfoTableModel validate(String loginId,String password) throws ClassNotFoundException{
        logger.log(Level.INFO,"method called");
		Integer userid=null;
		UserTableApi userApi=new UserTableApi();
		PassTableApi passApi=new PassTableApi();
		EmailTableApi emailApi=new EmailTableApi();
		MobileTableApi mobileApi=new MobileTableApi();
		UserinfoTableModel userDataObj=new UserinfoTableModel();
		boolean isvalid=false;
		String temp="$argon2i$v=19$m=500,t=1,p=1$";
		
		try{
			boolean ismobile=loginId.matches("[0-9]+");
			//->argon 	

			if(ismobile){
				userid=mobileApi.getIdByMobile(loginId);
			}else{
				userid=emailApi.getIdByEmail(loginId);
			}
//			System.out.print(quer2);
            PasswordTableModel passObj= passApi.getPassById(userid);
			
			if(passObj!=null){
				String hash=temp+passObj.getPass_salt()+"$"+passObj.getPass_hash();
				isvalid=argon2.verify(hash,password);
				System.out.println("pass validity"+isvalid);
			}
			//<-argon
            //if use and pass matching,then 

			if(isvalid){	
			    UserinfoTableModel userDetail=userApi.getUserById(userid);
				//password policy check- 30days
			    if(userDetail!=null){
			    	userDataObj.setUser_id(userDetail.getUser_id());
			    	userDataObj.setUser_name(userDetail.getUser_name());
			    	userDataObj.setPassTableObj(passObj);
			    	userDataObj.setMobileTableObj(mobileApi.getMobileById(userid));
			    	userDataObj.setEmailTableObj(emailApi.getEmailById(userid));
			    }
				passApi.closeOrmConnection();
				emailApi.closeOrmConnection();
				mobileApi.closeOrmConnection();
			    userApi.closeOrmConnection();
                return userDataObj;
			}else{
				System.out.print("no record");
				passApi.closeOrmConnection();
				emailApi.closeOrmConnection();
				mobileApi.closeOrmConnection();
			    userApi.closeOrmConnection();
                return null;
			}
		}catch(Exception e){
	        logger.log(Level.WARNING,"",e);
		}
		passApi.closeOrmConnection();
		emailApi.closeOrmConnection();
		mobileApi.closeOrmConnection();
	    userApi.closeOrmConnection();
		return null;
	}
	
    //Register
	public boolean registerUser(String name,String email,Integer mobile,String pass){
        logger.log(Level.INFO,"method called");
		EmailTableApi emailApi=new EmailTableApi();
		MobileTableApi mobileApi=new MobileTableApi();
		try{
		   boolean emailPresent=emailApi.checkEmail(email);
		   boolean mobilePresent=mobileApi.checkMobile(mobile);

		
            //check if the mobile,email,user-pass match already exists
			if(!emailPresent && !mobilePresent){
				UserTableApi userApi=new UserTableApi();
				PassTableApi passApi=new PassTableApi();
				//insert userName
				Integer uidGenerated=userApi.addUser(name);
				
				//insert hashed password
			    String h=argon2.hash(1, 500, 1,pass);
			    String[] hasharr=h.split("[$]");
				boolean isPassInserted=passApi.addPass(uidGenerated, hasharr[hasharr.length-2], hasharr[hasharr.length-1]);
				passApi.addPass(uidGenerated, pass, pass);
				
				//insert email
				boolean isEmailInserted=emailApi.addEmail(uidGenerated, email);
				
				//insert mobile
				boolean isMobileInserted=mobileApi.addMobile(uidGenerated, mobile);
				
				if(uidGenerated!=null && isPassInserted && isEmailInserted && isMobileInserted){
					System.out.println("Successfully added");
					passApi.closeOrmConnection();
					emailApi.closeOrmConnection();
					mobileApi.closeOrmConnection();
				    userApi.closeOrmConnection();
					return true;				
				}
				
			}
			emailApi.closeOrmConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		emailApi.closeOrmConnection();
		mobileApi.closeOrmConnection();
		return false;
	}
	
	public UserinfoTableModel getUserBySession(String sessionid){
        logger.log(Level.INFO,"method called");
		UserTableApi userApi=new UserTableApi();
        SessionTableApi sessionApi=new SessionTableApi();
        UserinfoTableModel ud;
        Integer userid=sessionApi.getIdBySession(sessionid);
	    ud=userApi.getUserById(userid);
	    userApi.closeOrmConnection();
	    sessionApi.closeOrmConnection();
	    return ud;
	}
	
	public boolean checkSession(Integer userid,String sessionid){
        logger.log(Level.INFO,"method called");
        SessionTableApi sessionApi=new SessionTableApi();
        boolean isValid=sessionApi.checkIdWithSession(userid,sessionid);
	    sessionApi.closeOrmConnection();
        return isValid;
	}
	
	public void deleteExpiredSessions(Integer uid){
        logger.log(Level.INFO,"method called");
        SessionTableApi sessionApi=new SessionTableApi();
        sessionApi.deleteExpiredSession(uid);
	}
	
	public void sessionInvalidate(String sessionid){
        logger.log(Level.INFO,"method called");
        SessionTableApi sessionApi=new SessionTableApi();
        sessionApi.deleteSession(sessionid);
    }
	
	public boolean addNewPass(Integer id,String oldpass,String newpass){
        logger.log(Level.INFO,"method called");
		PassTableApi passApi=new PassTableApi();
		String temp="$argon2i$v=19$m=500,t=1,p=1$";
		boolean isdub=false,iscurpass=false;
		String hash="";
		
		//check if new pass already exist
		List<PasswordTableModel> passObjList=passApi.getAllPassById(id);
		for(PasswordTableModel u:passObjList){
			hash=temp+u.getPass_salt()+"$"+u.getPass_hash();
			System.out.println(" new pass check "+argon2.verify(hash, newpass)+" ");
			isdub=argon2.verify(hash,newpass);
			if(isdub==true){
                break;
			}
		}
		//check if old pass is current pass
		if(isdub==false){
			for(PasswordTableModel u:passObjList){
				//current pass in db
				if(u.getPass_status()==1){
					hash=temp+u.getPass_salt()+"$"+u.getPass_hash();
					iscurpass=argon2.verify(hash,oldpass);
					if(iscurpass==true){
		                break;
					}
				}
				
			}
			
			//if both old and new pass entered is valid 
			if(iscurpass==true){
			    String h=argon2.hash(1, 500, 1,newpass);
				PasswordTableModel obj=new PasswordTableModel();
				obj.setPass_status(0);
				passApi.updatePassById(obj,id);
				String[] hasharr=h.split("[$]");
				boolean isInserted=passApi.addPass(id, hasharr[hasharr.length-2], hasharr[hasharr.length-1]);
				return isInserted;
			}
		}
        return false;
    }
}
