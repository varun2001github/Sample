package com.varun.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.varun.Dao.ChatDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class ShowMessages
 */
public class ShowMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=Logger.getLogger(ShowMessages.class.getName());
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  System.out.println("showing message");
		  try {
			      PrintWriter out=response.getWriter();
				  int senderid,recieverid;
				  StringBuffer jb = new StringBuffer();
				  String line = null;
				 
				  BufferedReader reader = request.getReader();
				  while ((line = reader.readLine()) != null) {
				      jb.append(line);
				  }
				  JSONObject jsonObject =  HTTP.toJSONObject(jb.toString());
				  String payload=(String) jsonObject.get("Method");
			      JSONObject jsondata=new JSONObject(payload);
			      String js=request.getParameter("senderidj");
				  senderid=jsondata.getInt("senderid");
			      recieverid=jsondata.getInt("recieverid");
			      Integer isGroup=jsondata.getInt("groupyn");
			      String timezone=jsondata.getString("timezone");
		          SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//		          TimeZone tz = TimeZone.getTimeZone(timezone);
//		          long offset=tz.getRawOffset();
		          System.out.println(System.currentTimeMillis()+timezone+"is offset added to current ");
		          List<MessagesModel> chatMessage=null;
		          List<GroupMessagesModel> groupChatMessage=null;	
				  ChatDao dao=new ChatDao();
				  if(isGroup!=1){
					  System.out.println("isGroup!=1");
					  chatMessage=dao.chatFetch(senderid, recieverid);
				  }else {
					  System.out.println("isGroup==1");
					  groupChatMessage=dao.groupChatFetch(recieverid);
				  }
				  
				  Integer recievedSenderId=null;
				  String text="",time="";
				  if(chatMessage!=null){
					  System.out.println("Chat fetch "+chatMessage);
					  if(chatMessage.size()>0){
				             for(MessagesModel m:chatMessage){
				                	recievedSenderId=m.getSenderid();
				         			text=(String)m.getText();
				         			Timestamp timestamp = new Timestamp(m.getChattime().longValue());
				         			time = dateFormat.format(timestamp);
				         			if(recievedSenderId==senderid){
				                	      out.println("<p style=\"padding-left:70%;\">"+text+"<br>"+time+"</p>");
				                	}else{
				                		  out.println("<p>"+text+"<br>"+time+"</p>");
				                	}
				     		  }
				       }	
				  }else{
					  System.out.println("group Chat fetch ");
					  if(groupChatMessage.size()>0){
				             for(GroupMessagesModel m:groupChatMessage){
				                	recievedSenderId=m.getSenderid();
				         			text=(String)m.getText();
				         			Timestamp timestamp = new Timestamp(m.getChattime().longValue());
				         			time = dateFormat.format(timestamp);
				         			if(recievedSenderId==senderid){
				                	      out.println("<p style=\"padding-left:70%;\">"+text+"<br>"+time+"</p>");
				                	}else{
				                		  out.println("<p>"+text+"<br>"+time+"</p>");
				                	}
				     		  }
				       }
				  }
		          
         }catch(Exception e){
        	 e.printStackTrace();
             logger.log(Level.WARNING,"Exception",e);
         }                
     }
}
