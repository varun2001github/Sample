package com.varun.JUnitTest;

import com.ProtoModel.UserModel.Email;

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
			return new Object[]{
				//valid cases 
		    		new Object[] {Email.newBuilder().setUserId(1).setEmailid("varunsashi@gmail.com").build(),Email.newBuilder().setUserId(1).setEmailid("varun@123.com").build(),true},
		    		new Object[] {Email.newBuilder().setUserId(1).setEmailid("varunsashi@gmail.com").build(),Email.newBuilder().setUserId(1).setEmailid("varun.sashi@zohocorp.com").build(),true},
		    		new Object[] {Email.newBuilder().setUserId(2).setEmailid("ramesh@gmail.com").build(),Email.newBuilder().setUserId(2).setEmailid("ramesh@123.com").build(),true},
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(1,"varun.sashi@zohocorp.com",null,null),true},
//		    		new Object[] {new EmailModel(2,"ramesh@gmail.com",null,null),new EmailModel(2,"ramesh@123.com",null,null),true},
		    		
	    		//invalid cases
		    		new Object[] {Email.newBuilder().setUserId(1).setEmailid("@.com").build(),Email.newBuilder().setUserId(1).setEmailid("varun@123.com").build(),false},
		    		new Object[] {Email.newBuilder().setUserId(1).setEmailid("varunsashi@gmail.com").build(),Email.newBuilder().setUserId(1).setEmailid("@.com").build(),false},
		    		new Object[] {Email.newBuilder().setUserId(1).build(),Email.newBuilder().setUserId(1).setEmailid("varunsashi@gmail.com").build(),false},
		    		new Object[] {Email.newBuilder().setUserId(-1).setEmailid("varunsashi@gmail.com").build(),Email.newBuilder().setUserId(1).setEmailid("varunsashi@gmail.com").build(),false},
		    		new Object[] {Email.newBuilder().build(),Email.newBuilder().build(),true},
		    		new Object[] {Email.newBuilder().build(),Email.newBuilder().build(),true},
//		    		new Object[] {new EmailModel(1,"@.com",null,null),new EmailModel(1,"varun@123.com",null,null),false},                          //invalid old email regex
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(1,"@.com",null,null),false},                  //invalid new email regex
//		    		new Object[] {new EmailModel(1,"a",null,null),new EmailModel(1,"sdf",null,null),false},                                       //invalid regex both
//		    		new Object[] {new EmailModel(-1,"varunsashi@gmail.com",null,null),new EmailModel(null,"varun@123.com",null,null),false},      //invalid userId
//		    		new Object[] {new EmailModel(245,"varunsashi@gmail.com",null,null),new EmailModel(null,"varun@123.com",null,null),false},     //invalid userId
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(null,"varunsashi@gmail.com",null,null),false},//same email
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(null,"",null,null),false},                    // new email=""
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(null,"        ",null,null),false},            // new email="     "
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),new EmailModel(null,null,null,null),false},                  // new email= null
//		    		new Object[] {new EmailModel(1,null,null,null),new EmailModel(2,null,null,null),false},                                       // new email= null
//		    		new Object[] {new EmailModel(null,null,null,null),new EmailModel(null,null,null,null),false},                                 // new email= null
//	    		    new Object[] {new EmailModel(1,"suresh@om",null,null),new EmailModel(1,"varun@123.com",null,null),false},                     // incorrect old email
//		    		new Object[] {null,new EmailModel(1,"varunsashi@gmail.com",null,null),false},
//		    		new Object[] {new EmailModel(1,"varunsashi@gmail.com",null,null),null,false},
//		    		new Object[] {null,null,false}
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
