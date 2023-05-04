package com.varun.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

import com.varun.Dao.ChatDao;
import com.varun.Logger.LoggerUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 * Servlet implementation class sendMessage
 */
public class SendMessage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger=Logger.getLogger(SendMessage.class.getName());

    public  void doPost(HttpServletRequest request, HttpServletResponse response){
		  try{
              ChatDao dao=new ChatDao(request);
			  StringBuffer jb = new StringBuffer();
			  String line = null;
		      BufferedReader reader = request.getReader();
		      while ((line = reader.readLine()) != null)
		          jb.append(line);
		  
		      JSONObject jsondata=new JSONObject(jb.toString());
			  int senderid=jsondata.getInt("senderid");
		      int  recieverid=jsondata.getInt("recieverid");
		      int isgroup=jsondata.getInt("groupyn");
		      String text=jsondata.getString("text");
		      
		      //send message data to dao 
			  dao.sendMessage(senderid, recieverid, text, isgroup);
			  response.sendRedirect(request.getContextPath()+"/userpage.jsp");
		  }catch(Exception e){
			    logger.log(Level.WARNING,"Exception",e);
		  } 
  
    }
}
