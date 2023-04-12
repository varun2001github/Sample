package com.varun.Api;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Logger.LoggerUtil;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
import com.varun.Model.SessionTableModel;
import com.varun.Model.UserinfoTableModel;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.Table;

public class SessionTableApi{
	private static final Logger logger=LoggerUtil.getLogger(SessionTableApi.class);
	private static SessionTableModel sessionTableObj=null;
	private static String Table=SessionTableModel.class.getAnnotation(Table.class).name();
	private static CriteriaBuilder cb=new CriteriaBuilder();
	private OrmImp orm;
    
    public SessionTableApi(OrmImp obj){
   	    try{
			orm=obj;
   	    	logger.log(Level.INFO," constructor ");
		}catch (Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public boolean createSession(Integer userid,String sessionid){
    	sessionTableObj=new SessionTableModel();
    	Instant now = Instant.now();
    	sessionTableObj.setUser_id(userid);
    	sessionTableObj.setSession_id(sessionid);
    	sessionTableObj.setExpiry(Date.from(now.plus(500, ChronoUnit.MINUTES)).getTime());
    	orm.Insert(sessionTableObj.getDataObject());
    	return false;
    }
    
    public Integer getIdBySession(String sessionid){
        orm.SelectQuery("user_id").From(Table).Where(cb.addEquals("session_id",sessionid));
        List<DataObject> l=orm.getSelect();
        if(l.size()>0){
            return (Integer)l.get(0).getDataMap().get("user_id");
        }
        return null;
    }
    
    public boolean checkIdWithSession(Integer userid,String sessionid){
        orm.SelectQuery("user_id").From(Table)
        .Where(cb.addEquals("session_id",sessionid))
        .And(cb.addEquals("user_id",userid));
        
		List<DataObject> l=orm.getSelect();
        if(l.size()>0){
            return true;
        }
        return false;
    }
    public void deleteSession(String sessionid){
        orm.DeleteQuery(Table).Where(cb.addEquals("session_id",sessionid));
        orm.delete();
    }
    public void deleteExpiredSession(Integer uid) {
        orm.DeleteQuery(Table).Where(cb.addEquals("user_id",uid)).And(cb.addLessThan("Expiry",System.currentTimeMillis()));
        orm.delete();
    }
    
}
