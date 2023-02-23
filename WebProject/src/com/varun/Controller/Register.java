package com.varun.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

import com.varun.Dao.AuthDao;
import com.varun.Dao.ChatDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerUtil.getLogger(Register.class);

	public  void service(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        int flag=1;
        String email=null;
        Integer mobile=null;
        String pass=null;
        String name=null;
		try {   
		        email=request.getParameter("email");
                try{
					mobile=Integer.parseInt(request.getParameter("mobile"));
				}catch(NumberFormatException e){
					flag=0;
				    logger.log(Level.WARNING,"NumberFormatException and redirected back",e);
				    session.setAttribute("regerror","Invalid mobile");
				    flag=1;
					response.sendRedirect("register.jsp");
				}
                if(flag==0) {
			    pass=request.getParameter("password");
				name=request.getParameter("name");
				//patterns
				Pattern emailpat=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,7}$", Pattern.CASE_INSENSITIVE);
				Pattern mobilepat=Pattern.compile("^[0-9]{10}$");
				Pattern passpat=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");		
		        
				//pattern validations
		        boolean emailmatch=emailpat.matcher(email).matches();
		        boolean mobilematch=mobilepat.matcher(mobile+"").matches();
		        boolean passmatch=passpat.matcher(pass).matches();
				
		        if(emailmatch && mobilematch && passmatch){
		        	System.out.print("pattern matched");
					try{
						AuthDao dao=new AuthDao();
						boolean updated=dao.registerUser(name,email,mobile,pass);
	
						if(updated) {
							session.setAttribute("logerror","Successfully registered");
						    response.sendRedirect("index.jsp");
						}else {
							session.setAttribute("regerror","CREDENTIALS ALREADY EXISTS OR INVALID,PLEASE CHANGE");
						    response.sendRedirect("register.jsp");
						}
					}catch (Exception e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!emailmatch){
					session.setAttribute("regerror","Invalid email");
					response.sendRedirect("register.jsp");
	//				response.sendRedirect("register.jsp?error=Invalid email");
				}else if(!mobilematch){
					session.setAttribute("regerror","Invalid mobile number");
					response.sendRedirect("register.jsp");
				}else if(!passmatch){
					session.setAttribute("regerror","Invalid password ");
					response.sendRedirect("register.jsp");
				}
            }
		}catch (IOException e) {
		    logger.log(Level.WARNING,"IOException",e);
		}catch(Exception e) {
		    logger.log(Level.WARNING,"Exception",e);
		}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
}
