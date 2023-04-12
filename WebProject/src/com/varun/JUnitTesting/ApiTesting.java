package com.varun.JUnitTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.varun.Api.*;
import com.varun.Orm.OrmImp;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
public class ApiTesting{
    
	private OrmImp orm;
	private EmailTableApi emailApi;
	private MobileTableApi mobileApi;
	private UserTableApi userApi;
	private GroupTableApi groupApi;

	@BeforeEach
	public void setUp(){
		orm=new OrmImp();
	}
	
    @Test
//  @ValueSource(strings = {"varunsashi@gmail.com","amesh@gmail.com","suresh@gmail.com"})
    public void EmailApiTest(){
    	emailApi=new EmailTableApi(orm);
    	Assertions.assertAll("CorrectEmail",
    			() -> Assertions.assertTrue(emailApi.checkEmail("varunsashi@gmail.com"),"EM2"),
    			() -> Assertions.assertTrue(emailApi.checkEmail("ramesh@gmail.com"),"EM3")
    	);
    	
    	Assertions.assertAll("WrongEmail",
    			() -> Assertions.assertFalse(emailApi.checkEmail("ramesh@gmail.com"),"EM1"),
    			() -> Assertions.assertFalse(emailApi.checkEmail("vaunsashi@gmailcom"),"EM2")
    	);
    }
    
    @ParameterizedTest
    @ValueSource(strings ={"1234567892","1234123412","9323943452"})
    public void MobileApiTest(String mobileNo){
    	mobileApi=new MobileTableApi(orm);
		Long mobileno=Long.parseLong(mobileNo);
    	Assertions.assertTrue(mobileApi.checkMobile(mobileno),"Mobile check for "+mobileno);
    }
}
