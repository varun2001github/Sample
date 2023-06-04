//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.User;

public interface UserApi extends CachableUserApi{

	boolean updateUser(User oldObj, User newObj);

	Integer addUser(User userObject);
	
    public int getInt();
	
	public void print();
	
}