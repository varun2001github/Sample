package com.varun.Dao;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Api.*;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;

public class ChatDao{
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

    public List<UserinfoTableModel> fetchFrnds(Integer uid){
          logger.log(Level.INFO,"method called");
		  UserTableApi userApi=new UserTableApi();
		  return userApi.fetchChatList(uid);
    }	
    
	public List<GroupInfoModel> fetchGroups(Integer uid){
          logger.log(Level.INFO,"method called");
		  GroupTableApi grpApi=new GroupTableApi();
		  return grpApi.fetchChatList(uid);       
	}
	
	public List<MessagesModel> chatFetch(Integer senderid,Integer recieverid,Integer isGroup){
          logger.log(Level.INFO,"method called");
	      MessageTableApi messageApi=new MessageTableApi();
	      List<MessagesModel> chatmsg=null;
		  if(isGroup==1){
			 //reciever id is group uid
			 chatmsg=messageApi.getGroupMsg(senderid,recieverid);
	      }else{
			 chatmsg=messageApi.getNormalMsg(senderid,recieverid);
		  }
		  return chatmsg;
    }
	
	public boolean sendMessage(Integer senderid,Integer recieverid,String text,int isGroup){
          logger.log(Level.INFO,"method called");
		  MessageTableApi msgApi=new MessageTableApi();
		  boolean isAdded=false;
		  if(isGroup==1){
			isAdded=msgApi.addGroupMessage(senderid, recieverid, text);
		  }else {
			isAdded=msgApi.addNormalMessage(senderid, recieverid, text);
		  }
		  return isAdded;
	}
     
	public void createGroup(Integer adminid,String groupname,String[] members){
          logger.log(Level.INFO,"method called");
          GroupTableApi groupApi=new GroupTableApi();
	      GroupMemberTableApi memberApi=new GroupMemberTableApi(); 
		  if(adminid!=0 && groupname!="" && members!=null) {
		 	try {
				Integer groupId=groupApi.addGroup(adminid, groupname);
				memberApi.addGroupMember(adminid,groupId, members);
            }catch(Exception e){
				e.printStackTrace();
			}
		  }

	}
}
