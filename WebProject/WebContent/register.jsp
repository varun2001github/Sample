<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.varun.Logger.LoggerUtil"%>
<%@ page import="java.util.logging.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <%    
        Logger logger=LoggerUtil.getLogger(this.getClass());
        if(session.getAttribute("user")!=null){
	         response.sendRedirect("userpage.jsp");           
	    }
    %>
    <div  style="text-shadow: 10px;padding:13%;justify-content: center;align-items: center;display:flex;flex-direction:column;">
		    <form action="register"  method="post" className="login-screen-container" name="body" >
				<div className="log-con">
			        <h1 className="login-title">Register Now</h1>
			        <%    
					    if(session.getAttribute("regerror")!=null){
					    	out.println("<h3 style=\"color:red\">"+session.getAttribute("regerror")+"</h3>");
					    }
			            session.setAttribute("regerror",null);
				    %>
			        <div>
			          <p>Enter your Name</p>
			          <input placeholder="Name" name="name" required />
			        </div>
			        <div>
			          <p>Enter your email</p>
			          <input placeholder="youremail@example.com" name="email" required />
			        </div>
			        <div>
			          <p>Enter Password</p>
			          <input type="password" placeholder="Strong password" name="password" required />
			        </div>
			        <div>
			          <p>Mobile</p>
			          <input type="password" placeholder="Mobile" name="mobile" required />
			        </div>
			        <div style="padding-top:10px; padding-left:20px;">
			          <button type="submit" className="login-button" placeholder="login" >register</button><br>			         
		            </div>
		            <div style="padding-top:20px;">
		              <a href="index.jsp">Go back to login page</a>
		            </div>
		         </div>
		      </form>    
    </div>
</body>
</html>