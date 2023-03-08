package com.varun.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/WeatherServlet")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		//get lat and long from request
		String lat=request.getParameter("latitude"),lon=request.getParameter("longitude"),location="";
		
		//Appending in URL
		location=lon+","+lat;
		String WeatherUrl="https://pfa.foreca.com//api/v1/current/"+location;
	    URL url = new URL(WeatherUrl);
	    HttpURLConnection con = (HttpURLConnection)url.openConnection();
	    con.setRequestMethod("POST");
	    con.addRequestProperty("Authorization","Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9wZmEuZm9yZWNhLmNvbVwvYXV0aG9yaXplXC90b2tlbiIsImlhdCI6MTY3ODE2ODE4NywiZXhwIjo5OTk5OTk5OTk5LCJuYmYiOjE2NzgxNjgxODcsImp0aSI6ImY3YzgwNGE5NGFjZDUxN2IiLCJzdWIiOiJ2YXJ1MTkzMjItZWUiLCJmbXQiOiJYRGNPaGpDNDArQUxqbFlUdGpiT2lBPT0ifQ.SPCRhPb0Ue1PuPDZoJ4TPUdr7Hm7rbROkeLHtxrlVCw");
	    StringBuilder strBuf = new StringBuilder();  
	    
	    //reading JSON response
	    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
        String output = null;  
        while ((output = br.readLine()) != null)  
           strBuf.append(output);
        br.close();
        JSONObject json=new JSONObject(strBuf.toString());
        System.out.println(json.toString());
        JSONObject nestedJson=new JSONObject(json.get("current").toString());
        
        //sending JSP response
        out.println("<div id=\"weather-report\" style=\"padding-left:20%;\">\n" + 
        		"		         <div style=\"background: linear-gradient(to right, #33ccff -28%, #ffffff 132%);width:360px;height:220px;border-radius:20px;\">\n" + 
        		"			             <div id=\"profile-weather\" style=\"width:60px;height:60px;padding-left:15%;\" >\n" + 
        		"					              <div style=\"display:flex;height:60%;\">\n" + 
        		"					                   <img src=\"https://developer.foreca.com/static/images/symbols/"+nestedJson.get("symbol")+".png\" style=\"width:100px;height:80px;padding-top:10px;\">\n" + 
        		"					                   <div style=\"padding-bottom:150px;padding-left:80%;\">\n" + 
        		"					                       <h1 style=\"padding-left:18%;height:10px;\" class=\"temperature\">"+nestedJson.get("temperature")+"&deg;</h1>\n" + 
        		"					                       <h2 style=\"width:50px;height:10px;\" class=\"description\">"+nestedJson.get("symbolPhrase")+"</h2>\n" + 
        		"					                   </div>\n" + 
        		"					        	  </div>\n" + 
        		"					        	  <div style=\"padding-top:100%;width:70px;height:40px;\">\n" + 
        		"						        	      <div style=\"display:flex;\">\n" + 
        		"							        	           <div style=\"width:40px;height:30px\">\n" + 
        		"							        	              <p style=\"height:10px;font-size:20px;\">"+nestedJson.get("relHumidity")+"%<P>\n" + 
        		"							        	              <p style=\"font-size:15px;\">Humidity<P>\n" + 
        		"							        	           </div>\n" + 
        		"								        	       <div style=\"width:40px;height:30px;padding-left:60%;\">\n" + 
        		"								        	           <p style=\"height:10px;font-size:20px;\">"+nestedJson.get("windSpeed")+"km/hr<P>\n" + 
        		"								        	           <p style=\"font-size:15px;\">Wind<P>\n" + 
        		"								        	       </div>\n" + 
        		"								        	       <div style=\"width:40px;height:30px;padding-left:80%;\">\n" + 
        		"								        	           <p style=\"height:10px;font-size:20px;\">"+nestedJson.get("thunderProb")+"<P>\n" + 
        		"								        	           <p style=\"font-size:15px;\">Rain Probability<P>\n" + 
        		"								        	       </div>\n" + 
        		"						        	      </div>\n" + 
        		"					        	  </div>\n" + 
        		"			             </div>\n" + 
        		"		         </div>\n" + 
        		"	      </div>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

    public WeatherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
