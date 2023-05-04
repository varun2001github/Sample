package com.varun.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Dao.ChatDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 * Servlet implementation class GroupFormation
 */
public class GroupFormation extends HttpServlet{
	
	private PrintWriter out=null;
	private int userid=0;
	private String groupName="";
	private static final Logger logger=Logger.getLogger(GroupFormation.class.getName());
	private ChatDao dao=null;
	
	public GroupFormation() {
	        super();
	        // TODO Auto-generated construcnametor stub
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			    dao=new ChatDao(request);
				userid=Integer.parseInt(request.getParameter("uid"));
				out=response.getWriter();
				List<UserinfoTableModel> l=null;
				l=dao.fetchFrnds(userid);
				if(l.size()>0){
					out.println("<form action=\"/WebServlet/GroupFormation?uid="+userid+"\" method=\"post\">");
					out.println("<input type=\"text\" placeholder=\"Group Name\" name=\"groupname\"><br>");
					for(UserinfoTableModel u:l){
						out.println(" <input type=\"checkbox\" name=\"groupusers\" value="+u.getUser_id()+">");
						out.println("<label for=\"names\">"+u.getUser_name()+"</label><br>");
					}
					out.println("<input type=\"submit\" value=\"create\"></form>");
					out.println("<br><button onclick=\"closeGroupCreation()\">Close</button>\n");
				}
			}catch(Exception e){
		        logger.log(Level.WARNING,"Exception",e);
		  }
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			out=response.getWriter();
			userid=Integer.parseInt(request.getParameter("uid"));
			String[] users=request.getParameterValues("groupusers");
			groupName=request.getParameter("groupname");
			out.println(userid+groupName+users[0]);
			dao.createGroup(userid,groupName,users);
		    response.sendRedirect(request.getContextPath()+"/userpage.jsp");
		    
		}catch(Exception e){
	        logger.log(Level.WARNING,"Exception",e);
	    }
	
	}

}
