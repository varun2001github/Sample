//$Id$
package com.varun.Api.Interface;

import com.ProtoModel.UserModel.Session;

public interface SessionApi extends CachableSessionApi{

	boolean createSession(Integer userid, String sessionid);

	void deleteSession(String sessionid);

	void deleteExpiredSession(Integer uid);

}