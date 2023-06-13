//$Id$
package com.varun.Api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.protobuf.Message;
import com.varun.Api.Interface.NonCachable;
import com.varun.cache.LRUCache;
import com.varun.cache.RedisUtil;

public class ProxyCacheApi{
	
	private RedisUtil redisUtil=null;
	
	public boolean isCachableMethod(Method method){
		if(NonCachable.class.isAssignableFrom(method.getDeclaringClass())){
			//if belongs to NonCachable
			return false;
		}else{
			return true;
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
            for(Object arg:args){
               keyBuilder.append(":").append(arg);
            }
        }
        
        return keyBuilder.toString();
    }
    
    public ProxyCacheApi(){
    	redisUtil=RedisUtil.getInstance();
    }
    
    public static ProxyCacheApi getInstance(){
    	return new ProxyCacheApi();
    }
    
    public Object hitRedisCache(Method method,Object[] args){
    	
    	try{
    		RedisUtil redisUtil=RedisUtil.getInstance();
    		byte[] serialized=null;
    		String methodCacheKey=getMethodCacheKey(method,args);
    		Class<?> methodReturnType=method.getReturnType();
        	Object returnValue=null;
        	
    		if(redisUtil.exists(methodCacheKey)){
    			 Class<?> protoClassType=getGenericType(method);
    			 
    			 if(Iterable.class.isAssignableFrom(methodReturnType)){  
    				 if(methodReturnType==List.class){
        				 List<byte[]> serializedList=redisUtil.getList(methodCacheKey);
        				 List<Object> list=new ArrayList<Object>();
        				 for(byte[] bytes:serializedList){
        					 list.add(deserializeProto(protoClassType,bytes));
        				 }
        				 returnValue=list;
        			 //protoobject
        			 }else if(methodReturnType==Set.class){
        				 
        				 Set<byte[]> serializedList=redisUtil.getFromSet(methodCacheKey);
        				 Set<Object> set=new HashSet<Object>();
        				 for(byte[] bytes:serializedList){
        					 set.add(deserializeProto(protoClassType,bytes));
        				 }
        				 returnValue=set;
        				 
        			 }
    			 }else if(Message.class.isAssignableFrom(methodReturnType)){
     				//fetch from redis cache
     			    serialized=redisUtil.getObject(methodCacheKey);
     				returnValue=deserializeProto(methodReturnType,serialized);
     			 }
    		}
    		redisUtil.close();
    		return returnValue;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public Object hitLRUCache(Method method,Object[] args){
    	try{
    		String methodCacheKey=getMethodCacheKey(method,args);
    		Object result=LRUCache.get(methodCacheKey);
    		return result;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
		
    }
    
    public Object getFromCache(Method method,Object[] args){
    	
		//fetch from in-memory LRUcache
		Object result=null;
//		result=hitLRUCache(method,args);
		if(result==null){
			result=hitRedisCache(method,args);
		}
		return result;
    }
    
    public Object InvokeAndCache(Object target,Method method,Object[] args){
    	Class<?> returnType=method.getReturnType();
    	Object result=null;
    	RedisUtil redisUtil=RedisUtil.getInstance();
		String methodCacheKey=getMethodCacheKey(method,args);

		//fetch from DB	 
	    try{
			result=method.invoke(target,args);
		}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     	     
	    if(result!=null){
	    	 
			    //store to Redis cache
		    	if(Iterable.class.isAssignableFrom(returnType)){
		    		Iterator<?> iterator=((Iterable<?>)result).iterator();
		    		
		    		if(Message.class.isAssignableFrom(iterator.next().getClass())){
		    			if(result instanceof List){
							while(iterator.hasNext()){
								 redisUtil.rpush(methodCacheKey,((Message)iterator.next()).toByteArray());
							} 
				    	}else if(result instanceof Set){
						    while(iterator.hasNext()){
						    	redisUtil.addProtoSet(methodCacheKey,((Message)iterator.next()).toByteArray());
						    }
					    }
		    		}
				}else if(result instanceof Message){
			    	Message protoObject=(Message)result;
			    	//set Redis cache
	             	redisUtil.setObject(methodCacheKey,protoObject.toByteArray());
			    }
	    }    
	    //store to in-memory cache
        LRUCache.put(methodCacheKey,result);

        redisUtil.close();
	    return result;

	}
    
    public Class<?> getGenericType(Method method){
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
}

