package com.varun.Security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
//import com.varun.dao.chatDao.ChatDaoImp;


public class CookieEncrypt{
	protected String SecretKey="616fe5463a758ab3b79dea2e86122e2fa497e48ce027335bd9fe74fc900b405";
	private Cipher cipher; 
	private SecretKey key = new SecretKeySpec(Arrays.copyOf(SecretKey.getBytes(),16),"AES");
	
	public String encryptCookie(String sessionid) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
			JSONObject json=new JSONObject();
			json.put("sessionid",sessionid);
	        EncryptionHandler handler=new EncryptionHandler();
	        return handler.encrypt(json);
	}
	
//    public UserData getClaims(String sessionInfoEncrypted) throws Exception{
//    	if(sessionInfoEncrypted!=null && !sessionInfoEncrypted.equals("")){
//	    	String sessionid;
//	        JSONObject json;
//	        EncryptionHandler handler=new EncryptionHandler();
//            json=handler.decrypt(sessionInfoEncrypted);
//	        sessionid=json.getString("sessionid");
////	        ld.setSessionid(json.getString("sessionid"));
////	        ChatDaoImp con=new ChatDaoImp();
////	        ld=con.getClaims(sessionid);
//////	        System.out.println("decoded: "+sessionid+"from db"+ld.getSessionid());
////		    if(ld!=null && sessionid.equals(ld.getSessionid())){
////	            return ld;
////		    }else{
////		    	System.out.println("sessioninfo ld claim null");
////		    	return null;
////		    } 
//	   
//    	}
//    	return null;
//     }
   

}
