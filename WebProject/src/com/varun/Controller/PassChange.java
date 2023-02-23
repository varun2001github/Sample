package com.varun.Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.varun.Dao.AuthDao;
import com.varun.Dao.AuthDao.*;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class passChange
 */
@WebServlet("/changepass")
public class PassChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerUtil.getLogger(PassChange.class);

	public  void service(HttpServletRequest request, HttpServletResponse response){
		HttpSession session=request.getSession();
		try{
			
    			String oldpass=request.getParameter("old-pass");
    			String newpass=request.getParameter("new-pass");
    			int id=(Integer)session.getAttribute("userid");
    			AuthDao dao=new AuthDao();
    			boolean isAdded=dao.addNewPass(id,oldpass,newpass);
    			if(isAdded==false){
    				session.setAttribute("passchangeerr","Invalid password or already exists,try again");
    				response.sendRedirect("passchange.jsp");
    			}else{
    				session.setAttribute("logerror","Password created successfully");
    				response.sendRedirect("index.jsp");
    			}
			
		}catch(NumberFormatException e){
		    logger.log(Level.WARNING,"Exception",e);
		    session.setAttribute("logerror","session expired,try login again");
			try {
				response.sendRedirect("index.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			    logger.log(Level.WARNING,"IOException",e);
			}
		} catch (IOException e){
		    logger.log(Level.WARNING,"IOException",e);
		}catch(Exception e){
		    logger.log(Level.WARNING,"Exception",e);
	    }
	}	

}
