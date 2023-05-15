package com.varun.Dao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.ProtoModel.UserModel.UserinfoModel;
import com.varun.Api.*;
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
    
	public List<UserinfoModel> fetchFrnds(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  UserTableApi userApi=new UserTableApi(ormObj);
		  List<UserinfoModel> list=null;
		  list =userApi.fetchChatList(uid);
    	  ormObj.close();
		  return list;
    }	
    
	public List<GroupInfoModel> fetchGroups(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  GroupTableApi grpApi=new GroupTableApi(ormObj);
		  List<GroupInfoModel> list=null;
    	  list =grpApi.fetchChatList(uid);
    	  ormObj.close();
		  return list;       
	}
	
	public List<MessagesModel> chatFetch(Integer senderid,Integer recieverid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
	      MessageTableApi messageApi=new MessageTableApi(ormObj);
	      List<MessagesModel> chatmsg=null;
		  chatmsg=messageApi.getNormalMsg(senderid,recieverid);
		  ormObj.close();
		  return chatmsg;
    }
	
	public List<GroupMessagesModel> groupChatFetch(Integer groupid){
        logger.log(Level.INFO,"method called");
		  ormObj=new OrmImp();
	      groupMessageTableApi groupMessageApi=new groupMessageTableApi(ormObj);
	      List<GroupMessagesModel> chatmsg=null;
		  
	      //reciever id is group uid
		  chatmsg=groupMessageApi.getGroupMsg(groupid);
	      ormObj.close();
		  return chatmsg;
   }
	public boolean sendMessage(Integer senderid,Integer recieverid,String text,int isGroup){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp(auditModel);
		  MessageTableApi msgApi=new MessageTableApi(ormObj);
	      groupMessageTableApi groupMessageApi=new groupMessageTableApi(ormObj);
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
          GroupTableApi groupApi=new GroupTableApi(ormObj);
	      GroupMemberTableApi memberApi=new GroupMemberTableApi(ormObj); 
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
