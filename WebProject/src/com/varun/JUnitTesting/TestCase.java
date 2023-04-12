package com.varun.JUnitTesting;

import java.util.stream.Stream;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.varun.Model.EmailTableModel;

public class TestCase{
	
	public static Object[] checkEmailTestCase(){
		    return new Object[]{
		    	//valid ->      "api param"   and  expected output in these array
	    		new Object[] {"varunsashi@gmail.com",true},
	    		new Object[] {"ramesh@gmail.com",true},
	    		new Object[] {"suresh@gmail.com",true},
	    		
	    		//invalid
	    		new Object[] {"suresh@gmailcom",false}, //dot missed
	    		new Object[] {"sureshgmailcom",false},  //@ symbol missed
	    		new Object[] {"varungmcom",false},     //both missed
	    		new Object[] {"suaaaaaaaaaaaaaaaaaaaaaaaaresh@gmail.com",false}, //size exceed 4 (max lim 30)  
	    		new Object[] {"@.com",false},          //pattern mismatch
	    		new Object[] {"",false},               //empty
	    		new Object[] {null,false}              //null
		    };
    }
	
	public static Object[] getIdByEmailTestCase(){
	    	return new Object[]{
	    			
	    		//valid 
	    		new Object[] {"varunsashi@gmail.com",1},
	    		new Object[] {"ramesh@gmail.com",2},
	    		new Object[] {"suresh@gmail.com",3},
	    		
	    		//invalid
	    		new Object[] {"suaaaaaaaaaaaaaaaaaaaaaaaaresh@gmail.com",null}, //size exceed 4 (max lim 30)  
	    		new Object[] {"suresh@gmailcom",null}, //dot missed
	    		new Object[] {"sureshgmailcom",null},  //@ symbol missed
	    		new Object[] {"varungmcom",null},      //both missed
	    		new Object[] {"@.com",null},           //pattern mismatch
	    		new Object[] {"",null},                //empty
	    		new Object[] {null,null}               //null
		    };
    }
	
	public static Object[] getEmailByIdTestCase(){
	    	return new Object[]{
	    		//valid
	    		new Object[] {1,"varunsashi@gmail.com"},
	    		new Object[] {2,"ramesh@gmail.com"},
	    		new Object[] {3,"suresh@gmail.com"},
	    		
	    		//invalid
	    		new Object[] {0,null},
	    		new Object[] {35689230,null},
	    		new Object[] {-38,null},
	    		new Object[] {null,null}
	    	};
    }  
	
	public static Object[] updateEmailTestCase(){
			//user1 old object
			EmailTableModel oldEmailObject1=new EmailTableModel();
			oldEmailObject1.setEmailid("varunsashi@gmail.com");
			
			//user2 old object
	        EmailTableModel oldEmailObject2=new EmailTableModel();
			oldEmailObject2.setEmailid("ramesh@gmail.com");
			
			//invalid old object
	        EmailTableModel oldEmailObject3=new EmailTableModel();
	        oldEmailObject3.setEmailid("suresh@om");
			
			//sample update
			EmailTableModel updatedEmailObject1=new EmailTableModel();
			updatedEmailObject1.setEmailid("varun@123.com");
			
			//space update
	        EmailTableModel updatedEmailObject2=new EmailTableModel();
			updatedEmailObject2.setEmailid("");
			
			//space update
	        EmailTableModel updatedEmailObject3=new EmailTableModel();
			updatedEmailObject3.setEmailid("      ");
			
			//null update		

			EmailTableModel updatedEmailObject4=new EmailTableModel();
			updatedEmailObject4.setEmailid(null);
		
			return new Object[]{
				//valid cases 
		    		new Object[] {1,oldEmailObject1,updatedEmailObject1,true},
		    		new Object[] {2,oldEmailObject2,updatedEmailObject1,true},
		    		
	    		//invalid cases
	    		    new Object[] {2,oldEmailObject3,updatedEmailObject1,false},  // incorrect old email
		    		new Object[] {1,oldEmailObject1,updatedEmailObject2,false},  // new email=""
		    		new Object[] {1,oldEmailObject1,updatedEmailObject3,false},  // new email="     "
		    		new Object[] {1,oldEmailObject1,updatedEmailObject4,false},  // new email= null
		    		new Object[] {-100,oldEmailObject1,oldEmailObject1,false},   // invalid userid 
		    		new Object[] {0,null,oldEmailObject1,false},
		    		new Object[] {1,null,null,false},
		    		new Object[] {null,null,null,false}
		    };
    }
    
	public static Object[] InsertEmailTestCase(){
    	return new Object[]{
    		
    		//valid      (api params..  , expected output)
    		new Object[] {1,"varun@gmail.com",true},
    		new Object[] {1,"varun.sashi@zohocorp.com",true}, 
    		new Object[] {1,"varunsashi@zohocorp.com",true},  

    		//invalid
    		new Object[] {1,"varunsashi@gmail.com",false},     //already exists
    		new Object[] {2,"ramesh@gmail.com",false},         //already exists
    		new Object[] {3,"suresh@gmail.com",false},         //already exists
    		new Object[] {0,"varunsashi@gmail.com",false},     //userid=0
    		new Object[] {-1,"varunsashi@gmail.com",false},    //userid<0
    		new Object[] {1,"susdfsdfsdfdfgthrtgthfghgfresh@gmail.com",false}, //size exceed 4 (max lim 30)  
    		new Object[] {345,"pranav@gmail.com",false},       //userid invalid foreign key

    		//null & regex pattern mismatch
    		new Object[] {1,"varunsashizohocorp.com",false},  //without @
    		new Object[] {1,"varunsashizohocorp.",false},     //without chars after .
    		new Object[] {1,"@.com",false},                   //invalid pattern
    		new Object[] {null,"ramesh@gmail.com",false},     //Null
    		new Object[] {2,null,false},                      //Null
    		new Object[] {null,null,false},                   //Null
	    };
    }
}
