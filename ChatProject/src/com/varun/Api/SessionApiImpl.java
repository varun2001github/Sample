package com.varun.Api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.Interface.SessionApi;
import com.varun.Model.DataObject;
//import com.varun.Model.Session;
import com.ProtoModel.UserModel.Session;
import com.ProtoModel.UserModel.Session.Builder;
import com.varun.Orm.CriteriaBuilder;
import com.varun.Orm.OrmImp;
import com.varun.Orm.ProtoMapper;

public class SessionApiImpl implements SessionApi{
	private static final Logger logger=Logger.getLogger(SessionApiImpl.class.getName());
	private Session sessionTableObj=null;
	private static String Table="session_info";
	private static CriteriaBuilder cb=new CriteriaBuilder();
	private OrmImp orm;
    
    public SessionApiImpl(OrmImp obj){
   	    try{
			orm=obj;
   	    	logger.log(Level.INFO," constructor ");
		}catch(Exception e){
			// TODO Auto-generated catch block
   	    	logger.log(Level.WARNING," constructor ",e);
		}
    }
    
    public SessionApiImpl(){
   	    orm=new OrmImp();
    }
    
    @Override
	public boolean createSession(Integer userid,String sessionid){
    	Instant now = Instant.now();
    	sessionTableObj=Session.newBuilder()
    	.setUserId(userid)
    	.setSessionId(sessionid)
    	.setExpiry(Date.from(now.plus(500, ChronoUnit.MINUTES)).getTime()).build();
    	Integer ins=orm.InsertQuery(ProtoMapper.getDataObject(sessionTableObj)).Insert();
    	if(ins!=null){
    		return true;
    	}
    	return false;
    }
    
    @Override
	public Session getObjectBySession(String sessionid){
        orm.SelectAll().From(Table).Where(cb.addEquals("session_id",sessionid));
        List<DataObject> l=orm.getSelect();
        Session sessionObject=null;
        if(l.size()>0){
        	sessionObject=(Session)ProtoMapper.getProtoObject(Session.newBuilder(),l.get(0));
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
    
    @Override
	public void deleteSession(String sessionid){
        orm.DeleteQuery(Table).Where(cb.addEquals("session_id",sessionid));
        orm.delete();
    }
    @Override
	public void deleteExpiredSession(Integer uid) {
        orm.DeleteQuery(Table).Where(cb.addEquals("user_id",uid)).And(cb.addLessThan("Expiry",System.currentTimeMillis()));
        orm.delete();
    }
    
}
