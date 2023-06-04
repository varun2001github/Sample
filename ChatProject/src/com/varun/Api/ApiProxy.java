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
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ProtoModel.UserModel.Email;
import com.ProtoModel.UserModel.Mobile;
import com.ProtoModel.UserModel.Password;
import com.ProtoModel.UserModel.Session;
import com.ProtoModel.UserModel.User;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.varun.Api.Interface.CachableApi;
import com.varun.Api.Interface.CachableUserApi;
import com.varun.Api.Interface.EmailApi;
import com.varun.Api.Interface.MobileApi;
import com.varun.Api.Interface.PasswordApi;
import com.varun.Api.Interface.SessionApi;
import com.varun.Api.Interface.UserApi;
//import com.varun.MyClass;
//import com.varun.MyClassInterface;
//import com.varun.ProxyHandler;
import com.varun.Dao.LRUCache;
import com.varun.Model.AuditModel;
import com.varun.Model.DataObject;
import com.varun.Orm.OrmImp;
import com.varun.Orm.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ApiProxy{
     
	private static UserApi userApi=null;
    private  Jedis redisUtil=null;
	
    public ApiProxy(){
    }
    
    public static ApiProxy getInstance(){
    	return new ApiProxy();
    }
    
    private boolean isCachableMethod(Method method){
    	try{
	        CachableApi.class.getMethod(method.getName(), method.getParameterTypes());
            return true;
    	}catch(NoSuchMethodException e) {
    		return false;
    	}
    }
    
    public class ProxyHandler implements InvocationHandler{
		
		Object target;
		
		public ProxyHandler(Object target){
			this.target=target;
		}
		
		public ProxyHandler(){
		}

	    public Object invoke(Object proxy,Method method,Object[] args) throws IllegalAccessException,InvocationTargetException{
	    	
	 	    if(isCachableMethod(method)){
	    		return cachableProxyInvoke(target,method,args);
	    	}
		    return method.invoke(target, args);
		}
	}
    
    private String getMethodCacheKey(Method method, Object[] args){
    	StringBuilder keyBuilder = new StringBuilder();
    	
        if(method.getReturnType()!=List.class){
            keyBuilder.append(method.getReturnType().getName());
        }else {
        	keyBuilder.append(method.getGenericReturnType().getTypeName());
        }
        
        if(args != null){
            for(Object arg : args){
               keyBuilder.append(":").append(arg);
            }
        }
        
        return keyBuilder.toString();
    }
        
    private Object cachableProxyInvoke(Object target,Method method,Object[] args){
    	Class<?> returnType=method.getReturnType();
    	Object returnValue=null;
    	RedisUtil redisUtil=null;
		byte[] serialized=null;
		
		redisUtil=RedisUtil.getInstance();
		     
		String methodCacheKey=getMethodCacheKey(method,args);

		 //fetch from in-memory LRUcache
//		 returnValue=LRUCache.get(cacheKey);
//		 						 
//		 if(returnValue!=null){
//			 return returnValue;
//		 }
		 
		 //check and fetch from cache
		 //list<>
		 if(returnType==List.class){
			 
			 Class<?> protoClassType=getListGenericType(method);
			 if(redisUtil.exists(methodCacheKey)){
				 List<byte[]> serializedList=redisUtil.getList(methodCacheKey);
				 List<Object> list=new ArrayList<Object>();
				 for(byte[] bytes:serializedList){
					 list.add(deserializeProto(protoClassType,bytes));
				 }
				 returnValue=list;
			 }
			 
			 
		 //protoobject
		 }else if(Message.class.isAssignableFrom(returnType)){
			 //fetch from redis cache
			 if(redisUtil.exists(methodCacheKey)){
				    serialized=redisUtil.getObject(methodCacheKey);
					returnValue=deserializeProto(returnType,serialized);
			 }
		 }
		 
		 if(returnValue!=null){
			 System.out.println("from redis cache--");
			 return returnValue;
		 }
		 
		 //fetch from DB	 
	     try{
			returnValue=method.invoke(target,args);
		 }catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	     	     
	     if(returnValue!=null){
	    	 
			    //store to Redis cache
			    if(returnType==List.class ){
			    	if(((List<Object>)returnValue).get(0) instanceof Message){
			    		List<Message> list=(List<Message>)returnValue;
				    	for(Message message:list){
				    		redisUtil.rpush(methodCacheKey, message.toByteArray());
				    	}
			    	}
			    }else if(returnValue instanceof Message){
			    	Message protoObject=(Message)returnValue;
					
			    	//store to Redis cache
	             	redisUtil.setObject(methodCacheKey,protoObject.toByteArray());
			    }
		    }
		    System.out.println("from method -----:");
			    
		    //store to in-memory cache
            //LRUCache.put(cacheKey,returnValue);
	
		    return returnValue;

	}
    
    public Class<?> getListGenericType(Method method){
    	Type returnType = method.getGenericReturnType();
    	Class<?> objectType=null;
        // Check if the return type is a parameterized type
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length > 0){
                Type typeArgument = typeArguments[0];

                if (typeArgument instanceof Class) {
                    objectType = (Class<?>) typeArgument;
                }
            }
        }
		return objectType;
    }
    
    private static Object deserializeProto(Class<?> protoClass,byte[] serialized) {
    	try{
            Method method = protoClass.getMethod("parseFrom",byte[].class);
            if(method.isAccessible()==false){
            	method.setAccessible(true);
            }
            return method.invoke(null,serialized);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
        	e.printStackTrace();
        	return null;
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
		System.out.println(user.getUser(1).getUserName());
		System.out.println(user.getUser(1).getUserName());

//		System.out.println(user.fetchChatList(1).get(0).getUserName());
//		System.out.println(user.fetchChatList(1).get(1).getUserName());
		
//		EmailApi api=ApiProxy.getInstance().getEmailApi();
//		System.out.println(api.getEmailById(id));
//		System.out.println(api.getEmailById(1).get(0).getEmailid());
//		System.out.println(api.getEmailById(1).get(0).getEmailid());

		
	}
}
























/*
 * if primitives(non void , non primitives ,
 *     no cache
 * else if(not list<>){
 *      if(proto){
 *           cache
 *      }else{
 *          serialize and cache
 *      }
 * else{
 *     (methodname+args)=CSV(keys) 
 *   }
 * */



/*	public EmailApi getEmailApi(){
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
*/	

/*Object result=null;
		byte[] serialized=null;
		boolean isCachable=cachableMethods.contains(method.getDeclaringClass().getName()+"#"+method.getName());
        Parser<User> parser=User.parser();
	    String methodReturnType=method.getGenericReturnType().getTypeName();
	    String UserObjectCacheKey="User",UserlistMethodCacheKey=generateCacheKey(method,args);
		
		if(isCachable){
	    	     jedis=RedisConnection.getConnection();
//               jedis.flushDB();

				 //fetch from in-memory LRUcache
//				 result=LRUCache.get(cacheKey);
				 
//		         Set<String> keys = jedis.keys("*");
//		         Iterator<String> namesIterator = keys.iterator();
//		         while(namesIterator.hasNext()){
//		        	   System.out.println("cache key ==> "+namesIterator.next());
//		         }		
	    	     
				 if(result==null){
					 
					 //fetch from redis cache (fetch User object or List<User>
					 if(jedis.exists((UserObjectCacheKey+args[0]).getBytes()) || jedis.exists(UserlistMethodCacheKey)){
					    	List<Object> ObjectList=new ArrayList<Object>();

						    Class<?> returnType=method.getReturnType();
						   
						    //convert List<byte> to List<Object>
						    if(methodReturnType.equals("java.util.List<com.ProtoModel.UserModel$User>")){
						    	String CSVIds=jedis.get(UserlistMethodCacheKey);
						    	if(!CSVIds.isEmpty() || CSVIds!=null){
						    		String[] ids=CSVIds.split(",");
							    	for(String id:ids){
							    		if(jedis.exists((UserObjectCacheKey+id).getBytes())){
											try {
												ObjectList.add(parser.parseFrom(jedis.get(UserObjectCacheKey+id).getBytes()));
											}catch(InvalidProtocolBufferException e){
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
							    		}else{
							    			User user=new UserApiImpl().getUser(Integer.parseInt(id));
							    			ObjectList.add(user);
							    			jedis.set((UserObjectCacheKey+id).getBytes(),user.toByteArray());
	 						    		}
							    	}
							    	result=ObjectList;
						    	}
						    	
						    }else{
							    serialized=jedis.get((UserObjectCacheKey+args[0]).getBytes());
						    	try{
									result=parser.parseFrom(serialized);
								}catch(InvalidProtocolBufferException e1){
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						    }
						    System.out.println("from redis cache--");
					 	    
					 //fetch from DB	 
					 }else{
							try{
								result=method.invoke(target,args);
							}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    
							if(result!=null){
								System.out.println("from method -----:");
							    
							    //store to Redis cache
							    if(methodReturnType.equals("java.util.List<com.ProtoModel.UserModel$User>")){
							    	
							    	List<User> list=(List<User>)result;
							    	String ids="";
							    	
							    	// check protoobject or not
						    		for(int i=0;i<list.size();i++){
						    			if(list.get(i) instanceof User){
						    				User user=list.get(i);
						    				ids+=user.getUserId();
						    				if(i<list.size()-1){
						    					ids+=",";
						    				}
						    				if(!jedis.exists((UserObjectCacheKey+user.getUserId()).getBytes())) {
												jedis.set((UserObjectCacheKey+user.getUserId()).getBytes(),user.toByteArray());
						    				}
						    			}
									}
				    				if(!jedis.exists(UserlistMethodCacheKey)){
							    		jedis.set(UserlistMethodCacheKey,ids);
				    				}
									
							    }else if(result instanceof User){
							    	User user=(User)result;
		                         	jedis.set( (UserObjectCacheKey+user.getUserId()).getBytes(),user.toByteArray());
							    }
						        
							    //store to in-memory cache
//							    LRUCache.put(cacheKey,result);
							}
					 }
//	                 jedis.flushDB();
				     jedis.disconnect();
				     jedis.close();
				 }
		}else{
			// For uncachable methods
			try{
				result=method.invoke(target,args);
			}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return result;*/

