//$Id$
package com.varun.Api.Interface;

import java.util.List;
import com.ProtoModel.UserModel.Email;
import com.ProtoModel.UserModel.User;

public interface CachableApi extends CachableUserApi,CachableEmailApi,CachableMobileApi,CachablePasswordApi,CachableSessionApi{
}
