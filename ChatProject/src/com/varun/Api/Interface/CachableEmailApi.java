//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Email;

public interface CachableEmailApi{
	
	Integer getIdByEmail(String email);

	List<Email> getEmailById(Integer id);
	
	Boolean checkEmail(String email);

}
