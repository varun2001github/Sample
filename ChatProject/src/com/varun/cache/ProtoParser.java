//$Id$
package com.varun.cache;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;

public class ProtoParser{
    public static Message parseProto(Parser<?> messageParser,byte[] serialized){
    	try{
    		Message message=(Message)messageParser.parseFrom(serialized);
			return message;
		}catch(InvalidProtocolBufferException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
