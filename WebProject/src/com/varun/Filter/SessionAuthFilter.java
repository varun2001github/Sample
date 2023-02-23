package com.varun.Filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.Controller.ChatList;
import com.varun.Dao.AuthDao;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.UserinfoTableModel;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter(filterName = "MyFilter", urlPatterns = {"/sendmessage","/userpage.jsp","/ShowMessages","/chatlist","/profileEdit.jsp"})
public class SessionAuthFilter implements Filter {
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

    /**
     * Default constructor. 
     */
    public SessionAuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        logger.log(Level.INFO,"Servlet Filter");
        System.out.println("filter__");
        String sessioninfo="";
        Integer userid=null;
        String username=null;
        int sessionFlag=0,dataobjFlag=0;
        boolean isSessionValid=false;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session=httpRequest.getSession();
		UserinfoTableModel userObj =null;
		try{
			Cookie[] cookies=httpRequest.getCookies();
			if(cookies!=null){
			       for(Cookie c:cookies){
			    	    //fetch session id from cookie
				       	if(c.getName().equals("userdata")){
				       		String encodedString=c.getValue();
				            String jsonString = URLDecoder.decode(encodedString, "UTF-8");
							JSONObject js=new JSONObject(jsonString);
                            userid=js.getInt("userid");
                            username=js.getString("username");
                        }
				       	if(c.getName().equals("sessionid") && sessionFlag==0){
				       		sessionFlag=1;
				       		sessioninfo=c.getValue();
                        }
				    }
			        if(sessionFlag==1){
			       	    AuthDao dao=new AuthDao();
			       	    userObj=new UserinfoTableModel();
			       	    userObj.setUser_id(userid);
			       	    userObj.setUser_name(username);
			       	    isSessionValid=dao.checkSession(userid,sessioninfo);
			       	    if(isSessionValid && userObj!=null){
			       	    	System.out.println("session valid in filter");
					  		request.setAttribute("dataobj",userObj);
		            		chain.doFilter(request, response);
		            	}else{
			       	    	RequestDispatcher rd = httpRequest.getRequestDispatcher("/log");
			      			rd.forward(request, response);
			       	    }
					}
			}
			
		 }catch(Exception e){
 	        logger.log(Level.WARNING,"unexpected",e);
     	 }
		 if(sessionFlag==0){
    	    RequestDispatcher rd = httpRequest.getRequestDispatcher("/log");
  			try {
				rd.forward(request, response);
			}catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
	 	        logger.log(Level.WARNING,"servlet",e);
			}
         }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
