//$Id$
package com.varun.Api.Interface;

import com.ProtoModel.UserModel.Session;

public interface CachableSessionApi {
    
	Session getObjectBySession(String sessionid);

}
