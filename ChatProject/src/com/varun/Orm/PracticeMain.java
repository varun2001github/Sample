//$Id$
package com.varun.Orm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.Api.PassTableApi;
import com.varun.Api.UserTableApi;
import com.varun.Dao.UserDao;
import com.varun.Model.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class PracticeMain {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

	}
}

//public static void main(String args[]) {
//OrmImp or=new OrmImp();
//SessionTableModel ses=new SessionTableModel();
//ses.setUser_id(1);
//ses.setSession_id("asdsa");
//or.DeleteQuery(ses.getDataObject());
//}
/*     public OrmImp DeleteQuery(DataObject obj){
		String tableName=(String)obj.getDataMap().get("Table");
		String query="";
		String key="";
		String keyval="";
		Object value=null;
		int counter=0;
		query+="DELETE FROM "+tableName+" WHERE ";
	     HashMap<String, Object> oldMapAudit=new HashMap<>();

		for(Map.Entry<String,Object> mapElement : obj.getDataMap().entrySet()){
		    key = mapElement.getKey();
				value =mapElement.getValue();
		
				if(value!=null && key!="Table"){
					//if value not null 
					if(counter!=0){
						query+=" AND ";							 
					}
					query+=key+"=";
					if(value.getClass().getTypeName().endsWith("String")){
						query+="'"+(String)value+"'";
					}else{
						query+=value;
					}
					if(!tableName.equals("audit_table")){
							oldMapAudit.put(key,value);
					}
					counter=1;
				}
		 }
		System.out.println(query);
		this.query=query;
		// audit object		   				
	 	if(!tableName.equals("audit_table")){
			try {
				auditModelObject.setAudits(tableName,"DELETE",new ObjectMapper().writeValueAsString(oldMapAudit),null, System.currentTimeMillis());
			}catch (JsonProcessingException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.log(Level.INFO,"audit exception"+e);
			}
		}
		logger.log(Level.INFO,"Query created "+query);
		return this;
    }*/
