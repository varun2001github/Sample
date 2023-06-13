//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Mobile;

public interface MobileApi extends NonCachable,CachableMobileApi{

	boolean addMobile(Integer uid, Long mobile);

	boolean updateMobile(Mobile oldMobileObject, Mobile newMobileObject);


}