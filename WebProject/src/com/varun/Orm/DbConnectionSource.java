package com.varun.Orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.tomcat.dbcp.BasicDataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;

public class DbConnectionSource{
	private static Connection con=null;
	private static BasicDataSource dataSource=null;
    private static String  urlpath="jdbc:mysql://localhost/chatappDB";
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	
	public void createDataSource(){
		// TODO Auto-generated constructor stub
					
    }
    
	public static Connection getConnection() throws SQLException{
		if(dataSource==null) {
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
		        logger.log(Level.INFO,"db connection occured to "+urlpath);
			}catch(ClassNotFoundException e) {
				// TODO Auto-generated catch block
		        logger.log(Level.INFO,"db connection not occured to ");
		        e.printStackTrace();
			}
	        dataSource =new BasicDataSource();
	        System.out.println("ds "+dataSource);
			dataSource.setUrl(urlpath);
			dataSource.setUsername("root");
			dataSource.setPassword("Varunsashi@2001");
			dataSource.setInitialSize(-1);
			dataSource.setMaxTotal(1000);
		}
		con=dataSource.getConnection();
        logger.log(Level.INFO,"dataSource connection object returned ");
		return con;	
	}

}