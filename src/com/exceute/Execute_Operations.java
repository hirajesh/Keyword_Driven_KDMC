package com.exceute;


import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.UI.UI_Operations;
import com.excelfile.Read_Excel_File;
import com.propertiesfile.Read_Properties_File;

public class Execute_Operations {

	WebDriver driver;
	int rowcount;
	Row row;
	
	@Test
	public void RUNSCRIPTS() throws Exception {

		System.setProperty("webdriver.chrome.driver","D:\\Selenium\\New folder\\chromedriver.exe"); 
        driver = new ChromeDriver();
        driver.manage().window().maximize();
		
		Read_Excel_File readdata=new Read_Excel_File();
		Read_Properties_File readproperty=new Read_Properties_File();
		UI_Operations uioperations=new UI_Operations(driver);
		
		Properties readpropertiesdata=readproperty.Read_Properties();
		Sheet excelsheet=readdata.ExcelReturn();
		
	
		rowcount=excelsheet.getLastRowNum()-excelsheet.getFirstRowNum();
		System.out.println("count is : "+rowcount);
		
		
		for (int i=1 ; i < rowcount+1; i++) 
		{
		  row=excelsheet.getRow(i);
		  
		  if(row.getCell(0).toString().trim().length()==0)
		  {
			  uioperations.UI(readpropertiesdata, row.getCell(1).toString(),  row.getCell(2).toString(),  row.getCell(3).toString(), row.getCell(4).toString()); 
			  Thread.sleep(2000);
		  }
		  else
		  {            
	        System.out.println(row.getCell(0).toString());
	      }
		
			
		}
		
		driver.close();
		
	}

	
}
