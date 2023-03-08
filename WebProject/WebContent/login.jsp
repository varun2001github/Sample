<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.*"%>
<%@ page import="java.util.logging.*"%>
<%@ page import="com.varun.Logger.LoggerUtil"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.net.InetAddress"%>
<%@ page import="java.net.UnknownHostException" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.varun.Dao.UserDao" %>
<html>
   <head>      
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>  
     <link rel="stylesheet" type="text/css" href="index1.css">   
   </head>  
   <body>

    <%  
	    Logger logger=LoggerUtil.getLogger(this.getClass());
        logger.log(Level.INFO,"logger init");
        String username="",remoteAddr=""; 
        request.setAttribute("a","1");
        String sessionid="";
        int sessionFlag=0,dataobjFlag=0,userid=0;
        boolean isSessionValid=false;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
	        for(Cookie c:cookies){
	        	if(c.getName().equals("userdata")){
		       		dataobjFlag=1;
		       		String encodedString=c.getValue();
		            String jsonString = URLDecoder.decode(encodedString,"UTF-8");
					JSONObject js=new JSONObject(jsonString);
                    userid=js.getInt("userid");
                    username=js.getString("username");
                }
		       	if(c.getName().equals("sessionid")){
		       		sessionFlag=1;
		       		sessionid=c.getValue();
                }
	        }
	        if(sessionFlag==1 && dataobjFlag==1){
	       	    UserDao dao=new UserDao();
	       	    isSessionValid=dao.checkSession(userid,sessionid);
	       	    if(isSessionValid){
	       	    	response.sendRedirect("userpage.jsp");
            	}
			}
        }
    %>
    <div id=21 class="hnt">

    </div>
    <div style="align_items:center; padding:20%; display:flex; flex-direction:column;">
	    <form  method="POST" style="justify-content: center; padding-left:40%" action="Authentication?operation=login" className="login-screen-container" name="body" >
			<div className="log-con" >
		        <p className="login-title">Welcome!</p>
		        <%    
		            if(session.getAttribute("logerror")!=null){
				    	out.println("<h3 style=\"color:red\">"+session.getAttribute("logerror")+"</h3>");
				    }
		            session.setAttribute("logerror",null);
			    %>
		        <div>
		          <p>Username</p>
		          <input placeholder="Email or Mobile" name="name" required />
		        </div>
		        <div>
		          <p> Password </p>
		          <input type="text" placeholder="Strong password" name="password" required />
		        </div>
		        <div style="padding-top:10px;">
		          <button style="width:64px;" type="submit" id="login-button" placeholder="login" >login</button>
	            </div>
	         </div>
	      </form>
	      <form  style="padding-left:40%; padding-top:10px;" action="register.jsp">
	         <button style="width:64px;" type="submit">Register</button>
	      </form>  
    </div>
    <div id="demo" >
	      
        <!--  
        	  <img src="https://developer.foreca.com/static/images/symbols/d000.png" style="width:50px;height:50px;">
              <div style="display: inline-flex; align-items: center; padding:5px;border-radius: 10%; height: auto; width: auto; background-color: #87cefa;">
                  <div>hi sdfsdsdfsgsdfsdsdfgf</div>
              </div>
              <div style="padding-top:1px;width:70px;height:40px;">
						        	      <div style="display:flex;">
							        	           <div style="width:40px;height:30px">
							        	              <p style="font-size:20px;">12<P>
							        	              <p style="font-size:10px;">Humidity<P>
							        	           </div>
								        	       <div style="width:40px;height:30px;padding-left:20%;">
								        	           <p style="font-size:20px;">12<P>
								        	           <p style="font-size:10px;">wind<P>
								        	       </div>
								        	       <div style="width:40px;height:30px;padding-left:40%;">
								        	           <p style="font-size:20px;">12<P>
								        	           <p style="font-size:10px;">Probability of rain<P>
								        	       </div>
						        	      </div>
					        	  </div>
        -->
    </div>
	
   </body>
 </html>
