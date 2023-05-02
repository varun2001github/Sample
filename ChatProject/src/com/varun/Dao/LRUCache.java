//$Id$
package com.varun.Dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.varun.Model.UserinfoTableModel;

import java.util.concurrent.ConcurrentHashMap;

public class LRUCache{
	 protected static int MAX_SIZE=6;
 	
     private static ThreadLocal<UserinfoTableModel> ThreadLocal = new ThreadLocal<>();
     private static ConcurrentLinkedQueue<String> CacheList=new ConcurrentLinkedQueue<>();
     private static ConcurrentHashMap<String,Object> Map=new ConcurrentHashMap<>();

     public static void put(String key,Object value){
//       System.out.println(Thread.currentThread().getName()+" came in");
    	 if(CacheList.size()==MAX_SIZE){
    		 if(!CacheList.contains(key)){
    			 //remove head(LRU) with data
        		 Map.remove(CacheList.poll());
    		 }else{
            	 CacheList.remove(key);
    		 }
    	 }else if(CacheList.contains(key)){
    		 //remove old and move to first
        	 CacheList.remove(key);
    	 }
    	 //if not already exists ,then add (this condition is checked for thread abnormal behaviour)
         if(!CacheList.contains(key) && CacheList.size()<MAX_SIZE){
    		 CacheList.add(key);
         }
		 Map.put(key,value);
    	 System.out.println(CacheList);
     }
     
     public static void setThreadLocal(UserinfoTableModel obj){
    	 ThreadLocal.set(obj);
     }
     
     public static UserinfoTableModel getThreadLocal(){
    	 return ThreadLocal.get();
     }
     
     public static Object get(String key){
//       System.out.println(Thread.currentThread().getName()+" came in get");
    	 if(!CacheList.contains(key)){
    		 return null;
    	 }
    	 //move to first
    	 CacheList.remove(key);
    	 CacheList.add(key);

//    	 System.out.println("Thread "+Thread.currentThread().getName()+" Getting :"+key+" List="+CacheList+" Thread local "+threadLocalVariable.get());
//       System.out.println(Thread.currentThread().getName()+" came out get");
		 return Map.get(key);
     }
     
     public static void showCacheList(){
    	 System.out.println(CacheList);
     }
     public static String getList(String key){
    	 return CacheList.toString();
     }
     
     public static void remove(String key){
    	 CacheList.remove(key);
		 Map.remove(key);
     }
     
     public static void clearCache(){
    	 CacheList.clear();
    	 Map.clear();
     }
     
//     //for testing
//     public static void main(String[] args){
//			Thread t1=new Thread(new LRUCache());
//			Thread t2=new Thread(new LRUCache());
//			Thread t3=new Thread(new LRUCache());
//			
//			t1.setName("t1");
//			t2.setName("t2");
//			t3.setName("t3");
//
//			t1.start();
//            t2.start();
//            t3.start();
//    }
//
//	@Override
//	public void run(){
//		// TODO Auto-generated method stub
//	    LRUCache.setThreadLocal(Thread.currentThread().getName()+"Local");
//		LRUCache.put("a"+Thread.currentThread().getName(),1);
//   	    LRUCache.put("b"+Thread.currentThread().getName(),2);
//   	    LRUCache.put("c"+Thread.currentThread().getName(),3);
//        System.out.println(LRUCache.get("b"+Thread.currentThread().getName()));
//        LRUCache.put("d"+Thread.currentThread().getName(),4);
//	   	System.out.println(LRUCache.get("c"+Thread.currentThread().getName()));
//	}

}
