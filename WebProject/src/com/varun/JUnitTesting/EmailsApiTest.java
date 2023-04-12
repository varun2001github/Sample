package com.varun.JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.varun.Api.EmailTableApi;
import com.varun.Model.AuditTableModel;
import com.varun.Model.EmailTableModel;
import com.varun.Orm.OrmImp;

@RunWith(Parameterized.class)
class EmailsApiTest {
	private OrmImp orm=new OrmImp(new AuditTableModel());
	private EmailTableApi emailApi=new EmailTableApi(orm);
    
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#checkEmailTestCase")
	void checkEmailTest(String email,Boolean isValid){
		assertEquals(isValid,emailApi.checkEmail(email));
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#getIdByEmailTestCase")
	void getIdByEmailTest(String email,Integer uid){
		assertEquals(uid,emailApi.getIdByEmail(email));
	}

	@ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#getEmailByIdTestCase")
	void getEmailByIdTest(Integer uid,String email){
		if(email!=null){
			assertNotNull(emailApi.getEmailById(uid));
		}else{
			assertNull(emailApi.getEmailById(uid));
		}
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#updateEmailTestCase")
	void updateEmail(Integer userId,EmailTableModel oldEmail,EmailTableModel newEmail,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApi.updateEmail(userId,oldEmail,newEmail));
		orm.rollback();
	}
	
	@ParameterizedTest
    @MethodSource("com.varun.JUnitTesting.TestCase#InsertEmailTestCase")
	void insertEmail(Integer userId,String email,Boolean expected){
		orm.beginTransaction();
		assertEquals(expected,emailApi.addEmail(userId, email));
		orm.rollback();
	}
}
