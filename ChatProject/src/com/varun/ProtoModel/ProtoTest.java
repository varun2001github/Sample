//$Id$
package com.varun.ProtoModel;

import java.util.*;

public class ProtoTest {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		UserModel.EmailTableModel emailModel1=UserModel.EmailTableModel.newBuilder()
											.setUserId(1)
											.setEmailid("varun@gmail.com")
											.setIsPrimary(0)
											.setIsVerified(1).build();
		
		UserModel.EmailTableModel emailModel2=UserModel.EmailTableModel.newBuilder()
				.setUserId(1)
				.setEmailid("varunsashi@gmail.com")
				.setIsPrimary(0)
				.setIsVerified(1).build();
		
		List<UserModel.EmailTableModel> emails=new ArrayList<UserModel.EmailTableModel>();
		emails.add(emailModel1);
		emails.add(emailModel2);
		
		//Main data object 
		UserModel.UserinfoTableModel userModel=UserModel.UserinfoTableModel.newBuilder()
				                               .setUserId(1)
				                               .setUserName("varun")
				                               .setGender("male")
				                               .setCountry("India")
				                               .addAllEmailTableObj(emails).build();
		
		System.out.println(userModel.getUserId()+" "
		                   +userModel.getUserName()+" "
				           +userModel.getEmailTableObjList().get(1).getEmailid());
	}

}
