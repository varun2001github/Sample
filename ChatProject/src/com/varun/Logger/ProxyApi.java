//$Id$
package com.varun.Logger;

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
import com.varun.Api.ApiProxy;
import com.varun.Api.UserApiImpl;
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

public class ProxyApi{
     
	private static UserApi userApi=null;
    private  Jedis redisUtil=null;
    
    private static final HashMap<String,String> protoPrimaryKey=getPrimaryKey();
    
    private static HashMap<String,String> getPrimaryKey(){
    	HashMap<String,String> protoPrimaryKey=new HashMap<String,String>();
    	protoPrimaryKey.put("com.varun.Api.UserApiImpl","user_id");
    	protoPrimaryKey.put("com.varun.Api.EmailApiImpl","emailid");
    	protoPrimaryKey.put("com.varun.Api.MobileApiImpl","mobileno");
    	protoPrimaryKey.put("com.varun.Api.PasswordApiImpl","user_id");
    	protoPrimaryKey.put("com.varun.Api.SessionApiImpl","session_id");
    	return protoPrimaryKey;
    }
    
//  private static final HashSet<String> cachableMethods=getCachableMethods();
//    
//	private static final HashSet<String> getCachableMethods(){
//		HashSet<String> cachableMethods=new HashSet<String>();  
//		cachableMethods.add("com.varun.Api.Interface.UserApi#getUser");  
//		cachableMethods.add("com.varun.Api.Interface.UserApi#fetchChatList");  		
//		return cachableMethods;
//	}
	
    public ProxyApi(){
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
	    	System.out.println(method.getGenericReturnType().getTypeName());
	    	System.out.println(isCachableMethod(method));
	 	    if(isCachableMethod(method)){
//	    		return cachableProxyInvoke(target,method,args);
	    	}
	 	    System.out.println("----> "+getListGenericType(method).getName());
	 	    System.out.println(target.getClass().getName());
//		    return method.invoke(target, args);
		    return null;
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
    
    public Class<?> getListGenericType(Method method){
    	Type returnType = method.getGenericReturnType();
    	Class<?> objectType=null;
        // Check if the return type is a parameterized type
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length > 0) {
                Type typeArgument = typeArguments[0];

                if (typeArgument instanceof Class) {
                    objectType = (Class<?>) typeArgument;
                    System.out.println("Return type of the method: " + objectType.getName());
                }
            }
        }
		return objectType;
    }
//    private String getProtoCacheKey(Method method){
//    	Class<?> returnType=method.getReturnType();
//    	 String protoUniqueVariable=protoPrimaryKey.get(method.getDeclaringClass().getName());
//		 Object UniqueId=protoObject.getField(protoObject.getDescriptorForType().findFieldByName(protoUniqueVariable));
//        
//    	return
//    	
//    }
    
    private Object cachableProxyInvoke(Object target,Method method,Object[] args){
    	Class<?> returnType=method.getReturnType();
    	Object returnValue=null;
    	RedisUtil redisUtil=null;
		byte[] serialized=null;
		
		redisUtil=RedisUtil.getInstance();
		     
		String methodCacheKey=getMethodCacheKey(method,args);
		System.out.println(returnType);
//		String cacheKey=getMethodCacheKey(method,args);

		 //fetch from in-memory LRUcache
//		 returnValue=LRUCache.get(cacheKey);
//		 						 
//		 if(returnValue!=null){
//			 return returnValue;
//		 }
		 
		 //check and fetch from cache
		 //list<>
		 if(returnType==List.class){
			 if(redisUtil.exists(methodCacheKey)){
				  String[] cacheKeys=redisUtil.getString(methodCacheKey).split(",");
			      List<Object> ObjectList=new ArrayList<Object>();
                  int flag=1;
				  for(String cacheKey:cacheKeys){
			    		if(redisUtil.exists(cacheKey)){
			    			    
//								ObjectList.add(parser.parseFrom(jedis.get(UserObjectCacheKey+id).getBytes()));
//								ObjectList.add(deserializeProto(,redisUtil.getObject(cacheKey)));
							
			    		}else{
			    			flag=0;
			    			break;
			    		}
			    	}
				  if(flag==1) {
					  returnValue=ObjectList;
				  }
			 }
			 
		 //protoobject
		 }else if(Message.class.isAssignableFrom(returnType)){
			 //fetch from redis cache
			 if(redisUtil.exists(methodCacheKey)){
				    serialized=redisUtil.getObject(methodCacheKey);
					returnValue=deserializeProto(returnType,serialized);
				    System.out.println("from redis cache--");
			 }
		 }
		 
		 if(returnValue!=null){
			 return returnValue;
		 }
		 
		 //fetch from DB	 
	     try{
			returnValue=method.invoke(target,args);
		 }catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	     
		 System.out.println("from method -----:");
	     
	     if(returnValue!=null){
		    
			    //store to Redis cache
			    if(returnType==List.class){
	
			    	List<Object> list=(List<Object>)returnValue;
			    	
			    	Class<?> listType=list.get(0).getClass();
			    	if(list!=null){
				    	String id="";
	                    String listCacheKey=listType.getName();
	                    String protoUniqueVariable=protoPrimaryKey.get(target.getClass().getName());
		    			if(list.get(0) instanceof Message){
		    				for(int i=0;i<list.size();i++){
			    				Message protoObject=(Message)list.get(i);
			    				if(protoUniqueVariable!=null) {
			    					Object UniqueId=protoObject.getField(protoObject.getDescriptorForType().findFieldByName(protoUniqueVariable));
			    					
				    				id+=(listCacheKey+UniqueId);
				    				if(i<list.size()-1){
				    					id+=",";
				    				}
				    				
				    				if(!redisUtil.exists((listCacheKey+UniqueId).getBytes())) {
										redisUtil.setObject((listCacheKey+UniqueId),protoObject.toByteArray());
				    				}
			    				}
			    				
						    }
		    			}
			    		
	    				if(!redisUtil.exists(methodCacheKey)){
				    		redisUtil.setString(methodCacheKey,id);
	    				}
			    	}
			    }else if(returnValue instanceof Message){
	                String protoUniqueVariable=protoPrimaryKey.get(method.getDeclaringClass().getName());
			    	Message protoObject=(Message)returnValue;
					Object UniqueId=protoObject.getField(protoObject.getDescriptorForType().findFieldByName(protoUniqueVariable));
					
			    	//store to Redis cache
	             	redisUtil.setObject(protoObject.getClass().getSimpleName()+UniqueId,protoObject.toByteArray());
	             	
			    }
			    
		    }
		    System.out.println("from method -----:");
			    
		    //store to in-memory cache
            //LRUCache.put(cacheKey,returnValue);
	
		    return returnValue;

	}
    
    private static Object deserializeProto(Class<?> protoClass,byte[] serialized){
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
	
	//Test
	public static void main(String[] args){
		
		UserApi user=ApiProxy.getInstance().getUserApi();
        user.fetchChatList(1);
//		user.getInt();
//		user.getBoolean();
//		UserApiImpl userapi=new UserApiImpl();
//		UserApi userApiNc=userapi;
//		Method method=userapi.getClass().getMethod("print");
//		CachableApi cachaua=userapi;
//		System.out.println(user.fetchChatList(2).get(0).getUserName()); 
//		System.out.println(user.getUser(1));  
//		user.getBoolean();
//		user.getInt();
		
	}
}