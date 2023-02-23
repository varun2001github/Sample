package com.varun.Api;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.SessionTableModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;

public class SessionTableApi {
	private static final Logger logger=LoggerUtil.getLogger(SessionTableApi.class);
	private static SessionTableModel sessionTableObj=new SessionTableModel();
	private static CriteriaBuilder cb=new CriteriaBuilder();
	private OrmImp orm;
    
    public SessionTableApi(){
   	    try{
			orm=new OrmImp(sessionTableObj);
   	    	logger.log(Level.INFO," constructor ");
		}catch (Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    public void closeOrmConnection(){
    	orm.close();
	}
    public SessionTableApi(SessionTableModel sessionTableObj){
   	    try{
   	    	SessionTableApi.sessionTableObj=sessionTableObj;
			orm=new OrmImp(sessionTableObj);
   	    	logger.log(Level.INFO," constructor ");
		}catch (Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    public boolean createSession(Integer userid,String sessionid){
		SessionTableModel sessionObj=new SessionTableModel();
    	Instant now = Instant.now();
    	sessionObj.setUser_id(userid);
		sessionObj.setSession_id(sessionid);
		sessionObj.setExpiry(BigInteger.valueOf(Date.from(now.plus(10, ChronoUnit.MINUTES)).getTime()));
    	orm.Insert(sessionObj);
    	return false;
    }
    
    public Integer getIdBySession(String sessionid){
        orm.SelectQuery("user_id").Where(cb.addEquals("session_id",sessionid));
        
        @SuppressWarnings("unchecked")
		List<SessionTableModel> l=(List<SessionTableModel>)(Object)orm.getSelect();
        if(l.size()>0){
            return l.get(0).getUser_id();
        }
        return null;
    }
    
    public boolean checkIdWithSession(Integer userid,String sessionid){
        orm.SelectQuery("user_id")
        .Where(cb.addEquals("session_id",sessionid))
        .And(cb.addEquals("user_id",userid));
        
        @SuppressWarnings("unchecked")
		List<SessionTableModel> l=(List<SessionTableModel>)(Object)orm.getSelect();
        if(l.size()>0){
            return true;
        }
        return false;
    }
    public void deleteSession(String sessionid){
        orm.DeleteQuery().Where(cb.addEquals("session_id",sessionid));
        orm.delete();
    }
    public void deleteExpiredSession(Integer uid) {
        orm.DeleteQuery().Where(cb.addEquals("user_id",uid)).And(cb.addLessThan("Expiry",System.currentTimeMillis()));
        orm.delete();
    }
    
}
