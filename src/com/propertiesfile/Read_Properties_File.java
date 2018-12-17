package com.propertiesfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Read_Properties_File {
	
	Properties propertiesfile=new Properties();
	
	public Properties Read_Properties() throws IOException 
	{
    
	InputStream Readproperty=new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\com\\propertiesfile\\Allproperties.properties"));	
	propertiesfile.load(Readproperty);
    return propertiesfile;	
	
	}


}
