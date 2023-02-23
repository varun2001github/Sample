package com.varun.Security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;


public class EncryptionHandler{
	protected String SecretKey="616fe5463a758ab3b79dea2e86122e2fa497e48ce02335bd9fe74fc900b405";
	private Cipher cipher; 
	
	public String encrypt(JSONObject json) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		cipher = Cipher.getInstance("AES");	
		SecretKey key = new SecretKeySpec(Arrays.copyOf(SecretKey.getBytes(),16),"AES");
        cipher.init(cipher.ENCRYPT_MODE,key);
        String enc=Base64.getEncoder().encodeToString(cipher.doFinal(json.toString().getBytes()));
        return enc;
    }
	
	
//	public String encrypt(String jsonstring) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
//		JSONObject json=new JSONObject(jsonstring);
//		cipher = Cipher.getInstance("AES");	
//        cipher.init(cipher.ENCRYPT_MODE,key);
//        String enc=Base64.getEncoder().encodeToString(cipher.doFinal(json.toString().getBytes()));
//        return enc;
//    }
	public JSONObject decrypt(String encrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
		cipher = Cipher.getInstance("AES");
		SecretKey key = new SecretKeySpec(Arrays.copyOf(SecretKey.getBytes(),16),"AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new JSONObject(new String(plainText));
	}
	
	public String encrypt(JSONObject json,String sessionid) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		cipher = Cipher.getInstance("AES");	
		SecretKey key = new SecretKeySpec(Arrays.copyOf((SecretKey+sessionid).getBytes(),16),"AES");
        cipher.init(cipher.ENCRYPT_MODE,key);
        String enc=Base64.getEncoder().encodeToString(cipher.doFinal(json.toString().getBytes()));
        return enc;
    }
	public JSONObject decrypt(String encrypted,String sessionid) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
		cipher = Cipher.getInstance("AES");
		SecretKey key = new SecretKeySpec(Arrays.copyOf((SecretKey+sessionid).getBytes(),16),"AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new JSONObject(new String(plainText));
	}
}
