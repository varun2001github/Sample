package com.varun.JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.varun.Api.EmailTableApi;
import com.varun.Model.AuditModel;
import com.varun.Model.EmailModel;
import com.varun.Orm.OrmImp;

public class EmailsApiTest{
	private OrmImp orm=new OrmImp(new AuditModel());
	private EmailTableApi emailApi=new EmailTableApi(orm);
    
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#checkEmailTestCase")
	public void checkEmailTest(String email,Boolean isValid){
		assertEquals(isValid,emailApi.checkEmail(email));
	}
	
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#getIdByEmailTestCase")
	public void getIdByEmailTest(String email,Integer uid){
		assertEquals(uid,emailApi.getIdByEmail(email));
	}

	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#getEmailByIdTestCase")
	public void getEmailByIdTest(Integer uid,String expectedEmail){
		if(expectedEmail!=null){
			assertNotNull(emailApi.getEmailById(uid));
		}else{
			assertNull(emailApi.getEmailById(uid));
		}
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#updateEmailTestCase")
	public void updateEmail(EmailModel oldEmail,EmailModel newEmail,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApi.updateEmail(oldEmail,newEmail));
		orm.rollback();
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#InsertEmailTestCase")
	public void insertEmail(Integer userId,String email,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApi.addEmail(userId, email));
		orm.rollback();
	}
	
}
