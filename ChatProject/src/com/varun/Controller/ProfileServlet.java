package com.varun.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Url;

import com.varun.Dao.*;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.EmailModel;
import com.varun.Model.MobileModel;
import com.varun.Model.UserModel;

/**
 * Servlet implementation class ProfileEdit
 */
public class ProfileServlet extends HttpServlet{
	private PrintWriter out=null;
	private static final Logger logger=Logger.getLogger(ProfileServlet.class.getName());
	private static final long serialVersionUID = 1L;
	 List<EmailModel> emailList=null;
     List<MobileModel> mobileList=null;
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
	    UserModel dataObj=null;
  	    UserDao dao=null;
		if(request.getAttribute("userid")!=null){
			userId=(Integer)request.getAttribute("userid");
			sessionId=(String)request.getAttribute("sessionid");
			dao=new UserDao(request);
			if(LRUCache.getThreadLocal()!=null){
				dataObj=LRUCache.getThreadLocal();
			}else if(LRUCache.get("userid"+userId)!=null){
				dataObj=(UserModel)LRUCache.get("userid"+userId);
			}else{
				dataObj=dao.getUserById(userId);
			}
			System.out.println("do not null"+dataObj);
		}else{
			System.out.println("do null"+request.getAttribute("dataobj"));
		}
		
	    UserModel updatedObj=new UserModel();
		String name=request.getParameter("username");
		String gender=request.getParameter("gender");
		String country=request.getParameter("country");
		String[] emailArr=request.getParameterValues("email[]");
		String[] mobileArr=request.getParameterValues("mobile[]");
		try{
			if(name!=null && !(dataObj.getUser_name().equals(name))){
					updatedObj.setUser_name(name);
			}
			if(country!=null && !(dataObj.getCountry().equals(country))){
					updatedObj.setCountry(country);
			}			
			if( gender!=null && !(dataObj.getGender().equals(gender))){
					updatedObj.setGender(gender);
			}	
			if(emailArr!=null){
				List<EmailModel> l=new ArrayList<EmailModel>();
				for(String email:emailArr){
					EmailModel ob=new EmailModel();
					ob.setEmailid(email);
					l.add(ob);
				}
				updatedObj.setEmailTableObj(l);
			}
			if(mobileArr!=null){
				List<MobileModel> l=new ArrayList<MobileModel>();
	            for(String mobile:mobileArr){
					MobileModel ob=new MobileModel();
					ob.setMobileno(Long.parseLong(mobile));
					l.add(ob);
				}
	            updatedObj.setMobileTableObj(l);
            }		
//          System.out.println(dataObj+" "+updatedObj);
            boolean isUpdated=false;
            isUpdated=dao.updateProfile(dataObj,updatedObj);
            
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
