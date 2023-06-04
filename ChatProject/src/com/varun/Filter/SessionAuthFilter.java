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

import com.ProtoModel.UserModel.Session;
import com.ProtoModel.UserModel.User;
import com.varun.Dao.LRUCache;
import com.varun.Dao.UserDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Servlet Filter implementation class SessionFilter "/profilepage.jsp"
 */
//@WebFilter(filterName = "MyFilter", urlPatterns = {"/sendmessage","/profilepage.jsp","/editprofile","/userpage.jsp","/ShowMessages","/chatlist"})
public class SessionAuthFilter implements Filter{
	private static final Logger logger=Logger.getLogger(SessionAuthFilter.class.getName());
    static JedisPool jpool=new JedisPool("localhost", 6379);
    private Jedis jedis=null;
    /**
     * Default constructor. 
     */
    public SessionAuthFilter(){
        // TODO Auto-generated constructor stub
		jedis = jpool.getResource();
    }
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("session auth filter"+ jedis.get("a"));
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
			        	User userObject=null;
			        	Session sessionObject=null;
			        	
			        	if(userObject==null){
			        		userObject=User.getDefaultInstance();
			        	}
			        	
                        //Get Session Object from redis/db
			       	    if(jedis.get(sessionid.getBytes())!=null){
			       	    	System.out.println("---session validate from redis cache---");
			       	    	sessionObject=Session.parseFrom(jedis.get(sessionid.getBytes()));
			       	    }else{
			       	    	System.out.println("---session validate from DB---");
			       	    	sessionObject=dao.getSessionObject(sessionid);
			       			jedis.set(sessionid.getBytes(),sessionObject.toByteArray());
			       	    }
			       	    
			       	    //if session valid
			       	    if(sessionObject!=null){
							System.out.println("cookie ses valid");
	
//                            //get basic user object from redis/db
//			       	    	if(jedis.get(("userid"+sessionObject.getUserId()).getBytes())!=null){
//				       	    	System.out.println("filter ud frm cache");
//				       	    	userObject=UserinfoModel.parseFrom( jedis.get(("userid"+sessionObject.getUserId()).getBytes()) );
//			       	    	}else{
//				       	    	System.out.println("filter ud frm db");
//				       	    	jedis.set(("userid"+sessionObject.getUserId()).getBytes(),userObject.toByteArray());
//			       	    	}
			       	    	userObject=dao.getUserById(sessionObject.getUserId());

			       	    	LRUCache.setThreadLocal(userObject);
			       	    	httpRequest.setAttribute("userid",sessionObject.getUserId());
			       	    	httpRequest.setAttribute("sessionid",sessionid);
			       	    	redirectFlag=1;
			       	    	//sd
			       	    	jedis.disconnect();
//			       	    	jedis.close();
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
	 * @see Filter#destroy()
	 */
	public void destroy(){
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	*/
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException{
		// TODO Auto-generated method stub
	}

}
