<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.varun.Dao.*" %>
<%@ page import="com.varun.Model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.crypto.*" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="com.varun.Model.*" %>
<%@ page import="com.varun.Security.CookieEncrypt" %> 
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="com.varun.Security.EncryptionHandler" %> 
<%@ page import="com.varun.Logger.LoggerUtil"%>
<%@ page import="java.util.logging.*"%>

<html>
   <head> 
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script> 
         <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js" integrity="sha256-/H4YS+7aYb9kJ5OKhFYPUjSJdrtV6AeyJOtTkw6X72o=" crossorigin="anonymous"></script>
         <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
       
       <%  try{
           //<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
           System.out.println("-------------inside userpage------------------");
	       String sessioninfo="";
      	   UserDao dao=new UserDao();
	       UserinfoTableModel dataObj=null;
	       Integer userid=null;
	       String username=null;
	       String email=null;
	       
	       List<EmailTableModel> emailList=null;
	       List<MobileTableModel> mobileList=null;
	       if(request.getAttribute("userid")!=null && LRUCache.getThreadLocal()!=null){
	    	   if(LRUCache.getThreadLocal()!=null){
	    		   dataObj=LRUCache.getThreadLocal();
      	    	}
	    	    userid=(Integer)request.getAttribute("userid");
	    	    dataObj=(UserinfoTableModel)LRUCache.get("userid"+userid);
	    	    
	    	    if(dataObj==null){
	    	    	dataObj=new UserinfoTableModel();
	    	    }
	    	    
      	    	if(dataObj.getUser_name()==null){
      	    		 dataObj=dao.getUserById(userid);
	      	    	 System.out.println("userpage userDataObject from db");
	      	    	 LRUCache.put("userid"+userid,dataObj);
      	    	}else{
	  	    		 System.out.println("userpage userDataObject from cache "+LRUCache.getThreadLocal());
	  	    		 if(LRUCache.getThreadLocal()!=null){
		      	    	 System.out.println("userpage userDataObject from THreadlocal");
	  	    			 dataObj=LRUCache.getThreadLocal(); 
	  	    		 }else{
		      	    	 dataObj=(UserinfoTableModel)LRUCache.get("userid"+userid);
	  	    		 }
	      	    }
	       }
	       
	       if(dataObj!=null){
	    	    System.out.println("up databject not null");
	            int flag=0;
	            username=dataObj.getUser_name();
	       }else{
	    	   System.out.println("up to logout");
	    	   response.sendRedirect("servlet/log");
	       }
	       LRUCache.showCacheList();
           //request.setAttribute("dataobj",dataObj);
           //session.setAttribute("dataobj",dataObj);
           //response.setIntHeader("Refresh",1);
           response.setHeader("Expires","0");
       %>

       <link rel="stylesheet" href="/WebServlet/src/main/webapp/webfiles/NewFile.css">       
    </head>
   <body>
       <% 
          response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
       %>
       <div name="logout_container" style="display:flex;">
	       <h1> Welcome to the page,</h1><h1> <%out.println(username); %> </h1>  
	       <form action="servlet/Authentication/logout" method="post" style="padding-left:135vh; padding-top:50px;">
	          <button type="submit" style="height:30px;width:60px;">Logout</button>
	          <a href="profilepage.jsp">PROFILE</a> 
	       </form>           
       </div>
       
       <div name="chats" id="chats" style="display:flex;height:800px;">
	            <div name="list" style="width:200px;height:600px;">
		               <div style="display:flex;">
				           <h1>Chats</h1>
				           <button placeholder="create" style="height:40px;width:70px" id="cg">CREATE GROUP</button>
				           <br>
			           </div>
		           <div id="chat_list"></div>
		           
                </div>
           <div class="conv" style="width:800px;height:900px;">
	            <div Class="msg_form" style= "padding-top:10px;height:900px;">
		               <h2 id=1></h2>
		               <div id=2 Class="messages" style="height:60%;width:80%;overflow:scroll;"> </div>
		               <div></div>
		               <input id="chat_message" name="chat_message" style= "width:60% ;height:23px;" id="chat-writer" type="text">
		               <div id="send_btn"></div>
	             </div>
	       </div>
	       
	       <div id="create_group">
	                <h2 id="create_grp_header"></h2>
                    <div id="users_for_group"></div>
                    <div id="group_list"></div>
           </div>
            </div> 
           <script>
	           var createGroupObj = document.getElementById("create_group");
	           
	           function closeGroupCreation(){
	        	   createGroupObj.style.display="none";
	           }
	           function operGroupCreation(){
	        	   createGroupObj.style.display="block";
	           }
		     <% 
		        EncryptionHandler handler=new EncryptionHandler();
		        JSONObject json=new JSONObject();
		        json.put("userid",userid);
		        out.println("var uid="+userid+";");%>
		        $(document).ready(function(){
		           	$("#cg").click(function(){
		           		console.log(document.getElementById("create_grp_header"));
		           		var createGroupContainer=document.getElementById("create_grp_header").innerHTML;
		           		console.log(createGroupContainer);
		           		if(createGroupContainer!=""){
		           			operGroupCreation();
		           		}else{
		           			console.log("servlet called");
		           			<% out.println("$.get(\"servlet/GroupFormation?uid="+userid+"\",function(data){");%>
		           		     $("#create_grp_header").html("CREATE GROUP");
		           			 $("#users_for_group").html(data);
		           		    <% out.println("})");%> 
		           		}
		          	})  
		        }); 
                    
		        	$(document).ready(function(){
		            	/* setInterval(function(){
		            		 
		           		 
					     },3000);  */
					     $.ajax({
		               		 url:"servlet/chatlist", 
		               		 type:"POST",
		               		 data:JSON.stringify({userid:uid}),
		               		 success: function(result){
		               			$("#chat_list").html(result);
		               	     }
		               	});
			        });
		        	
		        	var interval;
		        	var offsetInMillis = new Date().getTimezoneOffset() * 60 * 1000;
		        	var receivername="";
		        	const timezone=Intl.DateTimeFormat().resolvedOptions().timeZone;
		        	function chat(sid,rid,isgroup,receiver){
		        		this.receivername=receiver;
		        		console.log(offsetInMillis+" "+Intl.DateTimeFormat().resolvedOptions().timeZone);
		        		/*clearInterval(interval); */
		        		$("#send_btn").html("<button onclick=\"sendmsg("+sid+","+rid+","+isgroup+",'"+receiver+"');\">send</button>");
		        		$(document).ready(function(){
		        			$("#1").html(receiver);
			            	/*interval=setInterval(function(){
			            		
			           			
			           		 },1000); */
			           		/* var jsondat=JSON.stringify({encrypted:encrypted});
			           		var encrypted = CryptoJS.AES.encrypt(
			           	            jsondat,"pass"
			           	         );
			           		console.log(encrypted);
			           		var decrypted = CryptoJS.AES.decrypt(
			           	            encrypted,"pass"
			           	         ).toString(CryptoJS.enc.Utf8);
			           		console.log(decrypted); */
		        			$.ajax({
			               		 url:"servlet/ShowMessages", 
			               		 type:"POST",
			               		 headers:{"auth":"secret"},
			               		 datatype:"json",
			               		 data:JSON.stringify({senderid:sid,recieverid:rid,groupyn:isgroup,timezone:timezone}),
			               		 success: function(result){
			               			$("#2").html(result);
			               	     }
			               	});
				        });
		        	}
	           	    
		        	function sendmsg(sid,rid,isgroup,reciever){
		        		var text=document.getElementById("chat_message").innerHTML;
		        		$(document).ready(function(){
		        			const text = $("#chat_message").val()+"";
		        			console.log(text+reciever);
		        			$("#chat_message").val('');
		        			$.ajax({
			               		 url:"servlet/sendmessage", 
			               		 type:"POST",
			               		 data:JSON.stringify({senderid:sid,recieverid:rid,groupyn:isgroup,text:text}),
			               		 success: function(result){

			               		  chat(sid,rid,isgroup,this.receivername);	
			               	     }
			              	});
                        });
		        	}
			</script>
			<%
			   System.out.println("up  nxt");
		       }catch(Exception e){
		    	   e.printStackTrace();
		       }
			%>
   </body>
 </html>