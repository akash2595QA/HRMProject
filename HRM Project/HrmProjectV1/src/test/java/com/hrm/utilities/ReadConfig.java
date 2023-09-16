package com.hrm.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig 
{
	Properties pro;
	
	public ReadConfig()
	{
		File file = new File("./Configuration/configs.properties");
		
		try {
			FileInputStream fs = new FileInputStream(file);
			pro = new Properties();
			
			try {
				pro.load(fs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public String getPageUrl()
	{
		return pro.getProperty("URL");
	}
	
	public String getUsername()
	{
		return pro.getProperty("username");
	}
	
	public String getPassword()
	{
		return pro.getProperty("password");
	}
	
	public String getBrowserName()
	{
		return pro.getProperty("browser");
	}
	
}
