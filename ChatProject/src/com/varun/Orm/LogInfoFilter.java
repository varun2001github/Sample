package com.varun.Orm;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogInfoFilter implements Filter{
	public boolean isLoggable(LogRecord logRecord){
//		 System.out.println(Level.INFO.intValue()+" "+Level.WARNING.intValue());
//		 if(logRecord.getLoggerName().equals("OrmExecutor.class")) {
//			return true;
//		 }
         return logRecord.getLevel().intValue()>=Level.INFO.intValue();
    }
}
