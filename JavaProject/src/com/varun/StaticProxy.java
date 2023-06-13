//$Id$
package com.varun;

import java.util.HashMap;
import java.util.Map;

public class StaticProxy implements Runnable{
	public static Map<String,String> myMap=getMap();
	
	public static Map<String,String> getMap(){
		Map<String,String> myMap=new HashMap<String,String>();
		myMap.put("vn","by");
		myMap.put("se","gi");
		return myMap;
	}
	
	public void run(){
		// TODO Auto-generated method stub
		System.out.println(myMap.get("vn"));
		System.out.println(myMap.get("se"));
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		StaticProxy staticProxy = new StaticProxy();
		Thread t1=new Thread(staticProxy);
		Thread t2=new Thread(staticProxy);
		Thread t3=new Thread(staticProxy);
		Thread t4=new Thread(staticProxy);
		Thread t5=new Thread(staticProxy);
		Thread t6=new Thread(staticProxy);
		Thread t7=new Thread(staticProxy);
		Thread t8=new Thread(staticProxy);
		Thread t9=new Thread(staticProxy);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();

	}

}
