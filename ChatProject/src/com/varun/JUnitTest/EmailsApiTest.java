package com.varun.JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.ProtoModel.UserModel.Email;
import com.varun.Api.EmailApiImpl;
import com.varun.Api.Interface.EmailApi;
import com.varun.Model.AuditModel;
import com.varun.Orm.OrmImp;

public class EmailsApiTest{
	private OrmImp orm=new OrmImp(new AuditModel());
	private EmailApi emailApiImpl=new EmailApiImpl(orm);
    
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#checkEmailTestCase")
	public void checkEmailTest(String email,Boolean isValid){
		assertEquals(isValid,emailApiImpl.checkEmail(email));
	}
	
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#getIdByEmailTestCase")
	public void getIdByEmailTest(String email,Integer uid){
		assertEquals(uid,emailApiImpl.getIdByEmail(email));
	}

	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#getEmailByIdTestCase")
	public void getEmailByIdTest(Integer uid,String expectedEmail){
		if(expectedEmail!=null){
			assertNotNull(emailApiImpl.getEmailById(uid));
		}else{
			assertNull(emailApiImpl.getEmailById(uid));
		}
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#updateEmailTestCase")
	public void updateEmail(Email oldEmail,Email newEmail,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApiImpl.updateEmail(oldEmail,newEmail));
		orm.rollback();
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTest.TestCase#InsertEmailTestCase")
	public void insertEmail(Integer userId,String email,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApiImpl.addEmail(userId, email));
		orm.rollback();
	}
	
}
