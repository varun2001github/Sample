//$Id$
package com.varun.Api.Interface;

import java.util.HashMap;
import java.util.List;

import com.ProtoModel.UserModel.User;

public interface CachableUserApi{
	
	User getUser(Integer userid);
	
	User getUser(String email);

	List<User> fetchChatList(Integer uid);
	
	HashMap<Integer, User> getUsers(List<Integer> userIds);
}
