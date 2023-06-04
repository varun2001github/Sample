package com.varun.Dao;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import com.ProtoModel.UserModel.Email;
import com.ProtoModel.UserModel.Mobile;
import com.ProtoModel.UserModel.Password;
import com.ProtoModel.UserModel.Session;
import com.ProtoModel.UserModel.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Api.ApiProxy;
import com.varun.Api.EmailApiImpl;
import com.varun.Api.MobileApiImpl;
import com.varun.Api.PasswordApiImpl;
import com.varun.Api.SessionApiImpl;
import com.varun.Api.UserApiImpl;
import com.varun.Api.Interface.EmailApi;
import com.varun.Api.Interface.MobileApi;
import com.varun.Api.Interface.PasswordApi;
import com.varun.Api.Interface.SessionApi;
import com.varun.Api.Interface.UserApi;
import com.varun.Model.AuditModel;
import com.varun.Orm.OrmImp;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
//import javax.transaction;

public class UserDao{
	private Argon2 argon2 = (Argon2) Argon2Factory.create(Argon2Types.ARGON2i);
	private static final Logger logger=Logger.getLogger(UserDao.class.getName());
	private AuditModel auditModel=null;
	
	public UserDao(){
	}
	
    public UserDao(HttpServletRequest request) {
    	auditModel=new AuditModel((Integer)request.getAttribute("userid"),(String)request.getAttribute("sessionid"),request.getRemoteAddr());
	}
	
	public User validate(String loginId,String password) throws ClassNotFoundException {
        logger.log(Level.INFO,"method called");
		Integer userid=null;
		OrmImp ormObj=new OrmImp(auditModel);
		
		UserApi userApiImpl=new UserApiImpl(ormObj);
		PasswordApi passApi=new PasswordApiImpl(ormObj);
		User userDataObj=User.getDefaultInstance();
		boolean isvalid=false;
		String temp="$argon2i$v=19$m=500,t=1,p=1$";
		
		try{
			//System.out.print(quer2);
			Password passObj=passApi.getPassByloginid(loginId);
			if(passObj!=null){
				String hash=temp+passObj.getPassSalt()+"$"+passObj.getPassHash();
				isvalid=argon2.verify(hash,password);
                // System.out.println("pass validity"+isvalid);
			}
			//<-argon
            //if use and pass matching,then 

			if(isvalid){	
				userid=passObj.getUserId();
				
				userDataObj=userApiImpl.getUser(userid);
				
				//password policy check- 30days
			    if(userDataObj!=null){
			    	userDataObj=userDataObj.toBuilder()
							   .setPassObject(passObj).build();
			    }
			    ormObj.close();
                return userDataObj;
			}else{
                //System.out.print("no record");
				ormObj.close();
                return null;
			}
		}catch(Exception e){
	        logger.log(Level.WARNING,"",e);
		}
		ormObj.close();
        logger.log(Level.INFO,"Insert Query ret null");
        return null;  	
}

    //Register
	public boolean registerUser(String name,String email,Long mobile,String pass){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
		EmailApi emailApiImpl=new EmailApiImpl(ormObj);
		MobileApi mobileApi=new MobileApiImpl(ormObj);
		try{
			
		   boolean emailPresent=emailApiImpl.checkEmail(email);
		   boolean mobilePresent=mobileApi.checkMobile(mobile);

		   System.out.println(emailPresent+":"+mobilePresent);
            //check if the mobile,email,user-pass match already exists
			if(!emailPresent && !mobilePresent){
				UserApi userApiImpl=new UserApiImpl(ormObj);
				PasswordApi passApi=new PasswordApiImpl(ormObj);
				
				User user=User.newBuilder().setUserName(name).build();
				//insert userName
				
				ormObj.beginTransaction();

				Integer uidGenerated=userApiImpl.addUser(user);
				System.out.println(uidGenerated);
				if(uidGenerated!=null  && uidGenerated!=0){
					//insert hashed password
				    String h=argon2.hash(1, 500, 1,pass);
				    String[] hasharr=h.split("[$]");
					passApi.addPass(uidGenerated,hasharr[hasharr.length-2],hasharr[hasharr.length-1]);
					
					//insert email
					emailApiImpl.addEmail(uidGenerated, email);
					
					//insert mobile
					mobileApi.addMobile(uidGenerated, mobile);
					
					boolean isInserted=ormObj.commit();
					System.out.println("reg dao "+uidGenerated);
					if(isInserted){
						System.out.println("Successfully added");
						ormObj.close();
						return true;				
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ormObj.close();
		return false;
	}
	
	public List<Email> getEmail(Integer userid){
		OrmImp ormObj=new OrmImp();
		EmailApi emailApiImpl=new EmailApiImpl(ormObj);
		return emailApiImpl.getEmailById(userid);
	}
	
	public List<Mobile> getMobile(Integer userid){
		OrmImp ormObj=new OrmImp();
		MobileApi mobileApi=new MobileApiImpl(ormObj);
		return mobileApi.getMobileById(userid);
	}
	
	public Session getSessionObject(String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp();
        SessionApi sessionApi=new SessionApiImpl(ormObj);
        Session sessionObject=sessionApi.getObjectBySession(sessionid);
	    ormObj.close();
	    return sessionObject;
	}
	
	
	//proxy 
	public User getUserById(Integer userId){
        logger.log(Level.INFO,"method called");
		UserApi userApi=ApiProxy.getInstance().getUserApi();
		User ud=userApi.getUser(userId);
	    return ud;
	}
	
	
	//
	
	public boolean createSession(Integer userid,String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionApi sessionApi=new SessionApiImpl(ormObj);
        boolean isValid=sessionApi.createSession(userid, sessionid);
		ormObj.close();
        return isValid;
	}
	
	public void deleteExpiredSessions(Integer uid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionApi sessionApi=new SessionApiImpl(ormObj);
        sessionApi.deleteExpiredSession(uid);
        ormObj.close();
	}
	
	public void sessionInvalidate(String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionApi sessionApi=new SessionApiImpl(ormObj);
        sessionApi.deleteSession(sessionid);
        ormObj.close();
    }
	
	public boolean updateProfile(User oldUserObject,User newUserObject) throws JsonProcessingException{
		OrmImp ormObj=new OrmImp(auditModel);
		UserApi userApiImpl=new UserApiImpl(ormObj);
		EmailApi emailApiImpl=new EmailApiImpl(ormObj);
		MobileApi mobileApi=new MobileApiImpl(ormObj);

		boolean isUpdated=false;
		if(newUserObject.getUserName()!="" || 
		   newUserObject.getCountry() !="" || 
				newUserObject.getGender()!=""){
			           System.out.println("ud up"+newUserObject.getUserName());
			           isUpdated=userApiImpl.updateUser(oldUserObject,newUserObject);
		}
        logger.log(Level.INFO,"p Dao after");
		for(int i=0;i<oldUserObject.getEmailObjectCount();i++){
			    //if old email not equal to new
				if(!oldUserObject.getEmailObject(i).getEmailid().equals(newUserObject.getEmailObject(i).getEmailid())){
			            isUpdated=emailApiImpl.updateEmail(oldUserObject.getEmailObject(i),newUserObject.getEmailObject(i));
				}
		}
		for(int i=0;i<oldUserObject.getMobileObjectCount();i++){
		        //if old mobile not equal to new
				if(oldUserObject.getMobileObject(i).getMobileno()!=newUserObject.getMobileObject(i).getMobileno()) {
		            logger.log(Level.INFO,"p Dao mobile update before");
		            isUpdated=mobileApi.updateMobile(oldUserObject.getMobileObject(i),newUserObject.getMobileObject(i));
		            logger.log(Level.INFO,"p Dao mobile update after");
				}
		}
		ormObj.close();
		return isUpdated;
	}
	
	public boolean addNewPass(Integer id,String oldpass,String newpass) throws JsonProcessingException{
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
		PasswordApi passApi=new PasswordApiImpl(ormObj);
		Password oldPassobj=null;

		String temp="$argon2i$v=19$m=500,t=1auditModel,p=1$";
		boolean isdub=false,iscurpass=false;
		String hash="";
		
		//check if new pass already exist
		List<Password> passObjList=passApi.getAllPassById(id);
		for(Password u:passObjList){
			Password ad=null;;
			hash=temp+u.getPassSalt()+"$"+u.getPassHash();
			System.out.println(" new pass check "+argon2.verify(hash, newpass)+" ");
			isdub=argon2.verify(hash,newpass);
			if(isdub==true){
                break;
			}
		}
		//check if old pass is current pass
		if(isdub==false){
			for(Password passwordObj:passObjList){
				//current pass in db
				if(passwordObj.getPassStatus()==1){
					hash=temp+passwordObj.getPassSalt()+"$"+passwordObj.getPassHash();
					iscurpass=argon2.verify(hash,oldpass);
					if(iscurpass==true){
						oldPassobj=passwordObj;
		                break;
					}
				}
			}
			
			//if both old and new pass entered is valid ,insert
			if(iscurpass==true){
			    String h=argon2.hash(1, 500, 1,newpass);

				Password newPassobj=Password.newBuilder().setPassStatus(0).build();
				
				passApi.updatePassById(oldPassobj,newPassobj,id);
				
				String[] hasharr=h.split("[$]");
				boolean isInserted=passApi.addPass(id, hasharr[hasharr.length-2], hasharr[hasharr.length-1]);
				ormObj.close();
				return isInserted;
			}
		}
		ormObj.close();
        return false;
    }
	public static void main(String args[]) throws ClassNotFoundException{
		UserDao obj=new UserDao();
		System.out.println(obj.validate("varunsashi@gmail.com", "Varun@123").getEmailObject(0).getEmailid());
	}
}
