package com.varun.Dao;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import com.ProtoModel.UserModel.EmailModel;
import com.ProtoModel.UserModel.MobileModel;
import com.ProtoModel.UserModel.PasswordModel;
import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.UserinfoModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Api.PassTableApi;
import com.varun.Api.SessionTableApi;
import com.varun.Api.UserTableApi;
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
	
	public UserinfoModel validate(String loginId,String password) throws ClassNotFoundException {
        logger.log(Level.INFO,"method called");
		Integer userid=null;
		OrmImp ormObj=new OrmImp(auditModel);
		
		UserTableApi userApi=new UserTableApi(ormObj);
		PassTableApi passApi=new PassTableApi(ormObj);
		UserinfoModel userDataObj=UserinfoModel.getDefaultInstance();
		boolean isvalid=false;
		String temp="$argon2i$v=19$m=500,t=1,p=1$";
		
		try{
			//System.out.print(quer2);
			PasswordModel passObj=passApi.getPassByloginid(loginId);
			if(passObj!=null){
				String hash=temp+passObj.getPassSalt()+"$"+passObj.getPassHash();
				isvalid=argon2.verify(hash,password);
//				System.out.println("pass validity"+isvalid);
			}
			//<-argon
            //if use and pass matching,then 

			if(isvalid){	
				userid=passObj.getUserId();
				userDataObj=userApi.getUser(userid);
				
				//password policy check- 30days
			    if(userDataObj!=null){
			    	userDataObj=userDataObj.toBuilder()
							   .setPassObj(passObj).build();
			    }
			    ormObj.close();
                return userDataObj;
			}else{
//				System.out.print("no record");
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
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		MobileTableApi mobileApi=new MobileTableApi(ormObj);
		try{
		   boolean emailPresent=emailApi.checkEmail(email);
		   boolean mobilePresent=mobileApi.checkMobile(mobile);

		   System.out.println(emailPresent+":"+mobilePresent);
            //check if the mobile,email,user-pass match already exists
			if(!emailPresent && !mobilePresent){
				UserTableApi userApi=new UserTableApi(ormObj);
				PassTableApi passApi=new PassTableApi(ormObj);
				
				UserinfoModel user=UserinfoModel.newBuilder().setUserName(name).build();
				//insert userName
				
				ormObj.beginTransaction();

				Integer uidGenerated=userApi.addUser(user);
				System.out.println(uidGenerated);
				if(uidGenerated!=null  && uidGenerated!=0){
					System.out.println("uid----"+uidGenerated);
					//insert hashed password
				    String h=argon2.hash(1, 500, 1,pass);
				    String[] hasharr=h.split("[$]");
					passApi.addPass(uidGenerated,hasharr[hasharr.length-2],hasharr[hasharr.length-1]);
					
					//insert email
					emailApi.addEmail(uidGenerated, email);
					
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
	
	public List<EmailModel> getEmail(Integer userid){
		OrmImp ormObj=new OrmImp();
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		return emailApi.getEmailById(userid);
	}
	
	public List<MobileModel> getMobile(Integer userid){
		OrmImp ormObj=new OrmImp();
		MobileTableApi mobileApi=new MobileTableApi(ormObj);
		return mobileApi.getMobileById(userid);
	}
	
	public SessionModel getSessionObject(String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp();
        SessionTableApi sessionApi=new SessionTableApi(ormObj);
        SessionModel sessionObject=sessionApi.getObjectBySession(sessionid);
	    ormObj.close();
	    return sessionObject;
	}
	
	public UserinfoModel getUserById(Integer userId){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp();
		UserTableApi userApi=new UserTableApi(ormObj);
		UserinfoModel ud=userApi.getUser(userId);
	    ormObj.close();
	    return ud;
	}
	
	public boolean createSession(Integer userid,String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionTableApi sessionApi=new SessionTableApi(ormObj);
        boolean isValid=sessionApi.createSession(userid, sessionid);
		ormObj.close();
        return isValid;
	}
	
	public void deleteExpiredSessions(Integer uid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionTableApi sessionApi=new SessionTableApi(ormObj);
        sessionApi.deleteExpiredSession(uid);
        ormObj.close();
	}
	
	public void sessionInvalidate(String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
        SessionTableApi sessionApi=new SessionTableApi(ormObj);
        sessionApi.deleteSession(sessionid);
        ormObj.close();
    }
	
	public boolean updateProfile(UserinfoModel oldUserObject,UserinfoModel newUserObject) throws JsonProcessingException{
		OrmImp ormObj=new OrmImp(auditModel);
		UserTableApi userApi=new UserTableApi(ormObj);
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		MobileTableApi mobileApi=new MobileTableApi(ormObj);

		boolean isUpdated=false;
		if(newUserObject.getUserName()!="" || 
		   newUserObject.getCountry() !="" || 
				newUserObject.getGender()!=""){
			           System.out.println("ud up"+newUserObject.getUserName());
			           isUpdated=userApi.updateUserinfo(oldUserObject,newUserObject);
		}
        logger.log(Level.INFO,"p Dao after");
		for(int i=0;i<oldUserObject.getEmailObjCount();i++){
			    //if old email not equal to new
				if(!oldUserObject.getEmailObj(i).getEmailid().equals(newUserObject.getEmailObj(i).getEmailid())){
			            isUpdated=emailApi.updateEmail(oldUserObject.getEmailObj(i),newUserObject.getEmailObj(i));
				}
		}
		for(int i=0;i<oldUserObject.getMobileObjCount();i++){
		        //if old mobile not equal to new
				if(oldUserObject.getMobileObj(i).getMobileno()!=newUserObject.getMobileObj(i).getMobileno()) {
		            logger.log(Level.INFO,"p Dao mobile update before");
		            isUpdated=mobileApi.updateMobile(oldUserObject.getMobileObj(i),newUserObject.getMobileObj(i));
		            logger.log(Level.INFO,"p Dao mobile update after");
				}
		}
		ormObj.close();
		return isUpdated;
	}
	
	public boolean addNewPass(Integer id,String oldpass,String newpass) throws JsonProcessingException{
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
		PassTableApi passApi=new PassTableApi(ormObj);
		PasswordModel oldPassobj=null;

		String temp="$argon2i$v=19$m=500,t=1auditModel,p=1$";
		boolean isdub=false,iscurpass=false;
		String hash="";
		
		//check if new pass already exist
		List<PasswordModel> passObjList=passApi.getAllPassById(id);
		for(PasswordModel u:passObjList){
			hash=temp+u.getPassSalt()+"$"+u.getPassHash();
			System.out.println(" new pass check "+argon2.verify(hash, newpass)+" ");
			isdub=argon2.verify(hash,newpass);
			if(isdub==true){
                break;
			}
		}
		//check if old pass is current pass
		if(isdub==false){
			for(PasswordModel passwordObj:passObjList){
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

				PasswordModel newPassobj=PasswordModel.newBuilder().setPassStatus(0).build();
				
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
		System.out.println(obj.validate("varunsashi@gmail.com", "Varun@123").getEmailObj(0).getEmailid());
	}
}
