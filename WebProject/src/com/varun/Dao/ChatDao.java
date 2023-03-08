package com.varun.Dao;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.*;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Orm.OrmImp;

public class ChatDao{
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
    private OrmImp ormObj=null;
    public List<UserinfoTableModel> fetchFrnds(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  UserTableApi userApi=new UserTableApi(ormObj);
		  List<UserinfoTableModel> list=userApi.fetchChatList(uid);
		  ormObj.close();
		  return list;
    }	
    
	public List<GroupInfoModel> fetchGroups(Integer uid){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  GroupTableApi grpApi=new GroupTableApi(ormObj);
		  List<GroupInfoModel> list=grpApi.fetchChatList(uid);
		  ormObj.close();
		  return list;       
	}
	
	public List<MessagesModel> chatFetch(Integer senderid,Integer recieverid,Integer isGroup){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
	      MessageTableApi messageApi=new MessageTableApi(ormObj);
	      List<MessagesModel> chatmsg=null;
		  if(isGroup==1){
			 //reciever id is group uid
			 chatmsg=messageApi.getGroupMsg(senderid,recieverid);
	      }else{
			 chatmsg=messageApi.getNormalMsg(senderid,recieverid);
		  }
		  ormObj.close();
		  return chatmsg;
    }
	
	public boolean sendMessage(Integer senderid,Integer recieverid,String text,int isGroup){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
		  MessageTableApi msgApi=new MessageTableApi(ormObj);
		  boolean isAdded=false;
		  if(isGroup==1){
			isAdded=msgApi.addGroupMessage(senderid, recieverid, text);
		  }else {
			isAdded=msgApi.addNormalMessage(senderid, recieverid, text);
		  }
		  ormObj.close();
		  return isAdded;
	}
     
	public void createGroup(Integer adminid,String groupname,String[] members){
          logger.log(Level.INFO,"method called");
  		  ormObj=new OrmImp();
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
