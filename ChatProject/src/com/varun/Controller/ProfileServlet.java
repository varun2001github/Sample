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
import com.varun.Model.EmailTableModel;
import com.varun.Model.MobileTableModel;
import com.varun.Model.UserinfoTableModel;

/**
 * Servlet implementation class ProfileEdit
 */
public class ProfileServlet extends HttpServlet{
	private PrintWriter out=null;
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	private static final long serialVersionUID = 1L;
	 List<EmailTableModel> emailList=null;
     List<MobileTableModel> mobileList=null;
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
		
		Integer userId=null;
		String sessionId=null;
		HttpSession session=request.getSession();
	    UserinfoTableModel dataObj=null;
  	    UserDao dao=null;
		if(request.getAttribute("userid")!=null){
			userId=(Integer)request.getAttribute("userid");
			sessionId=(String)request.getAttribute("sessionid");
			dao=new UserDao(request);
			if(LRUCache.getThreadLocal()!=null){
				dataObj=LRUCache.getThreadLocal();
			}else if(LRUCache.get("userid"+userId)!=null){
				dataObj=(UserinfoTableModel)LRUCache.get("userid"+userId);
			}else{
				dataObj=dao.getUserById(userId);
			}
			System.out.println("do not null"+dataObj);
		}else{
			System.out.println("do null"+request.getAttribute("dataobj"));
		}
		
	    UserinfoTableModel updatedObj=new UserinfoTableModel();
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
				List<EmailTableModel> l=new ArrayList<EmailTableModel>();
				for(String email:emailArr){
					EmailTableModel ob=new EmailTableModel();
					ob.setEmailid(email);
					l.add(ob);
				}
				updatedObj.setEmailTableObj(l);
			}
			if(mobileArr!=null){
				List<MobileTableModel> l=new ArrayList<MobileTableModel>();
	            for(String mobile:mobileArr){
					MobileTableModel ob=new MobileTableModel();
					ob.setMobileno(Long.parseLong(mobile));
					l.add(ob);
				}
	            updatedObj.setMobileTableObj(l);
            }		
//          System.out.println(dataObj+" "+updatedObj);
            boolean isUpdated=false;
            logger.log(Level.INFO,"updateProfile called ----------------------------------");
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
