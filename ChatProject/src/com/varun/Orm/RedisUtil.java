//$Id$
package com.varun.Orm;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil{
	private static final JedisPool jpool=getJedisPool(); 
	
    private static final JedisPool getJedisPool() {
    	JedisPoolConfig config=new JedisPoolConfig();
    	config.setMaxTotal(100);
    	config.setMaxIdle(20);
    	config.setMinIdle(10);
    	return new JedisPool(config,"localhost", 6379);
    }
    
    public static RedisUtil getInstance() {
    	return new RedisUtil();
    }
   
    private Jedis jedis=null;

    public void setObject(String key,byte[] value){
    	jedis=jpool.getResource();
    	jedis.set(key.getBytes(),value);
    	jedis.disconnect();
    	jedis.close();
    }
    
    public void rpush(String key,byte[] value){
    	jedis=jpool.getResource();
    	jedis.rpush(key.getBytes(),value);
    	jedis.disconnect();
    	jedis.close();
    }
    
    public List<byte[]> getList(String key){
    	jedis=jpool.getResource();
    	List<byte[]> cacheObject=jedis.lrange(key.getBytes(),0,-1);
    	jedis.disconnect();
    	jedis.close();
    	return cacheObject;
    }
    public void setString(String key,String value){
    	jedis=jpool.getResource();
    	jedis.set(key,value);
    	jedis.disconnect();
    	jedis.close();
    }
    
    public byte[] getObject(String key){
    	jedis=jpool.getResource();
    	byte[] cacheObject=jedis.get(key.getBytes());
    	jedis.disconnect();
    	jedis.close();
    	return cacheObject;
    }
    
    public String getString(String key){
    	jedis=jpool.getResource();
    	String cacheObject=jedis.get(key);
    	jedis.disconnect();
    	jedis.close();
    	return cacheObject;
    }
    
    public boolean exists(String key){
    	jedis=jpool.getResource();
        boolean exists=jedis.exists(key);
        jedis.disconnect();
    	return exists;
    }
    
    public boolean exists(byte[] key){
    	jedis=jpool.getResource();
        boolean exists=jedis.exists(key);
        jedis.disconnect();
    	return exists;
    }
    public static void main(String args[]) {
    	Jedis jedis=jpool.getResource();
    	jedis.flushAll();
    }
}
