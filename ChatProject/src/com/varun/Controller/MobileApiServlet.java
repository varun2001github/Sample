package com.varun.Controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.varun.Api.EmailTableApi;
import com.varun.Api.MobileTableApi;
import com.varun.Model.DataObject;
import com.varun.Model.MobileTableModel;
import com.varun.Orm.OrmImp;

/**
 * Servlet implementation class MobileApiServlet
 */
public class MobileApiServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private OrmImp ormObj=null;
	private static final Logger logger=Logger.getLogger(MobileApiServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileApiServlet(){
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	   /MObileApi/getMobile/1
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		// TODO Auto-generated method stub
		System.out.println("Mobile servlet called");
		// TODO Auto-generated method stub
		String path=request.getPathInfo().substring(1);
		String[] segments=path.split("/");
   	    JSONArray jsonArray=new JSONArray();
		MobileTableApi api=new MobileTableApi(ormObj);

		//getAll
		if(segments[0].equals("getMobile")){
			if(segments.length>1){
		    	ormObj=new OrmImp();
				try{
					Integer userId=Integer.parseInt(segments[1]);
					List<MobileTableModel> emails=api.getMobileById(userId);
					for(MobileTableModel object:emails){
						jsonArray.put(object.getDataObject().getDataMap());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		ormObj.close();
		response.getWriter().println(jsonArray.toString());
	}//@WebServlet("/MobileApi/*")


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
