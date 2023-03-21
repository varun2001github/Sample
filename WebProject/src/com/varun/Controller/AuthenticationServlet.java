package com.varun.Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.Dao.UserDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel;
import com.varun.Security.CookieEncrypt;
import com.varun.Security.SessionidGenerator;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/Authentication")
public class AuthenticationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerUtil.getLogger(AuthenticationServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet(){
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operation=(String)request.getParameter("operation");
		System.out.println(operation);
		switch(operation){
		  case "login":
			  Login(request,response);
			  break;
		  case "logout":
			  Logout(request,response);
			  break;
		  case "register":
			  Register(request,response);
			  break;
		  case "passchange":
			  PassChange(request,response);
			  break;
		}
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	//login
	private void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Getting here...."); 
		HttpSession session=request.getSession();
		UserinfoTableModel userDataObj=null;
		try{      
		          UserDao dao=new UserDao();
				  String userId=request.getParameter("name");
		          String pass=request.getParameter("password");
		          System.out.println(pass);
		          Pattern emailpat=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,7}$",Pattern.CASE_INSENSITIVE);
		  		  Pattern mobilepat=Pattern.compile("^[0-9]{10}$");
		  		  Pattern passpat=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		          boolean passmatch=passpat.matcher(pass).matches();
		          boolean ismobile=userId.matches("[0-9]+");
		          boolean loginidmatch;
		
//		          //regex
//				  if(ismobile){
//					  loginidmatch=mobilepat.matcher(userId).matches();
//				  }else{
//					  loginidmatch=emailpat.matcher(pass).matches();
//				  }
				  loginidmatch=true;
				  passmatch=true;
				  if(loginidmatch && passmatch){
				   		logger.log(Level.INFO,"credentials regex valid ");
			        	userDataObj=dao.validate(userId,pass);
			        	System.out.println("dao ret"+userDataObj.getUser_id());
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
									dao.createSession(userDataObj.getUser_id(), sessionid);
									
							    	//session id cookie
							    	Cookie c1=new Cookie("sessionid",sessionid);
								    c1.setHttpOnly(true);
									c1.setMaxAge(800);
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
									c2.setMaxAge(800);
								    response.addCookie(c2);
							    	session.setAttribute("dataobj", userDataObj);
								    response.sendRedirect("userpage.jsp");
									
								}else{
									  //pass expired
								      session.setAttribute("userid",userDataObj.getUser_id());
							    	  response.sendRedirect("passchange.jsp");
								} 
						  }else {
							  logger.log(Level.INFO,"credentials regex valid");
						      session.setAttribute("logerror","Invalid loginid or password");
				              response.sendRedirect("login.jsp");
						  }
					   
				  }else if(!loginidmatch){
						session.setAttribute("logerror","Invalid loginid");
						response.sendRedirect("login.jsp");
				  }else if(!passmatch){
						session.setAttribute("logerror","Invalid password");
						response.sendRedirect("login.jsp");
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
    
	//logout
	private void Logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			  CookieEncrypt en=new CookieEncrypt();
	          System.out.println("logout servlet"+request.getAttribute("userid")+" sid"+request.getAttribute("sessionid"));
	          UserDao dao=new UserDao();
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
	              response.sendRedirect("login.jsp");
		      }
		}catch(NumberFormatException e){
		    logger.log(Level.WARNING,"NumberFormatException",e);
		}catch(Exception e){
		    logger.log(Level.WARNING,"Exception",e);
	    }
    }
		
	//register
	private void Register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    HttpSession session=request.getSession();
	        int flag=1;
	        String email=null;
	        Long mobile=null;
	        String pass=null;
	        String name=null;
	        System.out.println("registering");
			try {   
		        System.out.println("registering1");
			        email=request.getParameter("email");
	                try{
						mobile=Long.parseLong(request.getParameter("mobile"));
				        System.out.println("registering2");
					}catch(NumberFormatException e){
						flag=0;
                        e.printStackTrace();
						logger.log(Level.WARNING,"NumberFormatException and redirected back",e);
					    session.setAttribute("regerror","Invalid mobile");
						response.sendRedirect("register.jsp");
					}
	                System.out.println(flag);
	                if(flag==1){
	    		        System.out.println("registering3");
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
						System.out.println("reg in");
				        if(emailmatch && mobilematch && passmatch){
				        	System.out.print("pattern matched");
							try{
								UserDao dao=new UserDao();
								boolean updated=dao.registerUser(name,email,mobile,pass);
			
								if(updated) {
									session.setAttribute("logerror","Successfully registered");
								    response.sendRedirect("login.jsp");
								}else {
									session.setAttribute("regerror","CREDENTIALS ALREADY EXISTS OR INVALID,PLEASE CHANGE");
								    response.sendRedirect("register.jsp");
								}
							}catch (Exception e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(!emailmatch){
							System.out.println("emailmatch");
							session.setAttribute("regerror","Invalid email");
							response.sendRedirect("register.jsp");
			//				response.sendRedirect("register.jsp?error=Invalid email");
						}else if(!mobilematch){
							System.out.println("mobilematch");
	                        session.setAttribute("regerror","Invalid mobile number");
							response.sendRedirect("register.jsp");
						}else if(!passmatch){
							System.out.println("passmatch");
							session.setAttribute("regerror","Invalid password ");
							response.sendRedirect("register.jsp");
						}else {
							System.out.println("not reg");
						}
	            }
			}catch (IOException e){
				System.out.println("mobilematch");
				e.printStackTrace();
			    logger.log(Level.WARNING,"IOException",e);
			}catch(Exception e){
				System.out.println("mobilematch");
				e.printStackTrace();
			    logger.log(Level.WARNING,"Exception",e);
			}
	}
	
	//passchange
    private void PassChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session=request.getSession();
		try{
			
    			String oldpass=request.getParameter("old-pass");
    			String newpass=request.getParameter("new-pass");
    			Integer uid=null;
    			if(session.getAttribute("userid")!=null) {
    				uid=(Integer)session.getAttribute("userid");
    			}else if(request.getAttribute("userid")!=null) {
    				uid=(Integer)request.getAttribute("userid");
    			}
    			if(uid!=null) {
	    			UserDao dao=new UserDao();
	    			boolean isAdded=dao.addNewPass(uid,oldpass,newpass);
	    			if(isAdded==false){
	    				session.setAttribute("passchangeerr","Invalid password or already exists,try again");
	    				response.sendRedirect("passchange.jsp");
	    			}else{
	    				session.setAttribute("logerror","Password created successfully");
	    				response.sendRedirect("login.jsp");
	    			}
    			}else{
    				session.setAttribute("passchangeerr","Invalid password or already exists,try again");
    				response.sendRedirect("passchange.jsp");
    			}
			
		}catch(Exception e){
			logger.log(Level.WARNING,"Exception",e);
		    session.setAttribute("logerror","session expired,try login again");
		    response.sendRedirect("login.jsp");	    }
	    }
}
