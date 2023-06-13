//$Id$
package com.varun;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadProperties {
	public static void main(String args[]) throws IOException {
	    FileReader reader=new FileReader("/home/local/ZOHOCORP/varun-pt6303/ZIDE Workspace/JavaProject/sampleprop.properties");
        Properties props=new Properties();
        props.load(reader);
        Map m=props;
//        Map<String, String> hmap=(Map<String, String>)m;
        System.out.println("map -> "+m.get("em"));
	}
}
