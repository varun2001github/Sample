//$Id$
package com.varun;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Properties;

import com.ProtoModel.UserModel.EmailModel;
import com.ProtoModel.UserModel.UserinfoModel;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
//import com.varun.MyClass;
//import com.varun.MyClassInterface;
//import com.varun.ProxyHandler;
import com.varun.Dao.LRUCache;
import com.varun.Orm.OrmImp;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ApiProxy{
     
	private static UserApi userApi=null;
	private JedisPool jpool=null;
    private  Jedis jedis=null;
    private static final HashMap<String,MethodAttribute> cachableMethodMap=getprotoInstanceMap();
    
	private static final HashMap<String,MethodAttribute> getprotoInstanceMap(){
		Parser m=UserinfoModel.parser();
    	HashMap<String,MethodAttribute> protoParserMap=new HashMap<String,MethodAttribute>();  
    	protoParserMap.put("com.varun.Api.UserApi#getUser",new MethodAttribute(UserinfoModel.parser(),true,true));
    	protoParserMap.put("com.varun.Api.UserApi#fetchChatList",new MethodAttribute(UserinfoModel.parser(),true,true));
		return protoParserMap;
	}
	
    public ApiProxy(){
    	this.jpool=new JedisPool("localhost", 6379);
    	this.jedis=jpool.getResource();
    }
    
    static ApiProxy getInstance() {
    	return new ApiProxy();
    }
    
    public class ProxyHandler implements InvocationHandler{
		
		Object target;
		
		public ProxyHandler(Object target){
			this.target=target;
		}
		
		public ProxyHandler(){
		}

	    public Object invoke(Object proxy,Method method,Object[] args) throws IllegalAccessException, InvocationTargetException {
	    	return proxyInvoke(target,proxy,method,args);
		}
	}
    
    private String generateCacheKey(Method method, Object[] args){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getName());
        if(args != null){
            for(Object arg : args){
               keyBuilder.append(":").append(arg);
            }
        }
        return keyBuilder.toString();
    }
    
    private Object proxyInvoke(Object target,Object proxy,Method method,Object[] args){
    	Object returnValue=null;
		byte[] serialized=null;
		System.out.println(method.getDeclaringClass().getName()+method.getName()+method.getReturnType()+"------"+method.getGenericReturnType()
		.getTypeName());
		
		//for methods performing select operation
		if(cachableMethodMap.containsKey(method.getDeclaringClass().getName()+"#"+method.getName())){
			
			     MethodAttribute methodAttribute=cachableMethodMap.get(method.getDeclaringClass().getName()+"#"+method.getName());
			     String cacheKey=generateCacheKey(method,args);
				 
				 //fetch from in-memory LRUcache
				 returnValue=LRUCache.get(cacheKey);
				 						 
				 if(returnValue==null && methodAttribute.getRedisStatus()==true){
					 
					 //fetch from redis cache
					 if(jedis.exists(cacheKey.getBytes())){
						    
						    serialized=jedis.get(cacheKey.getBytes());
						    
						 	try{
								returnValue=UserinfoModel.parseFrom(serialized);
							}catch(InvalidProtocolBufferException e1){
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}								 
						    System.out.println("from redis cache--");
					 	    
					 //fetch from DB	 
					 }else{
						    try{
								returnValue=method.invoke(target,args);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    String methodReturnType=method.getReturnType().getSimpleName();
						    System.out.println("from method -----:");
						    
						    //store to Redis cache
                         	jedis.set(cacheKey.getBytes(),((Message)returnValue).toByteArray());
						    //store to in-memory cache
						    LRUCache.put(cacheKey,returnValue);
					 }
					 jedis.close();
				 }
		}else{
			//for methods performing insert,update or delete
			try{
				returnValue=method.invoke(target,args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnValue;
    }
    
	public UserApi getUserApi(){
		return (UserApi)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
	             ,new Class[]{UserApi.class},new ProxyHandler(new UserApiImpl()));
	}
	
	//Test
	public static void main(String[] args){
		UserApi user=ApiProxy.getInstance().getUserApi();
		System.out.println(user.fetchChatList(1).get(0).getUserName());  
//		System.out.println(user.fetchChatList(1).get(0).getUserName());
//		System.out.println(user.getUser(1).getUserName());
		/* 
		  Properties props=new Properties();
		  InputStream in=null;
		  try{
		  in =(InputStream)new FileInputStream("/home/local/ZOHOCORP/varun-pt6303/ZIDE Workspace/ChatProject/ApiClass.properties");
	      props.load(in);
		
	}catch(Exception e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Map<Object,Object> h=(Map<Object,Object>)props;
	System.out.println(h.get("name"));
	*/
	}
}

//private static final HashMap<String,Parser> protoParserMap=getprotoInstanceMap();
//
//private static HashMap<String,Parser> getprotoInstanceMap(){
//	Parser m=UserinfoModel.parser();
//	HashMap<String,Parser> protoParserMap=new HashMap<String,Parser>();
//	protoParserMap.put("com.ProtoModel.UserModel$UserinfoModel",UserinfoModel.parser());
//	return protoParserMap;
//}