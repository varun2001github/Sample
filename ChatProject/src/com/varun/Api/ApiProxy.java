//$Id$
package com.varun.Api;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ProtoModel.UserModel.User;
import com.google.protobuf.Message;
import com.varun.Api.CacheImpl.*;
import com.varun.Api.Interface.CachableUserApi;
import com.varun.Api.Interface.EmailApi;
import com.varun.Api.Interface.MobileApi;
import com.varun.Api.Interface.NonCachable;
import com.varun.Api.Interface.PasswordApi;
import com.varun.Api.Interface.SessionApi;
import com.varun.Api.Interface.UserApi;
import com.varun.Model.AuditModel;
import com.varun.Model.DataObject;
import com.varun.Orm.OrmImp;
import com.varun.cache.LRUCache;
import com.varun.cache.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ApiProxy{
     
	private static UserApi userApi=null;
	ProxyCacheApi proxyCacheApi=null;
	
    public ApiProxy(){
    }
    
    public static ApiProxy getInstance(){
    	return new ApiProxy();
    }
    
    public class ProxyHandler implements InvocationHandler{
		
		Object target;
		
		public ProxyHandler(Object target){
			this.target=target;
	    	proxyCacheApi=ProxyCacheApi.getInstance();
		}
		
		public ProxyHandler(){
		}

	    public Object invoke(Object proxy,Method method,Object[] args) throws IllegalAccessException,InvocationTargetException{
	    	System.out.println(method.getName());
	    	return ProxyInvokeApi(target,method,args);
	    }
	}
    
    public static final Map<String,Object> cacheImplMap=getCacheImplMap();
    
    public static Map<String,Object> getCacheImplMap(){
    	Map<String,Object> cacheImplMap=new ConcurrentHashMap<String,Object>();
    	cacheImplMap.put("CachableUserApi",new UserApiCacheImpl());
    	cacheImplMap.put("CachableEmailApi",new EmailApiCacheImpl());
    	cacheImplMap.put("CachableMobileApi",new MobileApiCacheImpl());
    	cacheImplMap.put("CachablePasswordApi",new PasswordApiCacheImpl());
    	cacheImplMap.put("CachableSessionApi",new SessionApiCacheImpl());
        return cacheImplMap;
    }

    private Object ProxyInvokeApi(Object target,Method method,Object[] args){
    	Object result=null;
    	boolean cachable=isCachableMethod(method);
    	System.out.println("cachability->"+cachable);
    	
		try{
			//if cachable method
	    	if(cachable){
	    		 Object newInstance=cacheImplMap.get(method.getDeclaringClass().getSimpleName());
	    		 //get from cache
                 result=method.invoke(newInstance,args);
 	    	     return result;
	    	}
	    	System.out.println("from method");
		    return method.invoke(target, args);
	    	
		}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public static boolean isCachableMethod(Method method){
		if(NonCachable.class.isAssignableFrom(method.getDeclaringClass())){
			//if belongs to NonCachable
			return false;
		}else{
			return true;
		}
    }
    
	public UserApi getUserApi(){
		return (UserApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
	             ,new Class[]{UserApi.class},new ProxyHandler(new UserApiImpl()));
	}
	
	public EmailApi getEmailApi(){
		return (EmailApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
		         ,new Class[]{EmailApi.class},new ProxyHandler(new EmailApiImpl()));
	}

	public MobileApi getMobileApi(){
	    return (MobileApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
	         ,new Class[]{MobileApi.class},new ProxyHandler(new MobileApiImpl()));
	}

	public SessionApi getSessionApi(){
	    return (SessionApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
	         ,new Class[]{SessionApi.class},new ProxyHandler(new SessionApiImpl()));
	}

	public PasswordApi getPasswordApi(){
	    return (PasswordApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
	         ,new Class[]{PasswordApi.class},new ProxyHandler(new PasswordApiImpl()));
	}
	
	//Test
	public static void main(String[] args){
		UserApi user=ApiProxy.getInstance().getUserApi();
		user.getUser(1);
//		user.addUser(User.newBuilder().build());
	}
}
