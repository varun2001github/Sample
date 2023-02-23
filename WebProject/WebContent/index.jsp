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
        //out.println(remoteAddr);
       /* boolean sessionidFlag=false,userObjectFlag=false;
	    Cookie[] cookies=request.getCookies();
        if(cookies!=null){
	        for(Cookie c:cookies){
	        	if(c.getName().equals("sessionid") && !c.getValue().equals("")){
	        		sessionidFlag=true;
	        	}
	        	if(c.getName().equals("userObject") && !c.getValue().equals("")){
	        		userObjectFlag=true;
	        	}
	        }
	        if(sessionidFlag && userObjectFlag){
        		response.sendRedirect("userpage.jsp"); 
            }
        }
        System.out.println("loginpage uid"+request.getServletContext().getAttribute("userid"));
        if(username!=""){
           response.sendRedirect("userpage.jsp");           
        }
        response.setHeader("Expires","0");
        String pass="";
        pass=request.getParameter("password")+"asd";
        */
    %>
    <div id=21 class="hnt">

    </div>
    <div style="align_items:center; padding:20%; display:flex; flex-direction:column;">
	    <form  method="POST" style="justify-content: center; padding-left:40%" action="login" className="login-screen-container" name="body" >
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
    <div id="ms" >
        <!--  <div style="display: inline-flex; align-items: center; padding:5px;border-radius: 10%; height: auto; width: auto; background-color: #87cefa;">
                  <div>hi sdfsdsdfsgsdfsdsdfgf</div>
              </div>
        -->
    </div>
   </body>
 </html>
