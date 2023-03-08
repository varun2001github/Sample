//$Id$
package com.varun.Orm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

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
		String lat="12.8356682";
		String lon="80.0606192";
		if(lat!=null && lon!=null){
			String WeatherUrl="https://api.openweathermap.org/data/2.5/weather?q=INDIA&appid=43b81b5911427aa18abe13dfa021e861";
		    URL url=new URL(WeatherUrl);
		    HttpURLConnection con=(HttpURLConnection)url.openConnection();
		    con.setRequestMethod("GET");
	        StringBuilder strBuf = new StringBuilder();  
		    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String output = null;  
            while ((output = br.readLine()) != null)  
                strBuf.append(output);
            br.close();
		    System.out.println(strBuf.toString());
		
	    }
	}

}
