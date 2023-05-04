//$Id$
package com.varun.ProtoModel;

import java.util.*;

import com.google.protobuf.InvalidProtocolBufferException;

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
				.setIsPrimary(1)
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
		
		//serialized
		byte[] serialized=userModel.toByteArray();
		
		UserModel.UserinfoTableModel UserModelDsr=null;
		
		//deserialized
		try {
			UserModelDsr=UserModel.UserinfoTableModel.parseFrom(serialized);
		}catch(InvalidProtocolBufferException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("---Deserialized---");
		System.out.println("Userid:"+UserModelDsr.getUserId()+"\nUsername: "
		                   +UserModelDsr.getUserName());
        System.out.println("Email objects: ");
        for(UserModel.EmailTableModel emailObj:UserModelDsr.getEmailTableObjList()) {
        	System.out.println("      "+emailObj.getUserId()+" "+emailObj.getEmailid());
        }
	}

}
