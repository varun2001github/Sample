<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.varun.Logger.LoggerUtil"%>
<%@ page import="java.util.logging.*"%>
<%@ page import="com.varun.Model.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script> 
       <%  
           System.out.println("inside profilepage");
	       String sessioninfo="";
	       UserinfoTableModel dataObj=null;
	       Integer userid=null;
	       String username=null;
	       String email=null;
	       String country=null;
	       String gender=null;
	       List<EmailTableModel> emailList=null;
	       List<MobileTableModel> mobileList=null;
	       if(request.getAttribute("dataobj")!=null){
	    	   System.out.println("pp data obj from request attribute123123");
	    	   dataObj=(UserinfoTableModel)request.getAttribute("dataobj");
	       }
	       if(dataObj!=null){
	    	    System.out.println("dataobj not null");
	            int flag=0;
	            userid=dataObj.getUser_id();
	            username=dataObj.getUser_name();
	            country=dataObj.getCountry();
	            gender=dataObj.getGender();
	       }else{
	    	   System.out.println("dataobj null");
	    	 //  response.sendRedirect("logout");
	       }
    	   System.out.println(username);
	       request.setAttribute("dataobj",dataObj);
           //response.setIntHeader("Refresh",1);
           //response.setHeader("Expires","0");
       %>
	<meta charset="UTF-8">
	<title>User profile</title>
</head>
<body>
  <div style="display:flex;">
        <h2>PROFILE</h2> 
        <a style="padding-left:135vh; padding-top:50px;" href="userpage.jsp">Continue chat</a>
  </div>
  <div style="padding-left:20%;display:flex;" id="profile_details">

        <form id="user_profile_form" action="editprofile" method="post" autocomplete="off">
           <%  
		      if(session.getAttribute("updateerr")!=null && session.getAttribute("updateerr")!=""){
			         out.println("<h3 style=\"color:red\">"+session.getAttribute("updateerr")+"</h3>");
			         session.setAttribute("updateerr","");
			  } 
           %>
           <h2>PROFILE DETAILS</h2>
           <label>NAME</label><br> <%out.println("<input name=\"username\" value=\""+ username +"\" disabled><br><br>"); %>
           <label>GENDER</label><br> <%out.println("<input name=\"gender\" value=\""+gender+"\" disabled><br><br>"); %>
           <label>COUNTRY </label><br> <%out.println("<input name=\"country\" value=\""+country+"\" disabled><br><br>"); %>
           <%    
                 out.println("<label>EMAIL</label><br>");    
		         if(dataObj!=null){
	 		         emailList=dataObj.getEmailTableObj();
			         mobileList=dataObj.getMobileTableObj();   
			         System.out.println("em mob ret frm db"+emailList.size());
		             System.out.println(mobileList.size());
		        	 for(EmailTableModel emailData:emailList){
			              out.println("<input name=\"email[]\" value="+emailData.getEmailid()+" disabled><br>");
				     }
		         }else{
			         out.println("<input name=\"email[]\" value=\"null\" disabled><br>");
		         }
		         out.println("<br><label>MOBILE</label><br>");    
		         if(dataObj!=null){
		             for(MobileTableModel mobileData:mobileList){
			                out.println("<input name=\"mobile[]\" id=\"mobile\" value="+mobileData.getMobileno()+" disabled><br>");
			         }
		         }else{
			         out.println("<input name=\"mobile[]\" id=\"mobile\" value=\"null\" disabled><br>");
		         }
           %>
           <div id="save-btn" style="display:none"><button type="submit">save</button>&ensp;<button onClick="canceledit()">cancel</button></div>
           
      </form>
      <div id="edit-btn" style="padding-top:20px;" ><button style="height:30px;width:60px;" onClick="editprofile();">Edit</button></div>
  </div>
  <button onclick="showPopup()">Show Weather details </button>
  <div id="weather-report" style="padding-left:20%;display:none;">
         <div style="display:flex;"><h2>WEATHER DETAILS</h2>&ensp;&ensp;<div style="padding-top:20px;"> <button style="height:30px;" onclick="hidePopup()">Close</button> </div></div>
         <div id="profile-weather" style="width:60px;height:60px;" ></div>
  </div>
  <script>
		  var popup = document.getElementById("weather-report");
		  function showPopup(){
		    popup.style.display = "block";
		  }
		  
		  function hidePopup(){
		    popup.style.display = "none";
		  }
		  
		  function closeGroupCreation(){
			   createGroupObj.innerHTML="";
		  }
         
         $("#user_profile_form :input").prop("disabled",true);
         <%out.println("var uid="+userid);%>
         
         var nestedEditButtons =document.getElementById("save-btn");
         var editbtn=document.getElementById("edit-btn");
         function editprofile(){
        	 editbtn.style.display="none";
        	 nestedEditButtons.style.display = "block";
    	     $("#user_profile_form :input").prop("disabled",false);
    	     console.log(document.getElementById("mobile").value);
         }
         
         function canceledit(){
        	 editbtn.style.display="block";
        	 nestedEditButtons.style.display = "none";
      	     $("#user_profile_form :input").prop("disabled",true);
         }
         
         var x = document.getElementById("weather-");
         
         if (navigator.geolocation){
         	  navigator.geolocation.getCurrentPosition(showPosition);
         }else{
        	  console.log("not sup");
         	  x.innerHTML = "Geolocation is not supported by this browser.";
         }
         
         function showPosition(position){
         	  $.ajax({
         	  		 url:"WeatherServlet?latitude="+position.coords.latitude+"&longitude="+position.coords.longitude,
         	  		 type:"GET",
         	  		 success: function(result){
         	  			$("#profile-weather").html(result);
         	  	     }
         	  	});
         }
         
         
  </script>
</body>
</html>