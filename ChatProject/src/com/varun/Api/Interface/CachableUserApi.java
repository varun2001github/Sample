//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.User;

public interface CachableUserApi{
	
	User getUser( Integer userid);

	User getUserSample( Integer userid);

	List<User> fetchChatList( Integer uid);

    public Boolean getBoolean();
	
}
