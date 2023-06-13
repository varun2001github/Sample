//$Id$
package com.varun;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

interface MyClassInterface{
	public void setI(int i);
	public int getI();
}

class MyClass implements MyClassInterface{
	int i;
	
	public void setI(int i){
		this.i=i;
	}
	
	public int getI(){
		return i;
	}
}

class ProxyHandler implements InvocationHandler{
	
	Object target;
	HashMap<String,Object> cache=new HashMap<>();
	
	public ProxyHandler(Object target){
		this.target=target;
	}
	
	public Object invoke(Object proxy,Method m,Object[] args) throws IllegalAccessException, InvocationTargetException{
		System.out.println("before method call -Proxied "+m.getDeclaringClass().getName()+m.getName());
		Object res=null;
		if(!m.getName().startsWith("set") || m.getReturnType()!=void.class){
			 Object cacheres=cache.get(m.getDeclaringClass().getName()+m.getName());
			 if(cacheres!=null) {
				 System.out.println(" method ret from cache");
				 return cacheres;
			 }else{
				 res=m.invoke(target,args);
				 cache.put(m.getDeclaringClass().getName()+m.getName(),res);
			 }
		}else {
			 res=m.invoke(target,args);
		}
		System.out.println("after method call");
		return res;
    }
}

public class DynamicProxy{
	public static void main(String[] args){
		MyClassInterface obj=(MyClassInterface)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
				             , new Class[]{MyClassInterface.class},new ProxyHandler(new MyClass()));
		obj.setI(1);
		System.out.println(obj.getI());
	}
}







//class ProxyHandler implements InvocationHandler{
//	
//	Object target;
//	
//	public ProxyHandler(Object target){
//		this.target=target;
//	}
//	
//	public Object invoke(Object proxy,Method m,Object[] args) throws IllegalAccessException, InvocationTargetException{
//		// ..... proxied logics before method invocation
//		System.out.println("before method call -Proxied "+m.getName());
//		Object res=m.invoke(target,args);
//		System.out.println("after method call");
//		// ..... proxied logics after method invocation
//		return res;
//	}
//}
