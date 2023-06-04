//$Id$
package practice;

import com.ProtoModel.UserModel.User;
import com.google.protobuf.InvalidProtocolBufferException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest{

	public static void main(String args[]) {
		User user=User.newBuilder().setCountry("india").build();
	    JedisPool jpool=new JedisPool("localhost", 6379);
	    try(Jedis jedis = jpool.getResource()){
	        jedis.set("a".getBytes(),user.toByteArray());
	        System.out.println( "parsed " +User.parseFrom(jedis.get("a".getBytes())));
	    }catch(InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
