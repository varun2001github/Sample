package com.varun.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.Api.SessionTableApi;
import com.varun.Dao.AuthDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel;
import com.varun.Model.SessionTableModel;
import com.varun.Security.EncryptionHandler;
import com.varun.Security.SessionidGenerator;



@WebServlet("/login")
public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerUtil.getLogger(Login.class);

	@SuppressWarnings("unused")
	public  void service(HttpServletRequest request, HttpServletResponse response){
		System.out.println("Getting here...."); 
		HttpSession session=request.getSession();
		  try{
				  SessionTableApi sessionApi=new SessionTableApi();
				  String userId=request.getParameter("name");
		          String pass=request.getParameter("password");
		          System.out.println(pass);
		          Pattern emailpat=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,7}$",Pattern.CASE_INSENSITIVE);
		  		  Pattern mobilepat=Pattern.compile("^[0-9]{10}$");
		  		  Pattern passpat=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		  		  UserinfoTableModel userDataObj=null;
		          boolean passmatch=passpat.matcher(pass).matches();
		          boolean ismobile=userId.matches("[0-9]+");
		          boolean loginidmatch;
		
		          //regex
		//		  if(ismobile){
		//			  loginidmatch=mobilepat.matcher(u).matches();
		//		  }else{
		//			  loginidmatch=emailpat.matcher(u).matches();
		//		  }
				  loginidmatch=true;
				  passmatch=true;
				  if(loginidmatch && passmatch){
				   		logger.log(Level.INFO,"credentials regex valid ");
			        	AuthDao dao=new AuthDao();
			        	userDataObj=dao.validate(userId,pass);
//			        	System.out.println("dao ret"+userDataObj.getUser_id());
						if(userDataObj!=null){
								long passCreatedTime=userDataObj.getPassTableObj().getCreated_time().longValue();
								long usedDays = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()-passCreatedTime);
								System.out.println("used days "+usedDays);
								
								//if login valid
								if(usedDays<60){
									
									//delete expired sessions of user
									dao.deleteExpiredSessions(userDataObj.getUser_id());
									
									//create session
									String sessionid=SessionidGenerator.Generate();
							    	sessionApi.createSession(userDataObj.getUser_id(),sessionid);
							    	
							    	//session id cookie
							    	Cookie c1=new Cookie("sessionid",sessionid);
								    c1.setHttpOnly(true);
									c1.setMaxAge(600);
									c1.setSecure(true);
								    response.addCookie(c1);
								    
								    //Converting the user Object to JSONString
									JSONObject js=new JSONObject();
									js.put("userid",userDataObj.getUser_id());
									js.put("username",userDataObj.getUser_name());
								    ObjectMapper mapper = new ObjectMapper();
								    String jsonString =js.toString();
//									JSONObject js=new JSONObject(jsonString);
//							  		EncryptionHandler eh=new EncryptionHandler();
//							    	Cookie c2=new Cookie("userObject",eh.encrypt(js));
								    String encodedJson = URLEncoder.encode(jsonString, "UTF-8");
							    	Cookie c2=new Cookie("userdata",encodedJson);
							    	c2.setHttpOnly(true);
									c2.setSecure(true);
								    response.addCookie(c2);
							    	

								    session.setAttribute("dataobj", userDataObj);
								    response.sendRedirect("userpage.jsp");
									
								}else {
									  //pass expired
								      session.removeAttribute("username");
							    	  response.sendRedirect("passchange.jsp");
								} 
						  }else {
							  logger.log(Level.INFO,"credentials regex valid");
						      session.setAttribute("logerror","Invalid loginid or password");
				              response.sendRedirect("index.jsp");
						  }
					   
				  }else if(!loginidmatch){
						session.setAttribute("logerror","Invalid loginid");
						response.sendRedirect("index.jsp");
				  }else if(!passmatch){
						session.setAttribute("logerror","Invalid password");
						response.sendRedirect("index.jsp");
				  }
		  }catch(NumberFormatException e){
		        logger.log(Level.WARNING,"NumberFormatException",e);
		  } catch (IOException e) {
			    logger.log(Level.WARNING,"IOException",e);
		  } catch (ClassNotFoundException e) {
			    logger.log(Level.WARNING,"ClassNotFoundException",e);
		  }catch(Exception e) {
			    logger.log(Level.WARNING,"Exception",e);
		  }	  
    }
	protected void doPost(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Getting here....");
	}
}

