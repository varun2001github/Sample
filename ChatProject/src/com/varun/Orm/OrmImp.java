package com.varun.Orm;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Model.*;


public class OrmImp{
	private Connection con=null;
	private static PreparedStatement stmt;
	private String query="";
    private static final Logger logger=Logger.getLogger(OrmImp.class.getName());
	
	//audit
	private AuditTableModel auditModelObject=null;
	private int insertAuditFlag=0,deleteAuditFlag=0;
	private OrmImp auditOrm=null;
	private String deleteAuditTable=null;
	
	public OrmImp(){
		try{
			con=DbConnectionSource.getConnection();
	        logger.log(Level.INFO,"ORM Db con from datasource");
		}catch(Exception e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"db con exception"+e);
	        e.printStackTrace();
	    }
	}
	
    public OrmImp(AuditTableModel auditModelObject){
    	this.auditModelObject=auditModelObject;
    	try{
			con=DbConnectionSource.getConnection();
	        logger.log(Level.INFO,"ORM Db con from datasource");
		}catch(Exception e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"db con exception"+e);
	    }
    }
    
	public void close(){
		try{
			this.con.close();
			stmt.close();
	        logger.log(Level.INFO,"ORM Db con closed");
		}catch(SQLException e){
			// TODO Auto-generated catch block
	        logger.log(Level.WARNING,"ORM Db con not closed "+e);
		}
	}
	
	public void beginTransaction(){
		try{
			con.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
			logger.log(Level.WARNING,e+"");
		}
	}
	
	public boolean commit(){
		try{
			con.commit();
			con.setAutoCommit(true);
			return true;
		}catch (SQLException e){
			try{
				con.rollback();
				con.setAutoCommit(true);
			}catch (SQLException e1){
				e1.printStackTrace();
				logger.log(Level.WARNING,e1+"");
			}
			e.printStackTrace();
			logger.log(Level.WARNING,e+"");
			return false;
		}
		
	}
	
	
	public void rollback(){
		try{
			con.rollback();
		}catch (SQLException e){
			e.printStackTrace();
			logger.log(Level.WARNING,e+"");
		}
	}
	
	public OrmImp UpdateQuery(DataObject oldObj,DataObject newObj) throws JsonProcessingException {
		 String tableName=(String)newObj.getDataMap().get("Table");
		 String query="UPDATE "+tableName+" SET ",keyval="";
	     int counter=0;
	     String key="";
	     Object value=null;
	     HashMap<String, Object> oldObjectMap=oldObj.getDataMap();
	     
	     HashMap<String, Object> oldMapAudit=new HashMap<>();
	     HashMap<String, Object> newMapAudit=new HashMap<>();
	     
	     //for audit
	     insertAuditFlag=1;
	     //iterating map and creating query
	     for(Map.Entry<String,Object> mapElement : newObj.getDataMap().entrySet()){
	    	        
	            key = mapElement.getKey();
	 			value =mapElement.getValue();
	 			if(value!=null && key!="Table" && !value.equals(oldObjectMap.get(key))){
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
	   				if(!tableName.equals("audit_table")){
	   					oldMapAudit.put(key, oldObjectMap.get(key));
	   					newMapAudit.put(key, newObj.getDataMap().get(key));
	   				}
	   				counter++;
	 			}
	     }
	     // audit object		   				
	 	 if(!tableName.equals("audit_table")){
			auditModelObject.setAudits(tableName,"UPDATE",new ObjectMapper().writeValueAsString(oldMapAudit),new ObjectMapper().writeValueAsString(newMapAudit), System.currentTimeMillis());
		 }
	 	 
	     query+=keyval;
		 this.query=query;
         logger.log(Level.INFO,"Update Query created "+query);
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
//       System.out.println(query);
         logger.log(Level.INFO,"SelectQuery(...) "+query);
      	 return this;
    }
    
    public OrmImp SelectAll(){
   	    query+="SELECT * ";
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
		        logger.log(Level.INFO,"result set avl") ;
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
    
	public OrmImp InsertQuery(DataObject obj){
		String tableName=(String)obj.getDataMap().get("Table");
	    String query="INSERT INTO "+tableName,col="(",val="(";
	    int counter=0;
	    HashMap<String, Object> newMapAudit=new HashMap<>();
        logger.log(Level.INFO,"Insert Query: "+query);
        String key="";
        Object value="";
        
        for(Map.Entry<String,Object> mapElement : obj.getDataMap().entrySet()){
            key = mapElement.getKey();
            value =mapElement.getValue();
            if(value!=null && key!="Table"){
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
	  			
	  		    // audit object
   				if(!tableName.equals("audit_table")){
	   					newMapAudit.put(key,value);
	  			}
                counter++;
            }
        }
        
        // audit object		   				
	 	if(!tableName.equals("audit_table")){
			try{
//				System.out.println("audit model obj: "+auditModelObject);
				auditModelObject.setAudits(tableName,"INSERT",null,new ObjectMapper().writeValueAsString(newMapAudit), System.currentTimeMillis());
			} catch (JsonProcessingException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		col+=")";val+=")";
		query+=col+" VALUES "+val;
		this.query=query;
        return this;
    }
	
	public Integer Insert(){
		logger.log(Level.INFO,"Insert Query is "+query);
//	    System.out.println(query);
		try{
            int rowsInserted;
			Integer uid=null;
			stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			rowsInserted=stmt.executeUpdate();
			ResultSet id=stmt.getGeneratedKeys();
            this.query="";
            if(rowsInserted>0){
            		
            	//Audit
    			if(insertAuditFlag==0){
    					insertAuditFlag=1;
    	    			this.InsertQuery(auditModelObject.getDataObject()).Insert();
    			}else if(insertAuditFlag==1){
	    				insertAuditFlag=0;
	                	return 0;
    			}
    			
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
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
        logger.log(Level.INFO,"Insert Query ret null");
		return  null;
	}
	
    public boolean update(){
         logger.log(Level.INFO,"update() called. Update Query is "+query);

    	 try{
	 		stmt = con.prepareStatement(query);
			int rowsAffected=stmt.executeUpdate();
            this.query="";

    		//Audit
            if(auditModelObject!=null){
    			this.InsertQuery(auditModelObject.getDataObject()).Insert();
            }else {
    		    logger.log(Level.WARNING," AUDIT object null");
            }
            
			if(rowsAffected>0) {
				return true;
			}
		 }catch (SQLException e){
			// TODO Auto-generated catch block
		    logger.log(Level.WARNING," Sql ",e);
		 }
         this.query="";
	     return false;
    }
    
	
    public OrmImp DeleteQuery(String table){
    	String query="";
    	query+="DELETE FROM "+table;
    	
    	if(!table.equals("audit_table")){
			auditOrm=new OrmImp();
    		deleteAuditFlag=1;
        	auditOrm.SelectAll().From(table);
    	}
    	deleteAuditTable=table;
    	this.query=query;
        logger.log(Level.INFO,"Query created "+query);
    	return this;
    }
    
    public boolean delete(){
         logger.log(Level.INFO,"delete() called.delete Query is "+query);
         System.out.println("-----del query-------"+query);
         List<DataObject> deleteAuditList=null;
       	 try{
       		 //audit
       		if(deleteAuditFlag==1 && deleteAuditTable!=null){
                logger.log(Level.INFO,"audit delete Query is "+auditOrm.getQuery());
            	deleteAuditList=auditOrm.getSelect();
            	
            }

	 		stmt = con.prepareStatement(query);
			stmt.execute();
            this.query="";
            
            if(deleteAuditFlag==1 && deleteAuditList!=null){
        		for(DataObject dataObject:deleteAuditList) {
        			HashMap<String, Object> deleteAuditMap=dataObject.getDataMap();
    				try{
    					auditModelObject.setAudits(deleteAuditTable,"DELETE",new ObjectMapper().writeValueAsString(deleteAuditMap),null, System.currentTimeMillis());
    				}catch(JsonProcessingException e){
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

                    //Audit
					insertAuditFlag=1;
        			this.InsertQuery(auditModelObject.getDataObject()).Insert();
        		}
        	}
			deleteAuditFlag=0;

            return true;
		 }catch (SQLException e){
			// TODO Auto-generated catch block
		        logger.log(Level.WARNING," Sql ",e);
		 }
         this.query="";
	     return false;
    }
    
	public <T extends CommonMethod> OrmImp InnerJoin(T obj,CriteriaBuilder cb){
    	query+=" INNER JOIN "+obj.getDataObject().getDataMap().get("Table")+cb.getCriteria();
        logger.log(Level.INFO," inner join concat "+query);
        return this;
    }
	
    public String getQuery(){
    	return query;
    }
    
	public OrmImp Where(CriteriaBuilder cr){
		String criteria=cr.getCriteria();
    	if(deleteAuditFlag==1){
        	auditOrm.Where(new CriteriaBuilder(criteria));
    	}
    	query+=" WHERE "+criteria;

        logger.log(Level.INFO," WHERE concat "+criteria);
        return this;
    }
    
    public OrmImp And(CriteriaBuilder cr){
		String criteria=cr.getCriteria();

    	if(deleteAuditFlag==1) {
        	auditOrm.And(new CriteriaBuilder(criteria));
    	}
		query+="AND "+criteria;

        logger.log(Level.INFO," AND concat "+criteria);
      	return this;
    }
    
    public OrmImp Or(CriteriaBuilder cr){
		String criteria=cr.getCriteria();
    	if(deleteAuditFlag==1) {
    	   
    	   auditOrm.Or(new CriteriaBuilder(criteria));
    	}
		query+=" OR "+criteria;

        logger.log(Level.INFO," OR concat "+criteria);
      	return this;
    }
    public OrmImp From(String table){
    	query+=" FROM "+table;
        logger.log(Level.INFO," FROM concat "+table);
    	return this;
    }
    public <t extends CommonMethod> OrmImp From(t obj){
    	query+=" FROM "+obj.getDataObject().getDataMap().get("Table");
        logger.log(Level.INFO," FROM concat "+obj.getDataObject().getDataMap().get("Table"));
    	return this;
    }
    public OrmImp As(String alias){
    	query+="AS "+alias;
        logger.log(Level.INFO," AS "+alias);
    	return this;
    }
    public OrmImp OrderBy(String column){
    	query+=" ORDER BY "+column;
        logger.log(Level.INFO," OrderBy concat "+column);
    	return this;
    }
    public void clearQuery() {
        this.query="";
    }
}
