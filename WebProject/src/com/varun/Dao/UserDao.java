package com.varun.Dao;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Api.PassTableApi;
import com.varun.Api.SessionTableApi;
import com.varun.Api.UserTableApi;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.OrmImp;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
//import javax.transaction;

public class UserDao{
	private Argon2 argon2 = (Argon2) Argon2Factory.create(Argon2Types.ARGON2i);
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	private AuditTableModel auditModel=null;
	
	public UserDao(){
		
	}
	
    public UserDao(HttpServletRequest request){
    	auditModel=new AuditTableModel((Integer)request.getAttribute("userid"),(String)request.getAttribute("sessionid"),request.getRemoteAddr());
	}
	
	public UserinfoTableModel validate(String loginId,String password) throws ClassNotFoundException {
        logger.log(Level.INFO,"method called");
		Integer userid=null;
		OrmImp ormObj=new OrmImp(auditModel);
		
		UserTableApi userApi=new UserTableApi(ormObj);
		PassTableApi passApi=new PassTableApi(ormObj);
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		MobileTableApi mobileApi=new MobileTableApi(ormObj);
		UserinfoTableModel userDataObj=null;
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
			
            //System.out.println("---------"+userid);
            //System.out.print(quer2);
			PasswordTableModel passObj=null;
			passObj= passApi.getPassById(userid);

			if(passObj!=null){
				String hash=temp+passObj.getPass_salt()+"$"+passObj.getPass_hash();
				isvalid=argon2.verify(hash,password);
//				System.out.println("pass validity"+isvalid);
			}
			//<-argon
            //if use and pass matching,then 

			if(isvalid){	
				userDataObj=userApi.getUserById(userid);
				//password policy check- 30days
			    if(userDataObj!=null){
			    	userDataObj.setPassTableObj(passObj);
			    	userDataObj.setMobileTableObj(mobileApi.getMobileById(userid));
			    	userDataObj.setEmailTableObj(emailApi.getEmailById(userid));
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
				//insert userName
				
				ormObj.beginTransaction();

				Integer uidGenerated=userApi.addUser(name);
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
	
	public List<EmailTableModel> getEmail(Integer userid){
		OrmImp ormObj=new OrmImp();
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		return emailApi.getEmailById(userid);
	}
	
	public List<MobileTableModel> getMobile(Integer userid){
		OrmImp ormObj=new OrmImp();
		MobileTableApi mobileApi=new MobileTableApi(ormObj);
		return mobileApi.getMobileById(userid);
	}
	
	public SessionTableModel getSessionObject(String sessionid){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp();
        SessionTableApi sessionApi=new SessionTableApi(ormObj);
        SessionTableModel sessionObject=sessionApi.getObjectBySession(sessionid);
	    ormObj.close();
	    return sessionObject;
	}
	
	public UserinfoTableModel getUserById(Integer userId){
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp();
		UserTableApi userApi=new UserTableApi(ormObj);
        UserinfoTableModel ud=userApi.getUserById(userId);
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
	
	public boolean updateProfile(UserinfoTableModel oldDataObj,UserinfoTableModel newDataObj) throws JsonProcessingException{
		OrmImp ormObj=new OrmImp(auditModel);
		UserTableApi userApi=new UserTableApi(ormObj);
		EmailTableApi emailApi=new EmailTableApi(ormObj);
		MobileTableApi mobileApi=new MobileTableApi(ormObj);

		boolean isUpdated=false;
		System.out.println("p Dao bef");
		if(newDataObj.getUser_name()!=null || 
		   newDataObj.getCountry() !=null || 
				newDataObj.getGender()!=null){
			           System.out.println("ud up"+newDataObj.getUser_name());
			           isUpdated=userApi.updateUserinfo(oldDataObj,newDataObj);
		}
		System.out.println("p Dao after");
		for(int i=0;i<oldDataObj.getEmailTableObj().size();i++){
			    //if old email not equal to new
				if(!oldDataObj.getEmailTableObj().get(i).getEmailid().equals(newDataObj.getEmailTableObj().get(i).getEmailid())){
			            System.out.println("em up");
			            isUpdated=emailApi.updateEmail(oldDataObj.getUser_id(),oldDataObj.getEmailTableObj().get(i),newDataObj.getEmailTableObj().get(i));
				}
		}
		for(int i=0;i<oldDataObj.getMobileTableObj().size();i++){
		        //if old mobile not equal to new
				if(!oldDataObj.getMobileTableObj().get(i).getMobileno().equals(newDataObj.getMobileTableObj().get(i).getMobileno())) {
		            System.out.println("mobile up");
		            isUpdated=mobileApi.updateMobile(oldDataObj.getUser_id(),oldDataObj.getMobileTableObj().get(i),newDataObj.getMobileTableObj().get(i));
				}
		}
		ormObj.close();
		return isUpdated;
	}
	
	public boolean addNewPass(Integer id,String oldpass,String newpass) throws JsonProcessingException{
        logger.log(Level.INFO,"method called");
		OrmImp ormObj=new OrmImp(auditModel);
		PassTableApi passApi=new PassTableApi(ormObj);
		PasswordTableModel oldPassobj=new PasswordTableModel();

		String temp="$argon2i$v=19$m=500,t=1auditModel,p=1$";
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
						oldPassobj=u;
		                break;
					}
				}
			}
			
			//if both old and new pass entered is valid ,insert
			if(iscurpass==true){
				PasswordTableModel newPassobj=new PasswordTableModel();
			    String h=argon2.hash(1, 500, 1,newpass);
			    newPassobj.setPass_status(0);
				
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
		System.out.println(obj.validate("varunsashi@gmail.com", "Varun@123").getEmailTableObj().get(0).getEmailid());
	}
}
