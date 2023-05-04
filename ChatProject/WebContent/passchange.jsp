<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.logging.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change password</title>
</head>
<body>
       <div style="align_items:center; padding:20%; display:flex; flex-direction:column;">
	    <form method="post" style=" justify-content: center; padding-left:40%" action="Authentication/passchange" className="login-screen-container" name="body" >
			<div className="log-con" >
		        <p>Password expired,please change the password</p>
		         <%    
				    if(session.getAttribute("passchangeerr")!=null){
				    	out.println("<h3 style=\"color:red\">"+session.getAttribute("passchangeerr")+"</h3>");
				    }
		            session.setAttribute("passchangeerr",null);
			    %>
		        <div>
		          <p>Enter old password</p>
		          <input type="text" placeholder="Old password" name="old-pass" required />
		        </div>
		        <div>
		          <p>Enter new Password </p>
		          <input type="text" placeholder="New password" name="new-pass" required />
		        </div>
		        <input type="hidden" name="old-pass" required />
		        <div style="padding-top:10px;">
		          <button style="width:64px;" type="submit" className="login-button" placeholder="login" >confirm</button>
	            </div>
	         </div>
	      </form> 
    </div>
</body>
</html>