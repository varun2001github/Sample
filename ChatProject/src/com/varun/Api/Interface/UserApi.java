//$Id$
package com.varun.Api.Interface;

import java.util.List;
import com.ProtoModel.UserModel.User;

//non cachable
public interface UserApi extends CachableUserApi,NonCachable{

	boolean updateUser(User oldObj, User newObj);

	Integer addUser(User userObject);
	
}