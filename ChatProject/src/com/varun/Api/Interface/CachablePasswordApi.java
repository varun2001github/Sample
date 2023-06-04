//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Password;

public interface CachablePasswordApi{
   
	Password getPassById(Integer uid);

	Password getPassByloginid(String loginid);

	List<Password> getAllPassById(Integer uid);
}
