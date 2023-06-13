//$Id$
package com.varun.Api.CacheImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ProtoModel.UserModel.User;
import com.varun.Api.UserApiImpl;
import com.varun.Api.Interface.CachableUserApi;
import com.varun.cache.ProtoParser;
import com.varun.cache.RedisUtil;

public class UserApiCacheImpl implements CachableUserApi{
    RedisUtil redis=null;
    private static final String UserClassName=User.class.getName();
    private static final String ListCacheKey="List<"+User.class.getName()+">";
    
    public UserApiCacheImpl(){
    	redis=RedisUtil.getInstance();
    }
    
	@Override
	public User getUser(Integer userid){
		// TODO Auto-generated method stub
		User user=null;
		
		//from cache
		byte[] serialized=redis.getObject(UserClassName+userid);
		
		if(serialized!=null){
			user=(User)ProtoParser.parseProto(User.parser(),serialized);
			System.out.println("User from cache");
			return user;
		}
		
		UserApiImpl userApi=new UserApiImpl();
        //from db
		user=userApi.getUser(userid);
		if(user!=null) {
			redis.setObject(UserClassName+userid,user.toByteArray());
			System.out.println("User from db");
			return user;
		}
		return null;
	}

	@Override
	public User getUser(String email){
		// TODO Auto-generated method stub
		User user=null;
		byte[] serialized=redis.getObject(UserClassName+email);
		
		if(serialized!=null) {
			user=(User)ProtoParser.parseProto(User.parser(),serialized);
			return user;
		}
		
		UserApiImpl userApi=new UserApiImpl();
		user=userApi.getUser(email);
		if(user!=null) {
			redis.setObject(UserClassName+email,user.toByteArray());
			return user;
		}
		return null;
	}

	@Override
	public List<User> fetchChatList(Integer uid){
		 List<User> listOfUser= null;
		 if(redis.exists((ListCacheKey+uid).getBytes())){
			 List<byte[]> serializedList=redis.getList(ListCacheKey+uid);
			 listOfUser=new ArrayList<User>();
			 for(byte[] serialized:serializedList){
				 listOfUser.add((User)ProtoParser.parseProto(User.parser(),serialized));
			 }
			 System.out.println("from cache");
			 return listOfUser;
		 }
		 //db
		 UserApiImpl userApi=new UserApiImpl();
		 listOfUser=userApi.fetchChatList(uid);
		 System.out.println("db");
		 for(User user:listOfUser){
			 redis.rpush(ListCacheKey+uid,user.toByteArray());
		 }
		 return listOfUser;
		 // TODO Auto-generated method stub
	}

	@Override
	public HashMap<Integer, User> getUsers(List<Integer> userIds) {
		// TODO Auto-generated method stub
		return null;
	}

}
