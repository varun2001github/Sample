package com.varun.Filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ProtoModel.UserModel.SessionModel;
import com.ProtoModel.UserModel.UserinfoModel;
import com.varun.Dao.LRUCache;
import com.varun.Dao.UserDao;

/**
 * Servlet Filter implementation class SessionFilter "/profilepage.jsp"
 */
//@WebFilter(filterName = "MyFilter", urlPatterns = {"/sendmessage","/profilepage.jsp","/editprofile","/userpage.jsp","/ShowMessages","/chatlist"})
public class SessionAuthFilter implements Filter{
	private static final Logger logger=Logger.getLogger(SessionAuthFilter.class.getName());

    /**
     * Default constructor. 
     */
    public SessionAuthFilter(){
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy(){
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("session auth filter");
        logger.log(Level.INFO,"Servlet Filter");
        String sessionid=null;
        int redirectFlag=0;
		HttpServletRequest httpRequest =(HttpServletRequest)request;
		HttpServletResponse httpResponse =(HttpServletResponse)response;
        System.out.println("sess auth filter"+httpRequest.getRequestURL());
		try{
			Cookie[] cookies=httpRequest.getCookies();
			if(cookies!=null){
			        for(Cookie c:cookies){
			    	   if(c.getName().equals("sessionid")){
				       		sessionid=c.getValue();
				       		System.out.println("cookie avl"+sessionid);
		                }
				    }
			       
			        if(sessionid!=null){
			        	UserDao dao=new UserDao();
//			        	UserinfoModel.Builder userBuilder=null;
			        	UserinfoModel userObject=null;
			        	SessionModel sessionObject=null;
			        	
			        	if(userObject==null){
			        		userObject=UserinfoModel.getDefaultInstance();
			        	}
			        	
                        //Get Session Object 
			        	
			       	    if(LRUCache.get(sessionid)!=null){
			       	    	System.out.println("---session validate from cache---");
			       	    	sessionObject=(SessionModel)LRUCache.get(sessionid);
			       	    }else{
			       	    	System.out.println("---session validate from DB---");
			       	    	sessionObject=dao.getSessionObject(sessionid);
			       	    	LRUCache.put(sessionid,sessionObject);
			       	    }
			       	    
			       	    //if session valid
			       	    if(sessionObject!=null){
							System.out.println("cookie ses valid");

			       	    	//add sessionObject to cache
			       	    	LRUCache.put(sessionid,sessionObject);
			       	    	
                            //get basic user object including Username
			       	    	if(LRUCache.get("userid"+sessionObject.getUserId())!=null){
				       	    	System.out.println("filter ud frm cache");
				       	    	userObject=(UserinfoModel)LRUCache.get("userid"+sessionObject.getUserId());
			       	    	}else{
				       	    	System.out.println("filter ud frm db");
				       	    	userObject=dao.getUserById(sessionObject.getUserId());
			       	    		LRUCache.put("userid"+sessionObject.getUserId(),userObject);
			       	    	}
			       	    	LRUCache.setThreadLocal(userObject);
			       	    	httpRequest.setAttribute("userid",sessionObject.getUserId());
			       	    	httpRequest.setAttribute("sessionid",sessionid);
			       	    	redirectFlag=1;
		            		chain.doFilter(request, response);
		            	}else{
		            		redirectFlag=1;
			       	    	RequestDispatcher rd = httpRequest.getRequestDispatcher("/servlet/Authentication/logout");
			      			rd.forward(request, response);
			       	    }
					}
			}else{
				System.out.println("cookie not avl");
			}
			
		 }catch(Exception e){
 	        logger.log(Level.WARNING,"unexpected",e);
     	 }
		 if(redirectFlag==0) {
			RequestDispatcher rd = httpRequest.getRequestDispatcher("/servlet/Authentication/logout");
			rd.forward(request, response);
		 }
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException{
		// TODO Auto-generated method stub
	}

}
