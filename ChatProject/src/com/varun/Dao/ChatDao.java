package com.varun.Dao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.ProtoModel.UserModel.User;
import com.varun.Api.*;
import com.varun.Api.Interface.UserApi;
import com.varun.Model.*;
import com.varun.Orm.OrmImp;

public class ChatDao{
	private static final Logger logger=Logger.getLogger(ChatDao.class.getName());
    private OrmImp ormObj=null;
	private AuditModel auditModel=null;

    public ChatDao(){
		
	}
    public ChatDao(HttpServletRequest request){
    	auditModel=new AuditModel((Integer)request.getAttribute("userid"),(String)request.getAttribute("sessionid"),request.getRemoteAddr());
	}
    
	public List<User> fetchFrnds(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  UserApi userApiImpl=new UserApiImpl(ormObj);
		  List<User> list=null;
		  list =userApiImpl.fetchChatList(uid);
    	  ormObj.close();
		  return list;
    }	
    
	public List<GroupInfoModel> fetchGroups(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  GroupApi grpApi=new GroupApi(ormObj);
		  List<GroupInfoModel> list=null;
    	  list =grpApi.fetchChatList(uid);
    	  ormObj.close();
		  return list;       
	}
	
	public List<MessagesModel> chatFetch(Integer senderid,Integer recieverid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
	      MessageApi messageApi=new MessageApi(ormObj);
	      List<MessagesModel> chatmsg=null;
		  chatmsg=messageApi.getNormalMsg(senderid,recieverid);
		  ormObj.close();
		  return chatmsg;
    }
	
	public List<GroupMessagesModel> groupChatFetch(Integer groupid){
        logger.log(Level.INFO,"method called");
		  ormObj=new OrmImp();
	      GroupMessageApi groupMessageApi=new GroupMessageApi(ormObj);
	      List<GroupMessagesModel> chatmsg=null;
		  
	      //reciever id is group uid
		  chatmsg=groupMessageApi.getGroupMsg(groupid);
	      ormObj.close();
		  return chatmsg;
   }
	public boolean sendMessage(Integer senderid,Integer recieverid,String text,int isGroup){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp(auditModel);
		  MessageApi msgApi=new MessageApi(ormObj);
	      GroupMessageApi groupMessageApi=new GroupMessageApi(ormObj);
		  boolean isAdded=false;
		  if(isGroup==1){
			isAdded=groupMessageApi.addGroupMessage(senderid,recieverid,text);
		  }else {
			isAdded=msgApi.addNormalMessage(senderid, recieverid, text);
		  }
		  ormObj.close();
		  return isAdded;
	}
     
	public void createGroup(Integer adminid,String groupname,String[] members){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp(auditModel);
          GroupApi groupApi=new GroupApi(ormObj);
	      GroupMemberApi memberApi=new GroupMemberApi(ormObj); 
		  if(adminid!=0 && groupname!="" && members!=null) {
		 	try {
				Integer groupId=groupApi.addGroup(adminid, groupname);
				memberApi.addGroupMember(adminid,groupId, members);
            }catch(Exception e){
				e.printStackTrace();
			}
		  }
		  ormObj.close();
	}
}
