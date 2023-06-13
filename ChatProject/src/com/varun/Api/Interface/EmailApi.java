//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Email;

public interface EmailApi extends NonCachable,CachableEmailApi{

	Boolean addEmail(Integer uid, String email);

	boolean updateEmail(Email oldEmail, Email newEmail);

}