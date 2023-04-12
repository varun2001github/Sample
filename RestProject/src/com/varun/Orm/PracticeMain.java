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

import org.json.JSONObject;

import com.varun.Api.UserTableApi;
import com.varun.Model.DataObject;
import com.varun.Model.UserinfoTableModel;


public class PracticeMain {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
//		Argon2 argon2 = (Argon2) Argon2Factory.create(Argon2Types.ARGON2i);
//		String temp="$argon2i$v=19$m=500,t=1,p=1$";
//		String hash=temp+"rLXb/sJmzAZxwc2sJZVjvA"+"$"+"5Bwbkg1IIJmL1RwofWcRDbMKP+nq/HTC5IUaAkqLHWM";
//		boolean isvalid=argon2.verify(hash,"Pranav@123");
//		System.out.println(isvalid);
		String lat="12.8356682";
		String lon="80.0606192";
		if(lat!=null && lon!=null){
			String WeatherUrl="https://pfa.foreca.com/api/v1/location/search/Barcelona?lang=en";
		    URL url=null;
			url = new URL(WeatherUrl);
		    HttpURLConnection con=null;
			con = (HttpURLConnection)url.openConnection();
		    con.setRequestMethod("POST");
		    con.addRequestProperty("Authorization","Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9wZmEuZm9yZWNhLmNvbVwvYXV0aG9yaXplXC90b2tlbiIsImlhdCI6MTY3ODE2ODE4NywiZXhwIjo5OTk5OTk5OTk5LCJuYmYiOjE2NzgxNjgxODcsImp0aSI6ImY3YzgwNGE5NGFjZDUxN2IiLCJzdWIiOiJ2YXJ1MTkzMjItZWUiLCJmbXQiOiJYRGNPaGpDNDArQUxqbFlUdGpiT2lBPT0ifQ.SPCRhPb0Ue1PuPDZoJ4TPUdr7Hm7rbROkeLHtxrlVCw");
		    StringBuilder strBuf = new StringBuilder();  
		    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String output = null;  
            while ((output = br.readLine()) != null)  
            strBuf.append(output);
            br.close();
            JSONObject ob=new JSONObject(strBuf.toString());
		    System.out.println(ob.toString());
	    }
	}
}
