package com.varun.Orm;

import java.util.logging.Logger;
import com.varun.Logger.LoggerUtil;


public class CriteriaBuilder{
	 String condition="";
	 private static final Logger logger=LoggerUtil.getLogger(CriteriaBuilder.class);

	    
	    
	    public <T> CriteriaBuilder addGreaterThan(String column,T value){
	    	if(!value.getClass().getSimpleName().equals("String")) {
	    		condition+=column+">"+value+" ";
	   	    }else {
	   		     condition+=column+">'"+value+"' ";
	      	}
	     	return this;
	    }
	    public <T> CriteriaBuilder addLessThan(String column,T value){

	    	if(!value.getClass().getSimpleName().equals("String")) {
	    		condition+=column+"<"+value+" ";
	  	    }else {
	  	    	condition+=column+"<'"+value+"' ";
	     	}
	    	return this;
	    }
	    public <T> CriteriaBuilder addEquals(String column,T value){

	    	if(!value.getClass().getSimpleName().equals("String")) {
	    		condition+=column+"="+value+" ";
	 	    }else {
	 	    	condition+=(column+"='"+value+"'");
	    	}
	      	return this;
	    }

	    public <T> CriteriaBuilder addNotEquals(String column,T value){

	    	if(!value.getClass().getSimpleName().equals("String")) {
	    		condition+=column+"!="+value+" ";
	 	    }else {
	 	    	condition+=(column+"!='"+value+"'");
	    	}
	      	return this;
	    }
	    
        @SuppressWarnings("rawtypes")
        public CriteriaBuilder addIn(String col,OrmImp ormNested){
    		condition+=col+" IN ("+ormNested.getQuery()+")";
            return this;
        }
        
        public <T> CriteriaBuilder addNestedCondition(CriteriaBuilder nested){
    		condition+="("+nested.getCriteria()+")";
            return this;
        }
        
        public CriteriaBuilder addJoinCondition(String col1,String col2){
        	condition+=" ON "+col1+"="+col2;
            return this;
        }
        public <T> CriteriaBuilder AndEquals(String column,T value){
        	condition+=" AND "+column+"="+value;
          	return this;
        }
        
        public CriteriaBuilder Or(CriteriaBuilder cr){
        	condition+=" OR "+cr.getCriteria();
          	return this;
        }
        public String getCriteria() {
    	    String condition=this.condition;
    	    this.condition="";
    	    return condition;
        }
}
