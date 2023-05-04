package com.varun.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.varun.Dao.ChatDao;
import com.varun.Dao.LRUCache;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;
import com.varun.Security.EncryptionHandler;

//import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class messages
 */
public class ChatList extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger=Logger.getLogger(ChatList.class.getName());

   
    public  void service(HttpServletRequest request, HttpServletResponse response){
//        String name="",text="",time="";
//		  HttpSession session=request.getSession();
//		  EncryptionHandler handler=new EncryptionHandler();
    	  System.out.println("__chatlist servlet called ___");
		  int recieverId;
   	      String recieverName="",status="";
   	      
   	      try{      
    		      PrintWriter out=response.getWriter();
				  StringBuffer jb = new StringBuffer();
				  String line = null;
				  BufferedReader reader = request.getReader();
				  
				  while ((line = reader.readLine()) != null){
				      jb.append(line);
				  }
				  
				  JSONObject jsonObject =  HTTP.toJSONObject(jb.toString());
				  String payload=(String) jsonObject.get("Method");
			      JSONObject jsondata=new JSONObject(payload);
//				  jsondata=handler.decrypt(jsondata.getString("encrypteddata"),sessionid);
				  int id=jsondata.getInt("userid");
       	          recieverName="";
                  ChatDao dao= new ChatDao(request);
                  List<UserinfoTableModel> chatList=null;
                  List<GroupInfoModel> grpChatList=dao.fetchGroups(id);
                  UserinfoTableModel userObject=null;
                  
                  try {
                	  if(LRUCache.getThreadLocal()!=null){
                    	  
                    	  int uid=(Integer)request.getAttribute("userid");
//                    	  System.out.println(LRUCache.get(key));
                    	  userObject=(UserinfoTableModel)LRUCache.get("userid"+uid);
                    	  
                    	  if(userObject==null){
                    		  userObject=new UserinfoTableModel();
                    	  }
                    	  
                		  if(userObject.getChatList()==null){
                    		  System.out.println("---- DB chatlist----");
                    		  chatList= dao.fetchFrnds(id);
                    		  userObject.setChatList(chatList);
                    		  LRUCache.put("userid"+uid,userObject);
                    	  }else{
        			 		  System.out.println("---- cache chatlist----");
        			 		  userObject=(UserinfoTableModel)LRUCache.get("userid"+uid);
        			 		  chatList=userObject.getChatList();
                          }
                		  
                		  if(userObject.getGroupChatList()==null){
                    		  System.out.println("---- DB grp chatlist----");
                    		  grpChatList =dao.fetchGroups(id);
                    		  userObject.setGroupChatList(grpChatList);
                    		  LRUCache.put("userid"+uid,userObject);
                    	  }else{
        			 		  System.out.println("---- cache grp chatlist----");
        			 		  userObject=(UserinfoTableModel)LRUCache.get("userid"+uid);
        			 		  grpChatList=userObject.getGroupChatList();
                          }
                      }
                  }catch(Exception e) {
                	  e.printStackTrace();
                  }
                  
        		  
        		  
                  //group chat list
                  if(grpChatList!=null) {
                	  for(GroupInfoModel obj:grpChatList){
              			 recieverId=obj.getGroup_id();
              			 recieverName=obj.getGroup_name();
//      	            	 JSONObject json=new JSONObject();
//              			 json.put("groupyn",1);
//                        json.put("recieverid",recieverId);
//              			 json.put("senderid",id);
               			 out.println("<button type=\"button\" class=\"btn btn-primary active\" onclick=\"chat("+id+","+recieverId+","+1+",'"+recieverName+"');\">"+recieverName+"</button><br>");
                       }  
                  }
 	              
 	              if(chatList!=null) {
 	            	 for(UserinfoTableModel obj:chatList){
 	         			 recieverId=obj.getUser_id();
 	         			 recieverName=obj.getUser_name();
// 	 	            	 JSONObject json=new JSONObject();
// 	         			 json.put("groupyn",1);
// 	                   json.put("recieverid",recieverId);
// 	         			 json.put("senderid",id);
 	          			 out.println("<button type=\"button\" class=\"btn btn-primary active\" onclick=\"chat("+id+","+recieverId+","+0+",'"+recieverName+"');\">"+recieverName+"</button><br>");
// 	          		 if(status.equals("1")){
// 	            		    out.println("<div style=\"padding-left:5px;padding-top:4px\"><div class=\"status\" style=\"background-color:#40CF49;width:10px;height:10px;border-radius:100%;\"></div></div><br></div>");
// 	            	     }else{
// 	            	    	out.println("<br></div>");
// 	        	         }
 	 	              }  
 	              }
 	              
 	       }catch (JSONException e){
			// TODO Auto-generated catch block
		        logger.log(Level.WARNING,"JSONException",e);
 	       }catch(NumberFormatException e){
 			    logger.log(Level.WARNING,"NumberFormatException",e);
 		   }catch (IOException e){
 			    logger.log(Level.WARNING,"IOException",e);
 		   }catch(Exception e) {
 			    logger.log(Level.WARNING,"Exception",e);
 		   }

    }
    
}
