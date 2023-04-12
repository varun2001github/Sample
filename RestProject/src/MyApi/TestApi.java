package MyApi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@Path("/myapi")
public class TestApi{
	  
      static int i=1;
      
      @GET
      @Path("/getId/{num}")
      @Produces(MediaType.APPLICATION_JSON)
      public String print(@PathParam("num") int j){
    	  JSONObject ob=new JSONObject();
    	  ob.put("key",j+1+"");
    	  return ob.toString();
      }
}
