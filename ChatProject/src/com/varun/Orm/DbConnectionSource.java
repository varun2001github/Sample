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
    private static String urlpath="jdbc:mysql://localhost/chatappDB";
    private static final Logger logger=Logger.getLogger(DbConnectionSource.class.getName());
	private static BasicDataSource dataSource=createSourceConfig();

	private static final BasicDataSource createSourceConfig(){
		    System.out.println(urlpath);
		    // TODO Auto-generated constructor stub
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
	
			}catch(ClassNotFoundException e) {
				// TODO Auto-generated catch block
		        logger.log(Level.INFO,"db connection not occured to ");
		        e.printStackTrace();
			}
		    BasicDataSource dataSource=new BasicDataSource();
			dataSource.setUrl(urlpath);
			dataSource.setUsername("root");
			dataSource.setPassword("Varunsashi@2001");
			dataSource.setInitialSize(-1);
			dataSource.setMaxTotal(1000);
			return dataSource;
			
    }
    
	public static Connection getConnection(){
		Connection con=null;
		try {
			con = dataSource.getConnection();
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
	        logger.log(Level.WARNING,"dataSource connection object returned ");
		}
		return con;	
		
	}
	public static void main(String[] args){
		OrmImp orm=new OrmImp();
		
	}
}