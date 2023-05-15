package com.varun.Api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Model.DataObject;
//import com.varun.Model.SessionModel;
import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.SessionModel.Builder;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.ProtoMapper;

public class SessionTableApi{
	private static final Logger logger=Logger.getLogger(SessionTableApi.class.getName());
	private SessionModel sessionTableObj=null;
	private static String Table="session_info";
	private static CriteriaBuilder cb=new CriteriaBuilder();
	private OrmImp orm;
    
    public SessionTableApi(OrmImp obj){
   	    try{
			orm=obj;
   	    	logger.log(Level.INFO," constructor ");
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public boolean createSession(Integer userid,String sessionid){
    	Instant now = Instant.now();
    	sessionTableObj=SessionModel.newBuilder()
    	.setUserId(userid)
    	.setSessionId(sessionid)
    	.setExpiry(Date.from(now.plus(500, ChronoUnit.MINUTES)).getTime()).build();
    	Integer ins=orm.InsertQuery(ProtoMapper.getDataObject(sessionTableObj)).Insert();
    	if(ins!=null){
    		return true;
    	}
    	return false;
    }
    
    public SessionModel getObjectBySession(String sessionid){
        orm.SelectAll().From(Table).Where(cb.addEquals("session_id",sessionid));
        List<DataObject> l=orm.getSelect();
        SessionModel sessionObject=null;
        if(l.size()>0){
        	sessionObject=(SessionModel)ProtoMapper.getProtoObject(SessionModel.newBuilder(),l.get(0));
            return sessionObject;
        }
        return null;
    }
    
//    public boolean checkIdWithSession(String sessionid){
//        orm.SelectQuery("user_id").From(Table)
//        .Where(cb.addEquals("session_id",sessionid));
//        
//		List<DataObject> l=orm.getSelect();
//        if(l.size()>0){
//            return true;
//        }
//        return false;
//    }
    //proto 
    
    public void deleteSession(String sessionid){
        orm.DeleteQuery(Table).Where(cb.addEquals("session_id",sessionid));
        orm.delete();
    }
    public void deleteExpiredSession(Integer uid) {
        orm.DeleteQuery(Table).Where(cb.addEquals("user_id",uid)).And(cb.addLessThan("Expiry",System.currentTimeMillis()));
        orm.delete();
    }
    
}
