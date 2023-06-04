//$Id$
package com.varun.Api.Interface;

import java.util.List;

import com.ProtoModel.UserModel.Mobile;

public interface CachableMobileApi {
	
	Integer getIdByMobile(String mobileno);
	
	boolean checkMobile(Long mobileno);
	
	List<Mobile> getMobileById(Integer id);

}
