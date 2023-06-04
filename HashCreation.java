package com.jdbctrial;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class HashCreation{
	static String a1="93aed34f3f0a8450",b1="b6dd4f040c288392",c1="f32d7096c582ca96",d1="7a04d96ea1adc5a1";
	static BigInteger a=new BigInteger(a1,16);
	static BigInteger b=new BigInteger(b1,16);
	static BigInteger c=new BigInteger(c1,16);
	static BigInteger d=new BigInteger(d1,16);
	
    public static BigInteger rro(BigInteger v, int k){
		   char[] s=v.toString(2).toCharArray();
	//	   System.out.println("bit lens"+s.length);
		   for(int j=1;j<=k;j++){
		      for(int i=s.length-1;i>0;i--){
			    char ts=s[i-1];
			    s[i-1]=s[i];
			    s[i]=ts;
		      }
		   }
		   String m=String.valueOf(s);
		   return new BigInteger(m,2);
    }
   
   public static BigInteger lro(BigInteger v, int k){
		   char[] s=v.toString(2).toCharArray();
   	//	   System.out.println("bit lens"+s.length);
		   for(int j=0;j<k;j++){
		      for(int i=0;i<s.length-1;i++){
			    char ts=s[i];
			    s[i]=s[i+1];
			    s[i+1]=ts;
		      }
		   }
		   String m=String.valueOf(s);
		   return new BigInteger(m,2);
   }
   
   public static BigInteger p1(BigInteger w,BigInteger d,BigInteger b){
//		   BigInteger big=rro(w,5).xor(lro(d,6)).xor(lro(b,4));
//		   System.out.println(big.toString(16));
	   
	       // w xor rl6(d) xor rl4(b)
		   return w.xor(lro(d,6)).xor(lro(b,4));
   }
   
   public static  BigInteger p2(BigInteger w,BigInteger a,BigInteger c){
//		   BigInteger big=rro(w,4).xor(lro(a,6)).xor(rro(c,8));
//		   System.out.println(big.toString(16));
	   
	       // w xor (rl6(a) xor rr8(c)
		   return w.xor(lro(a,6)).xor(rro(c,8));
   }
   
   
   public static  BigInteger ch(BigInteger a,BigInteger b,BigInteger c){
//		   BigInteger big=(a.and(b)).xor(a.not().and(c));
//		   System.out.println(big.toString(16));
	   
	       // (a&b) xor (!a & c) 
		   return (a.and(b)).xor(a.not().and(c));
   }
   
   public static  BigInteger summation(BigInteger w){
//		   BigInteger big=rro(w,15).xor(lro(w,23)).xor(rro(w,3));
//		   System.out.println(big.toString(16));
	   
	       // rr15(w) xor rl23(w) xor rr3(w)
		   return rro(w,15).xor(lro(w,23)).xor(rro(w,3));
   }
   
   public static void process(BigInteger w){
		   a=p1(a,d,b).xor(ch(a,d,b)).xor(summation(a)).xor(w);
		   b=a;
		   c=b.xor(p2(a,b,d)).xor(summation(b)).xor(w);
		   d=c;
//		   System.out.println("a length "+a.bitLength()+" b length "+b.bitLength()+" c length "+c.bitLength()+" d length "+d.bitLength());
   }
   public static String createWord(String s1,String s2,String s3,String s4) {
		   BigInteger w1=new BigInteger(s1,2);
		   BigInteger w2=new BigInteger(s2,2);
		   BigInteger w3=new BigInteger(s3,2);
		   BigInteger w4=new BigInteger(s4,2);
		   // w[i-1] XOR (rr1(w[i-2]) XOR rr1(w[i-2]) XOR lr7(w[i-2])) XOR w[i-3] XOR (rr1(w[i-4]) XOR rr1(w[i-4]) XOR lr7(w[i-4])) 
		   String s=( w1.xor(rro(w2,3).xor(rro(w2,12)).xor(w3).xor(lro(w4,5)) ).toString(2));
		   return s;
   }
   
   public static String digest(String hash){
	   
		   int WordsCount=hash.length()/64;
		   System.out.println("NOW"+WordsCount);
		   String[] wordpass=new String[WordsCount];
		   String[] word=new String[80];
	
		   for(int i=0;i<WordsCount;i++){
			   wordpass[i]=hash.substring(i*64,(i+1)*64);
			   if((i+1)%4==0){
				  for(int j=0;j<(80);j++){
					   if(j<4) {
						  word[j]=wordpass[j];
					   }else{
						   word[j]=createWord(word[j-1],word[j-2],word[j-3],word[j-4]);
//						   System.out.println("word"+j+" "+word[j]);
					   }
				       process(new BigInteger(word[j],2));
				   }
			   }
		   }
		   return a.toString(16)+b.toString(16)+c.toString(16)+d.toString(16);
   }
   
   public static String salt(){
	    byte[] salt=new byte[10];
	    Random random=new SecureRandom();
	    random.nextBytes(salt);
        BigInteger a=new BigInteger(salt);
        System.out.println("salt "+a.toString().length());
	    return a.toString(16);
  }
   
   public static String hash(String s){
	   String hash="";
	   byte[] bt=s.getBytes();

	   BigInteger b=new BigInteger(bt);
	   
	   //Byte array to binary string
	   hash+=b.toString(2);

	   //binary length
	   String lenorg="";
	   lenorg+=hash.length();
	   BigInteger len=new BigInteger(lenorg,10);
	   
//	   System.out.println("hash len before pad"+hash.length());
	   
	   //padding
	   hash+="1";
       int total=256,NOPad=0,i=0;
       
       //find required after padding length
	   while(true){
		   NOPad=(total-64);
		   if((total-64)>hash.length()) {
			   break;
		   }
		   total*=2;
	   }
	   
	   //
	   i=total-64-hash.length();
	   while(i!=0){
		   hash+="0";
		   i--;
	   }
	   
	   i=64-len.toString(2).length()%64;
//	   System.out.println(i);
	   
	   //Append length
       while(i>0){
		   hash+="0";
		   i--;
	   }
	   hash+=len.toString(2);
	   return digest(hash);
   }
//Main Code   
   public static void main(String args[]){
		   String str="asasfddasdfsgd";
//         String cpy="e4670e1f2eee2afce4670e1f2eee2afc5ec9d06b0af640ab5ec9d06b0af640ab";
		   long startHash = System.currentTimeMillis();
		   String sal=salt();
		   String hashval=hash(sal+str);
		   System.out.println("HASH VALUE IS :"+hashval);
//		   System.out.println("hashlen"+hashval.length());
		   long endHash = System.currentTimeMillis();
		   System.out.println("time"+(endHash-startHash));
	
	 }
}
