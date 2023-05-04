<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <% 
      StringBuffer jb = new StringBuffer();
	  String line = null;
	 
	  BufferedReader reader = request.getReader();
	  while ((line = reader.readLine()) != null) {
	      jb.append(line);
	  }
      out.println(jb.toString()); 
    %>
   <h2> Hi all!</h2>
</body>
</html>