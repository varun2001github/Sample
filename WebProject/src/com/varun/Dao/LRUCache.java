//$Id$
package com.varun.Dao;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache{
	 protected static int MAX_SIZE=3;
     private static HashMap<String,Object> Map=new HashMap<>();
     private static LinkedList<String> CacheList=new LinkedList<String>();  	
//   private static ThreadLocal<HashMap<String,Object>> threadLocalVariable = new ThreadLocal<>();
     
     public static void put(String key, Object value){
//    	 System.out.println(" ___Put in cache__:"+value.getClass().getSimpleName());
//    	 threadLocalVariable.set(ListMap);
//    	 System.out.println("threadLocalVariable is set "+threadLocalVariable.get());
    	 
    	 if(CacheList.size()>=MAX_SIZE){
    		 Map.remove(CacheList.getLast());
    		 CacheList.removeLast();
    	 }
    	 CacheList.addFirst(key);
    	 Map.put(key, value);
     }
     
     public static Object get(String key){
    	 if(!CacheList.contains(key)){
    		 return null;
    	 }
//    	 System.out.println("T L V: "+threadLocalVariable.get());
    	 int keyIndex=CacheList.indexOf(key);
    	 CacheList.remove(keyIndex);
    	 CacheList.addFirst(key);
		 return Map.get(key);
     }
     
     public static void remove(String key){
    	 int keyIndex=CacheList.indexOf(key);
    	 CacheList.remove(keyIndex);
		 Map.remove(key);
     }
     
     public static void clearCache(){
    	 CacheList.clear();
    	 Map.clear();
     }
     
     public static void main(String[] args){
			LRUCache.put("a",1);
	   	    LRUCache.put("b",2);
	   	    System.out.println(LRUCache.CacheList);
	   	    LRUCache.put("c",3);
	   	    System.out.println(LRUCache.CacheList);
	        System.out.println(LRUCache.get("b"));
	        System.out.println(LRUCache.CacheList);
	        LRUCache.put("d",4);
	        System.out.println(LRUCache.CacheList);

     }

}
