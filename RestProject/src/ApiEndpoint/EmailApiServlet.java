package ApiEndpoint;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import com.varun.Api.EmailTableApi;
import com.varun.Model.DataObject;
import com.varun.Model.EmailTableModel;
/**
 * Servlet implementation class EmailApiServlet
*/
public class EmailApiServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    public static HashMap<String,UrlMap> MethodMap=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
   
    public EmailApiServlet(){
        super();
        
        if(MethodMap==null){
        	MethodMap=new HashMap<String,UrlMap>();
        	MethodMap.put("/EmailApi/getEmail",new UrlMap("OrmPractice.Model.UserLogin","getUsername","String"));
    	}
        // TODO Auto-generated constructor stub
    }
    
    //For handling url mapped methods
    private Object ApiInvoke(String url,Object... params) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
    	String pack="";
		String method="";
		String methodType="";
		UrlMap urlMapObject=MethodMap.get(url);
		
        try{
    		System.out.println("api came in");       		                                                 

        	if(urlMapObject!=null){
    			pack=urlMapObject.getPackageName();
       		    method=urlMapObject.getMethod();
       		    methodType=urlMapObject.getMethodType();
       		    
    	    	Class<?> ApiClass=Class.forName(pack);
    	    	
    	    	Object result=null;
    	    	Class<?>[] paramClassArray=null;
    	    	if(params!=null){
    	    		System.out.println("param not null");
    	    		paramClassArray=new Class<?>[params.length];
    	    		for(int i=0;i<params.length;i++){
                    	paramClassArray[i]=params[i].getClass();
                    }
    	    	}
    		    result=ApiClass.getMethod(method,paramClassArray).invoke(ApiClass.newInstance(),params);
                System.out.println("result "+result);
                return result;
    	    }
        }catch(Exception e){
        	e.printStackTrace();
        }
    	return null;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)  
	 */
    /*URLS :  /EmailApi/getEmail
                       /getEmail/{id}
    */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("called");
		// TODO Auto-generated method stub
		String path=request.getPathInfo().substring(1);
		Map<String,String[]> queryParamMap=request.getParameterMap();
		String[] segments=path.split("/");
   	    JSONArray jsonArray=new JSONArray();
		EmailTableApi api=new EmailTableApi();
		//getAll
		if(segments[0].equals("getEmail")){
			if(segments.length==1){
				List<DataObject> emails=api.getAllEmail();
				for(DataObject object:emails){
					jsonArray.put(object.getDataMap());
				}
			}else if(segments.length>1){
				try{
					Integer userId=Integer.parseInt(segments[1]);
					List<DataObject> emails=api.getEmailByUid(userId);
					for(DataObject object:emails){
						jsonArray.put(object.getDataMap());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		response.getWriter().println(jsonArray.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
