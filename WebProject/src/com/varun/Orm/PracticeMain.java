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
import com.varun.Api.UserTableApi;
import com.varun.Dao.UserDao;
import com.varun.Model.DataObject;
import com.varun.Model.UserinfoTableModel;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class PracticeMain {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
//		Argon2 argon2 = (Argon2) Argon2Factory.create(Argon2Types.ARGON2i);
//		String temp="$argon2i$v=19$m=500,t=1,p=1$";
//		String hash=temp+"rLXb/sJmzAZxwc2sJZVjvA"+"$"+"5Bwbkg1IIJmL1RwofWcRDbMKP+nq/HTC5IUaAkqLHWM";
//		boolean isvalid=argon2.verify(hash,"Pranav@123");
//		System.out.println(isvalid);
//		String lat="12.8356682";
//		String lon="80.0606192";
//		if(lat!=null && lon!=null){
//			String WeatherUrl="https://pfa.foreca.com/api/v1/location/search/Barcelona?lang=en";
//		    URL url=null;
//			url = new URL(WeatherUrl);
//		    HttpURLConnection con=null;
//			con = (HttpURLConnection)url.openConnection();
//		    con.setRequestMethod("POST");
//		    con.addRequestProperty("Authorization","Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9wZmEuZm9yZWNhLmNvbVwvYXV0aG9yaXplXC90b2tlbiIsImlhdCI6MTY3ODE2ODE4NywiZXhwIjo5OTk5OTk5OTk5LCJuYmYiOjE2NzgxNjgxODcsImp0aSI6ImY3YzgwNGE5NGFjZDUxN2IiLCJzdWIiOiJ2YXJ1MTkzMjItZWUiLCJmbXQiOiJYRGNPaGpDNDArQUxqbFlUdGpiT2lBPT0ifQ.SPCRhPb0Ue1PuPDZoJ4TPUdr7Hm7rbROkeLHtxrlVCw");
//		    StringBuilder strBuf = new StringBuilder();  
//		    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
//            String output = null;  
//            while ((output = br.readLine()) != null)  
//            strBuf.append(output);
//            br.close();
//            JSONObject ob=new JSONObject(strBuf.toString());
//		    System.out.println(ob.toString());
//	    }
//		URL url = new URL("http://localhost:8081/RestProject/EmailApi/getEmail/2");
//	    HttpURLConnection con = (HttpURLConnection)url.openConnection();
//	    System.out.println(con.getResponseCode());
		String s="varun.sashi2001@gmial.com";
		System.out.println(s.matches("^[A-Za-z0-9.]+@[A-Za-z0-9]{2,}\\.[a-zA-Z]+$"));
		System.out.println("suaaaaaaaaaaaaaaaaaaaaaaaaresh@gmail.com".length());
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
