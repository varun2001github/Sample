//$Id$
package com.varun.JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.varun.Dao.UserDao;

@RunWith(Parameterized.class)
class AuthenticationTest{
    private	UserDao userDao=new UserDao();
    
    @ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#emailTestCases")
    void sample(String email){
    	System.out.println("param "+email);
		assertEquals("varunsashi@gmail.com",email);
	}
	
    
    @Test
    @DisplayName("validLoginTest - Test cases:7")
	void ValidLoginTest(){
		assertAll("Valid Login",
				
				//Valid Email and Password
    			() -> assertNotNull(userDao.validate("varunsashi@gmail.com","Varun@123"),"u1 -Email login"),
    			() -> assertNotNull(userDao.validate("ramesh@gmail.com","Ramesh@123")   ,"u2 -Email login"),
    			() -> assertNotNull(userDao.validate("suresh@gmail.com","Suresh@123")   ,"u1 -Email login"),
    			
				//Valid MobileNo and Password
    			() -> assertNotNull(userDao.validate("1234567892","Varun@123") ,"u1 -Mobile login"),
    			() -> assertNotNull(userDao.validate("1234123412","Ramesh@123"),"u1 -Mobile login"),
    			() -> assertNotNull(userDao.validate("1212121212","Suresh@123"),"u1 -Mobile login"),
    			
    			//sql injection ,if 1st user's password known
    			() -> assertNotNull(userDao.validate("asd' OR '1'='1","Varun@123"),"u1- sql injection")
    	);
	}
	
	@Test
    @DisplayName("InvalidLoginTest -Test cases:17")
	void InvalidLoginTest(){
		assertAll("Invalid Login",
				
				//InValid Email
    			() -> assertNull(userDao.validate("varunsashigmail.com","Varun@123"),"u1-wrong email"),
    			() -> assertNull(userDao.validate("ramesh@gmailcom","Ramesh@123")   ,"u2-wrong email"),
    			() -> assertNull(userDao.validate("suresh@gmail.cm","Suresh@123")   ,"u3-wrong email"),
    			
				//InValid Mobile
    			() -> assertNull(userDao.validate("123456792","Varun@123") ,"u1-wrong mobile"),
    			() -> assertNull(userDao.validate("123423412","Ramesh@123"),"u2-wrong mobile"),
    			() -> assertNull(userDao.validate("121212212","Suresh@123"),"u3 -wrong mobile"),
    			
				//InValid mobile and password
    			() -> assertNull(userDao.validate("124567892","Varu@13") ,"u1-wrong mobile & password"),
    			() -> assertNull(userDao.validate("1234123412","Rame23") ,"u2-wrong mobile & password"),
    			() -> assertNull(userDao.validate("122121212","Sur123")  ,"u3 -wrong mobile & password"),
                
				//InValid email and password
    			() -> assertNull(userDao.validate("varunsashimail.com","Va123"),"u1-email & password"),
    			() -> assertNull(userDao.validate("rameilcom","Rames123")   ,"u2-email & password"),
    			() -> assertNull(userDao.validate("suresh@.cm","Sures23")   ,"u3-email & password"),
    			
    			//empty login 
    			() -> assertNull(userDao.validate("","Varun@123"),"u1- Empty loginid"),

    			//empty password
    			() -> assertNull(userDao.validate("varunsashi@gmail.com",""),"u1-Empty password"),

    			//empty loginid and password
    			() -> assertNull(userDao.validate("","")  ,"u1- Empty loginid & password"),
    			
    			//injection for unknown password
    			() -> assertNull(userDao.validate("asd' OR '1'='1",""),"u1- sql injection"),
    			
    			//null passed
    			() -> assertNull(userDao.validate(null,null),"u1- Null loginid & password")

    	);
	}

}
