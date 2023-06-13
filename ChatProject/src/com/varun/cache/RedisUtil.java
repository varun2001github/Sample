//$Id$
package com.varun.cache;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil{
	private static final JedisPool jpool=getJedisPool(); 
	
    private static final JedisPool getJedisPool(){
    	JedisPoolConfig config=new JedisPoolConfig();
    	config.setMaxTotal(100);
    	config.setMaxIdle(20);
    	config.setMinIdle(10);
    	JedisPool jpool=null;
    	try{
        	jpool=new JedisPool(config,"localhost", 6379);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    	return jpool;
    }
    
    private Jedis jedis=null;

    public static RedisUtil getInstance(){
    	return new RedisUtil();
    }
    
    public RedisUtil(){
    	jedis=jpool.getResource();
    }
    
    public void addProtoSet(String key,byte[] value) {
    	jedis.sadd(key.getBytes(),value);
    }
    public Set<byte[]> getFromSet(String key){
    	Set<byte[]> setMembers=jedis.smembers(key.getBytes());
    	return setMembers;
    }
    
    public void setObject(String key,byte[] value){
    	jedis.set(key.getBytes(),value);
    }
    
    public void rpush(String key,byte[] value){
    	jedis.rpush(key.getBytes(),value);
    }
    
    public List<byte[]> getList(String key){
    	List<byte[]> cacheObject=jedis.lrange(key.getBytes(),0,-1);
    	return cacheObject;
    }
    
    public void setString(String key,String value){
    	jedis.set(key,value);
    }
    
    public byte[] getObject(String key){
    	byte[] cacheObject=jedis.get(key.getBytes());
    	return cacheObject;
    }
    
    public String getString(String key){
    	String cacheObject=jedis.get(key);
    	return cacheObject;
    }
    
    public boolean exists(String key){
        boolean exists=jedis.exists(key);
    	return exists;
    }
    
    public boolean exists(byte[] key){
        boolean exists=jedis.exists(key);
    	return exists;
    }
    public void del(String key) {
    	jedis.del(key);
    }
    public void del(byte[] key){
    	jedis.del(key);
    }
    public void flushAll(){
    	 jedis.flushAll();
    }
    public void close(){
    	jedis.close();
    }
    
    public static void main(String args[]) {
    	RedisUtil redis=RedisUtil.getInstance();
    	System.out.println(redis.getObject("com.ProtoModel.UserModel$User:1"));
    	System.out.println(redis.getObject("com.ProtoModel.UserModel$User:2"));;
//    	redis.flushAll();
//    	redis.setString("name","varun");
//    	redis.setString("name2","varunS");
//    	System.out.println("->"+redis.getString("name")+redis.getString("name2"));
    }
}
