package com.varun.Orm;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;


public class OrmImp{
	private Connection con=null;
	private static PreparedStatement stmt;
	private String query="";
//	private O obj=null;
	private static DbConnectionSource conSource=new DbConnectionSource();

	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);
	public OrmImp(){
        try{
			con=DbConnectionSource.getConnection();
	        logger.log(Level.INFO,"ORM Db con from datasource");
		}catch (Exception e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"db con exception"+e);
	    }
	}

	public void close(){
		try{
			this.con.close();
			stmt.close();
	        logger.log(Level.INFO,"ORM Db con closed");
		}catch (SQLException e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"ORM Db con not closed "+e);
		}
	}
	
	public void beginTransaction(){
		try {
			con.setAutoCommit(false);
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean commit(){
		try{
			con.commit();
			con.setAutoCommit(true);
			return true;
		}catch (SQLException e){//		if(super.map!=null){
//			for(DbColumn c:DbColumn.values()){
//			//if getter methods result not null
//			if(c.getValFromRef().apply(this)!=null){
//				//put in parents hashmap
//				super.map.put(c.name(),c.getValFromRef().apply(this));
//			}
//		}	
//	}
			// TODO Auto-generated catch block
			try{
				con.rollback();
			}catch (SQLException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}
	
	public OrmImp UpdateQuery(DataObject obj){
		 String query="UPDATE "+obj.getClass().getAnnotation(Table.class).name()+" SET ",keyval="";
	     int counter=0;
	     String key="";
	     Object value=null;
	     //iterating map and creating query
	     for (Map.Entry<String,Object> mapElement : obj.getDataMap().entrySet()){
			            key = mapElement.getKey();
			 			value =mapElement.getValue();
			 			if(value!=null){
			 				//if value not null 
			   				if(counter!=0){
			   						 keyval+=",";							 
			   				}
			   				keyval+=key+"=";
			   				if(value.getClass().getTypeName().endsWith("String")){
			   					  keyval+="'"+(String)value+"'";
			   				}else{
			   					  keyval+=value;
			   				}
			   				counter++;
			 			}
			   			
	     }
	     query+=keyval;
		 this.query=query;
		 System.out.println(query);
         logger.log(Level.INFO,"Update Query created "+query);
		 return this;
	}
	
    public OrmImp DeleteQuery(String table){
    	String query="";
    	query+="DELETE FROM "+table;
    	this.query=query;
        logger.log(Level.INFO,"Deljava.sql.SQLException: Connection is null.eteQuery created "+query);
    	return this;
    }
    
    public OrmImp SelectQuery(String... columns){
    	 int counter=0;
      	 query+="SELECT ";
      	 for(String column:columns){
      		 if(counter==1){
   					 query+=",";
   			  }
   		      query+=(column);
   			  counter=1;
         }
//      	 System.out.println(query);
         logger.log(Level.INFO,"SelectQuery(...) "+query);
      	 return this;
    }
    
    public OrmImp SelectAll(){
//    	System.out.println("select query is: "+query);
//    	String table=obj.getClass().getAnnotation(Table.class).name();
   	    query+="SELECT * ";
//   	    System.out.println(query);
        logger.log(Level.INFO,"SelectAll quer :"+query);
      	return this;
    }
    
    public List<DataObject> getSelect(){
		List<DataObject> l=new ArrayList<DataObject>();
        logger.log(Level.INFO,"select Query: "+query);
		try{
			stmt = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){
		        logger.log(Level.INFO,"result set avl");
				ResultSetMetaData meta = rs.getMetaData();
			    rs.beforeFirst();
			    int colcount=meta.getColumnCount(); 
			    String columnName="";
			    Object columnValue="";
				while(rs.next()){
					HashMap<String,Object> map=new HashMap<>();
					for(int i=1;i<=colcount;i++){
						int columnType=meta.getColumnType(i);
						columnName=meta.getColumnName(i);
					    if(columnType == Types.TIMESTAMP){
					    	if(rs.getObject(i)!=null){
					    		Timestamp t=rs.getTimestamp(i);
						    	columnValue=t.getTime();
								map.put(columnName,columnValue);
					    	}
					    }else{
                          	columnValue = rs.getObject(i);
							map.put(columnName,columnValue);
					    }
					}
					l.add(new DataObject(map));
		        }
		        rs.close();
			}
            this.query="";
            return l;
		 }catch(SQLException e){
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING,"SQL Exception",e);
		 }
        this.query="";
        return null;
	}
    
	public Integer Insert(DataObject obj){
		System.out.println("in");
//		this.obj=obj;
	    String query="INSERT INTO "+obj.getClass().getAnnotation(Table.class).name(),col="(",val="(";
	    int counter=0;
        logger.log(Level.INFO,"Insert Query: "+query);
        String key="";
        Object value="";
        for(Map.Entry<String,Object> mapElement : obj.getDataMap().entrySet()){
            key = mapElement.getKey();
            value =mapElement.getValue();
            if(value!=null){
            	if(counter!=0){
  			      col+=",";
  			      val+=",";
	  	        }
	  			col+=(key);
	  			if(value.getClass().getTypeName().toString().endsWith("String")){
	  			   val+="'"+value+"'";
	  			}else{
	  	      	   val+=value;
	  			}
	  			counter++;
            }
        }
    	
		col+=")";val+=")";
		query+=col+" VALUES "+val;
//		System.out.println(query);
        logger.log(Level.INFO,"Insert Query is "+query);
        try{
            int rowsInserted;
			Integer uid=null;
			stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			System.out.println("ins");
			rowsInserted=stmt.executeUpdate();
			ResultSet id=stmt.getGeneratedKeys();
            this.query="";
            if(rowsInserted>0){
    	        logger.log(Level.INFO,"row inserted.No. of inserts "+rowsInserted);
            	if(id.next()){
                	uid=id.getInt(1);
        	        logger.log(Level.INFO,"New id generated id="+uid);
                	return uid;
                }else{
                    logger.log(Level.INFO,"Inserted without Key gen return 0");
                	return 0;
                }
            }
    	    logger.log(Level.INFO,"No row inserted. No. of inserts "+rowsInserted);
            
            
         }catch(SQLException e){
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING," Sql ",e);
		    System.out.println("not ins");
		 }
         logger.log(Level.INFO,"Insert Query ret null");
         return null;  	
    }
	
    public boolean update(){
         logger.log(Level.INFO,"update() called. Update Query is "+query);
		 System.out.println(query + " status");

    	 try{
	 		stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			 System.out.println(query + " Updated");
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
    public String getQuery(){
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
    public OrmImp From(String table){
    	query+=" FROM "+table;
        logger.log(Level.INFO," FROM concat "+table);
    	return this;
    }

}
