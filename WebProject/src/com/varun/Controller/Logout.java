package com.varun.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Dao.AuthDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Security.CookieEncrypt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class Logout extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerUtil.getLogger(Logout.class);

	public  void service(HttpServletRequest request, HttpServletResponse response){
		try {
			  CookieEncrypt en=new CookieEncrypt();
	          System.out.println("logout servlet"+request.getAttribute("userid")+" sid"+request.getAttribute("sessionid"));
	          AuthDao dao=new AuthDao();
//		      String userid=(String)request.getAttribute("userid"); 
		      Cookie[] cookies=request.getCookies();
		      if(cookies!=null){
			      for(Cookie c:cookies){
			    	  if(c.getName().equals("userdata")){
			    		  c.setMaxAge(0);
			    		  response.addCookie(c);
			    	  }
				      if(c.getName().equals("sessionid")){
				       		dao.sessionInvalidate(c.getValue());
				       		c.setMaxAge(0);
				       		response.addCookie(c);
				       		System.out.println("session deleted frm cookie");
				      }
			      }
		      }
	          
		      if(request.getAttribute("responseerr")!=null && !request.getAttribute("responseerr").equals("")){
		    	  int res=(int)request.getAttribute("responseerr");
		    	  response.sendError(res);
		      }else{
	              response.sendRedirect("index.jsp");
		      }
		}catch(NumberFormatException e){
		    logger.log(Level.WARNING,"Exception",e);
		} catch (IOException e){
		    logger.log(Level.WARNING,"Exception",e);
		}catch(Exception e) {
		    logger.log(Level.WARNING,"Exception",e);
	    }	 
	      
	}
}