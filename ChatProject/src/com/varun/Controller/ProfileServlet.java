package com.varun.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ProtoModel.UserModel.User;
import com.ProtoModel.UserModel.Email;
import com.ProtoModel.UserModel.Mobile;
import com.varun.Dao.*;

/**
 * Servlet implementation class ProfileEdit
 */
public class ProfileServlet extends HttpServlet{
	private PrintWriter out=null;
	private static final Logger logger=Logger.getLogger(ProfileServlet.class.getName());
	private static final long serialVersionUID = 1L;
	 List<Email> emailList=null;
     List<Mobile> mobileList=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet(){
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	//		String userid=request.getParameter("uid");
	//		String username=request.getParameter("username");
        logger.log(Level.INFO,"updateProfile called ----------------------------------");
		Integer userId=null;
		String sessionId=null;
		HttpSession session=request.getSession();
	    User userObject=null;
  	    UserDao dao=null;
		if(request.getAttribute("userid")!=null){
			userId=(Integer)request.getAttribute("userid");
			sessionId=(String)request.getAttribute("sessionid");
			dao=new UserDao(request);
			
			if(LRUCache.getThreadLocal()!=null){
				userObject=LRUCache.getThreadLocal();
			}else if(LRUCache.get("userid"+userId)!=null){
				userObject=(User)LRUCache.get("userid"+userId);
			}else{
				userObject=dao.getUserById(userId);
			}
			
			System.out.println("do not null"+userObject);
		}else{
			System.out.println("do null"+request.getAttribute("dataobj"));
		}
		
		User updatedUserObject=null;
		User.Builder userObjBuilder=User.newBuilder();
	    
		String name=request.getParameter("username");
		String gender=request.getParameter("gender");
		String country=request.getParameter("country");
		String[] emailArr=request.getParameterValues("email[]");
		String[] mobileArr=request.getParameterValues("mobile[]");
		try{
			if(name!=null && !(userObject.getUserName().equals(name))){
					userObjBuilder.setUserName(name);
			}
			if(country!=null && !(userObject.getCountry().equals(country))){
					userObjBuilder.setCountry(country);
			}			
			if( gender!=null && !(userObject.getGender().equals(gender))){
					userObjBuilder.setGender(gender);
			}
			
			if(emailArr!=null){
				List<Email> emailObjList=new ArrayList<Email>();
				for(String email:emailArr){
					Email.Builder emailBuilder=Email.newBuilder();
					emailBuilder.setEmailid(email);
					emailObjList.add(emailBuilder.build());
				}
				userObjBuilder.addAllEmailObject(emailObjList);
			}
			
			if(mobileArr!=null){
				List<Mobile> mobileObjList=new ArrayList<Mobile>();
	            for(String mobile:mobileArr){
	            	Mobile.Builder mobileBuilder=Mobile.newBuilder();
					mobileBuilder.setMobileno(Long.parseLong(mobile));
					mobileObjList.add(mobileBuilder.build());
				}
	            userObjBuilder.addAllMobileObject(mobileObjList);
            }	
			updatedUserObject=userObjBuilder.build();
			
//          System.out.println(dataObj+" "+updatedObj);
            boolean isUpdated=false;
            isUpdated=dao.updateProfile(userObject,updatedUserObject);
            
            //clear cache
            LRUCache.remove("userid"+request.getAttribute("userid"));
            
            System.out.println(" UPDATE STAT :"+isUpdated);
            if(isUpdated){
	    		session.setAttribute("updateerr","UPDATED");
            }else{
    			session.setAttribute("updateerr","NOT UPDATED");
            }
		}catch(Exception e){
			e.printStackTrace();
			session.setAttribute("updateerr","NOT UPDATED");
		}
		response.sendRedirect("/ChatProject/profilepage.jsp");
	}
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
//		String operation=(String)request.getParameter("operation");
//		System.out.println(operation);
//		switch(operation) {
//		  case "login":
//			  getWeatherData(request,response);
//			  break;
//		  
//		}
//	}
	
//	protected void getWeatherData(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
//		String lat=request.getParameter("latitude");
//		String lon=request.getParameter("longitude");
//		if(lat!=null && lon!=null){
//			String WeatherUrl="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=43b81b5911427aa18abe13dfa021e861";
//		    URL url=new URL(WeatherUrl);
//		    HttpURLConnection con=(HttpURLConnection)url.openConnection();
//		    con.setRequestMethod("GET");
//	        StringBuilder strBuf = new StringBuilder();  
//		    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
//            String output = null;  
//            while ((output = br.readLine()) != null)  
//                strBuf.append(output);
//		    System.out.println(strBuf.toString());
//		}
//		
//	}
}
