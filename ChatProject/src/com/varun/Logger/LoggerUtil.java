package com.varun.Logger;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerUtil{
    private static final String PROPERTIES_FILE_PATH = "/home/local/ZOHOCORP/varun-pt6303/ZIDE Workspace/ChatProject/logger.properties";
    private static boolean propertySetFlag=false;
    
    private static void addProperty(){
    	try{
    	    LogManager.getLogManager().readConfiguration(new FileInputStream(PROPERTIES_FILE_PATH));
			propertySetFlag=true;
		}catch (SecurityException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args){
    	if(propertySetFlag==false){
    		addProperty();
    	}
    }
}
