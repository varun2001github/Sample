//$Id$
package com.varun.Api.CacheClearImpl;

import java.util.HashMap;
import java.util.List;

import com.ProtoModel.UserModel.User;
import com.varun.Api.Interface.UserApi;
import com.varun.cache.RedisUtil;

public class UserApiCacheClearImpl{
    RedisUtil redis=null;
    private static final String UserClassName=User.class.getName();
    private static final String ListCacheKey="List<"+User.class.getName()+">";
    
    public UserApiCacheClearImpl(){
    	redis=RedisUtil.getInstance();
    }

	public boolean updateUser(User oldObj, User newObj){
		redis.del(UserClassName+oldObj.getUserId());
		// TODO Auto-generated method stub
		return false;
	}

}
