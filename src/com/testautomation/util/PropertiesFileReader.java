package com.testautomation.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
	
	public Properties getProperty() throws IOException
	{
		FileInputStream inputStream=null;
        Properties properties = new Properties();
        try {        	 
            properties.load(new FileInputStream("Resource/browser-config.properties"));
            properties.load(new FileInputStream("Resource/testdata-config.properties"));
            properties.load(new FileInputStream("Resource/page-config.properties"));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
       } 
         return properties;	
	}

}
