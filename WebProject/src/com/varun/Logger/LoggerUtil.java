package com.varun.Logger;

import java.io.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerUtil {
    private static final String PROPERTIES_FILE_PATH = "/home/local/ZOHOCORP/varun-pt6303/ZIDE Workspace/WebProject/logger.properties";
    private static LogManager manager;
    
    private static void createManager(){
    	try{
    	    manager=LogManager.getLogManager();
			manager.readConfiguration(new FileInputStream(PROPERTIES_FILE_PATH));
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
    
    public static Logger getLogger(Class<?> clazz){
    	if(manager==null) {
    		createManager();
    	}
    	return Logger.getLogger(clazz.getName());
    }
}
