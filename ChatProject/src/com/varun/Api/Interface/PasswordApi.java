//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Password;

public interface PasswordApi extends CachablePasswordApi{	

	boolean addPass(Integer userid, String passSalt, String passHash);

	boolean updatePassById(Password oldObj, Password newObj, Integer uid);

}