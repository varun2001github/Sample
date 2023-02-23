package com.varun.Orm;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;


public class OrmImp{
	private Connection con=null;
	private static PreparedStatement stmt;
	private String query="";
	private Object obj=null;
	private static DbConnectionSource conSource=new DbConnectionSource();
	
	//logger 
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	
	public OrmImp(Object obj){
		this.obj=obj;
		try{
			con=DbConnectionSource.getConnection();
	        logger.log(Level.INFO,"ORM Db con from datasource");
		} catch (Exception e) {
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"db con exception"+e);
	    }
	}
    
	public void close(){
		try{
			this.con.close();
	        logger.log(Level.INFO,"ORM Db con closed");
		}catch (SQLException e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"ORM Db con not closed "+e);
		}
	}
	
	public OrmImp UpdateQuery(Object obj){
    	 String query="UPDATE "+obj.getClass().getAnnotation(Table.class).name()+" SET ",keyval="";
	     int counter=0;
	    
	     //reflection to get db details from model obj
    	 Field[] farr=obj.getClass().getDeclaredFields();
	     for(Field field:farr){
	    	 if(field.isAnnotationPresent(Column.class)){
	    		 try{
					  if(field.get(obj)!=null){
							  if(counter!=0){
									 keyval+=",";							 
							  }
							  keyval+=field.getName()+"=";
							  if(field.getType().toString().endsWith("String")){
								  keyval+="'"+field.get(obj)+"'";
							  }else{
								  keyval+=field.get(obj);
							  }
							  counter++;
					  }
				  }catch(IllegalArgumentException e){
						// TODO Auto-generated catch block
				        logger.log(Level.WARNING,"UpdateQuery()",e);
				  }catch(IllegalAccessException e){
						// TODO Auto-generated catch block
				        logger.log(Level.WARNING,"UpdateQuery()",e);
				  }
	    	 }
	    	 field.setAccessible(true);
		 }
		 query+=keyval;
		 this.query=query;
         logger.log(Level.INFO,"Update Query created "+query);
		 return this;
	}
    
    public OrmImp DeleteQuery(){
    	String query="";
    	query+="DELETE FROM "+obj.getClass().getAnnotation(Table.class).name();
    	this.query=query;
        logger.log(Level.INFO,"Deljava.sql.SQLException: Connection is null.eteQuery created "+query);
    	return this;
    }
    
    public OrmImp SelectQuery(String... columns){
//       System.out.println("select query is: "+query);
    	 int counter=0;
    	 String table=obj.getClass().getAnnotation(Table.class).name();
      	 query+="SELECT ";
      	 for(String column:columns){
      		 if(counter==1){
   					 query+=",";
   			  }
   		      query+=(column);
   			  counter=1;
         }
      	 query+=" FROM "+table;
         logger.log(Level.INFO,"SelectQuery(...) "+query);
      	 return this;
    }
    
    @SuppressWarnings("rawtypes")
    public OrmImp SelectAll(){
//    	System.out.println("select query is: "+query);
    	String table=obj.getClass().getAnnotation(Table.class).name();
   	    query+="SELECT * FROM "+table;
        logger.log(Level.INFO,"SelectAll quer :"+query);
      	return this;
    }
    
    public List<Object> getSelect(){
//      logger.log(Level.INFO,"getSelect()");
		List<Object> l=new ArrayList<Object>();
        logger.log(Level.INFO,"select Query: "+query);
		try{
			stmt = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
		        logger.log(Level.INFO,"result set avl");
				ResultSetMetaData meta = rs.getMetaData();
				rs.last();
				@SuppressWarnings("unchecked")
				Constructor<Object> constructor =(Constructor<Object>) obj.getClass().getDeclaredConstructor();
				Method setterMethod=null;
			    rs.beforeFirst();
			    int colcount=meta.getColumnCount(); 
				while(rs.next()){
					Object newInstance = constructor.newInstance();	
					for(int i=1;i<=colcount;i++){
						String columnName=meta.getColumnName(i);
                        //System.out.println(columnName);
						int columnType=meta.getColumnType(i);
//						System.out.println(meta.getColumnTypeName(i)+" "+"set" + (columnName.charAt(0)+"").toUpperCase()+columnName.substring(1,columnName.length()));
						Object columnValue = rs.getObject(i);
                        //System.out.println(l.size());
						if(columnValue!=null){
							try{
								if(columnType == Types.VARCHAR || columnType == Types.CHAR || columnType == Types.LONGVARCHAR ){
									setterMethod= newInstance.getClass().getMethod("set" + (columnName.charAt(0)+"").toUpperCase()+columnName.substring(1,columnName.length()), String.class);
									setterMethod.invoke(newInstance,rs.getString(i));
								}else if(columnType==Types.BIGINT || columnType==Types.NUMERIC){
									setterMethod= newInstance.getClass().getMethod("set" + (columnName.charAt(0)+"").toUpperCase()+columnName.substring(1,columnName.length()), BigInteger.class);
			                        setterMethod.invoke(newInstance,BigInteger.valueOf(rs.getLong(i)));
								}else{
									setterMethod= newInstance.getClass().getMethod("set" + (columnName.charAt(0)+"").toUpperCase()+columnName.substring(1,columnName.length()), Integer.class);
			                        setterMethod.invoke(newInstance,rs.getInt(i));
								}
							}catch(NoSuchMethodException e){
								// TODO Auto-generated catch block
						        logger.log(Level.WARNING,"",e);
							}catch (SecurityException e){
								// TODO Auto-generated catch block
						        logger.log(Level.WARNING,"",e);
							}
						}
					}
					l.add(newInstance);
		        }
		        rs.close();
			}
			stmt.close();
            this.query="";
            return l;
		 }catch(SQLException e) {
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING,"SQL Exception",e);
		 } catch (InstantiationException e) {
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING,e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,e.getMessage());
		} catch (SecurityException e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,e.getMessage());
		}
        this.query="";
        return null;
	}
    
	public Integer Insert(Object obj){
		this.obj=obj;
	    String query="INSERT INTO "+obj.getClass().getAnnotation(Table.class).name(),col="(",val="(";
	    int counter=0;
        logger.log(Level.INFO,"Insert Query: "+query);

	    //Using reflection to get db details from model obj
    	Field[] farr=obj.getClass().getDeclaredFields();
	    for(Field field:farr){
	    	 if(field.isAnnotationPresent(Column.class)){
	    		 field.setAccessible(true);
					try{
						if(field.get(obj)!=null){
							  if(counter!=0){
									 col+=",";
									 val+=",";
							  }
							  col+=(field.getName());
							  if(field.getType().toString().endsWith("String")){
								  val+="'"+field.get(obj)+"'";
							  }else{
								  val+=field.get(obj);
							  }
							  counter++;
						 }
					}catch(IllegalArgumentException e){
						// TODO Auto-generated catch block
				        logger.log(Level.FINE,"InsertQuery()",e.getMessage());
					}catch(IllegalAccessException e){
						// TODO Auto-generated catch block
				        logger.log(Level.INFO,"InsertQuery()",e.getMessage());
					}
	    	 }
    	}
		col+=")";val+=")";
		query+=col+" VALUES "+val;
		System.out.println(query);
        logger.log(Level.INFO,"Insert Query is "+query);
        try{
            int rowsInserted;
			Integer uid=null;
			stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			rowsInserted=stmt.executeUpdate();
			ResultSet id=stmt.getGeneratedKeys();
            this.query="";
            if(rowsInserted>0){
    	        logger.log(Level.INFO,"row inserted.No. of inserts "+rowsInserted);
            	if(id.next()){
                	uid=id.getInt(1);
        	        logger.log(Level.INFO,"New id generated id="+uid);
         		    stmt.close();
                	return uid;
                }else{
                    logger.log(Level.INFO,"Inserted without Key gen return 0");
        		    stmt.close();
                	return 0;
                }
            }
    	    logger.log(Level.INFO,"No row inserted. No. of inserts "+rowsInserted);
            
            
         }catch(SQLException e){
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING," Sql ",e);
		 }
         logger.log(Level.INFO,"Insert Query ret null");
         return null;  	
    }
	
    public boolean update(){
        logger.log(Level.INFO,"update() called. Update Query is "+query);
    	try {
	 		stmt = con.prepareStatement(query);
			stmt.execute();
            stmt.close();
            this.query="";
			return true;
		 }catch (SQLException e){
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING," Sql ",e);
		 }
         this.query="";
	     return false;
    }
    
    public boolean delete(){
        logger.log(Level.INFO,"delete() called.delete Query is "+query);
       	try{
	 		stmt = con.prepareStatement(query);
			stmt.execute();
            stmt.close();
            this.query="";
			return true;
		 }catch (SQLException e){
			// TODO Auto-generated catch block
		        logger.log(Level.WARNING," Sql ",e);
		 }
         this.query="";
	     return false;
    }
    
	public <T> OrmImp InnerJoin(T obj,CriteriaBuilder cb){
    	query+=" INNER JOIN "+obj.getClass().getAnnotation(Table.class).name()+cb.getCriteria();
        logger.log(Level.INFO," inner join concat "+query);
        return this;
    }
    public String getQuery() {
    	return this.query;
    }
	public OrmImp Where(CriteriaBuilder cr){
    	query+=" WHERE "+cr.getCriteria();
        logger.log(Level.INFO," WHERE concat "+cr.getCriteria());
        return this;
    }
    
    public OrmImp And(CriteriaBuilder cr){
		query+=" AND "+cr.getCriteria();
        logger.log(Level.INFO," AND concat "+cr.getCriteria());
      	return this;
    }
    
    public OrmImp Or(CriteriaBuilder cr){
		query+=" OR "+cr.getCriteria();
        logger.log(Level.INFO," OR concat "+cr.getCriteria());
      	return this;
    }

}
