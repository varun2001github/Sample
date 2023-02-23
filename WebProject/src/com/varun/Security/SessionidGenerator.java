package com.varun.Security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SessionidGenerator {
    public static String Generate() {
        	byte[] salt=new byte[20];
        	SecureRandom r=new SecureRandom();
        	r.nextBytes(salt);
        	BigInteger in=new BigInteger(salt);
        	return in.toString(16);
    }
}
