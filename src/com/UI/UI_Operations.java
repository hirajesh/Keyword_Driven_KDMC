package com.UI;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.excelfile.Read_Excel_File;
import com.gargoylesoftware.htmlunit.javascript.host.Window;


public class UI_Operations {

	String CMORNO, PO_Rno;
    Read_Excel_File excelfile=new Read_Excel_File();
    Sheet sheet;
    XSSFWorkbook Workbook;
    Row row,row1;
    Object obj;
	
	public UI_Operations(WebDriver driver) {
		super();
		this.driver = driver;
	}

	WebDriver driver;
	 Cell cell,cell1;
	

	public void UI(Properties readproperty, String Operation, String Objectname, String ObjectType, String values)
			throws Exception {

		switch (Operation.toUpperCase().toString()) {
		case "CLICK":
			driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)).click();
			Thread.sleep(2000);
			break;
		case "DYNAMIC_CLICK":
			driver.findElement(By.id(values.split("\\.")[0].trim())).click();
			Thread.sleep(2000);
			break;
		case "SETTEXT":
			driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)).sendKeys(values);
			Thread.sleep(2000);
			break;
		case "GOTOURL":
			driver.get(readproperty.getProperty(values));
			break;
		case "GETTEXT":
			driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)).getText();
			break;
		case "LINKTEXT":
			driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)).getText();
			break;
		case "SELECT":
			Select select1 = new Select(driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)));
			select1.selectByIndex(Integer.parseInt(values.split("\\.")[0].trim()));	
			break;	
		case "MULTI-SELECT":
			Thread.sleep(2000);
			Select categorys = new Select(driver.findElement(By.id("optcategory")));
			categorys.selectByIndex(1);
			Thread.sleep(3000);
			request(readproperty, Objectname, ObjectType);
			break;
		case "CMOSELECT":
			
			while(true)
			{
				    JavascriptExecutor scroll = (JavascriptExecutor) driver;
					scroll.executeScript("window.scrollBy(0,100)", "");
				try {
					driver.findElement(By.id(CMORNO)).click();
				    break;
				} catch (Exception e) {
					continue;
				}	
			}
			
			Select selects = new Select(driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)));
			selects.selectByIndex(Integer.parseInt(values));
			break;
			
		case "CALLFUNCTION":
	 
			try {
			    UI_Operations yourClass = new UI_Operations(driver);
			    Method method = UI_Operations.class.getMethod(values);
			    method.invoke(yourClass);
			} catch (Exception e) {
			    e.printStackTrace();
			}
			break;
		}

	}

	public By return_the_objects(Properties readproperty, String Objectname, String ObjectType) throws Exception {
		if (ObjectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(readproperty.getProperty(Objectname));
		} else if (ObjectType.equalsIgnoreCase("ID")) {
			return By.id(readproperty.getProperty(Objectname));
		} else if (ObjectType.equalsIgnoreCase("LINKTEXT")) {
			return By.linkText(readproperty.getProperty(Objectname));
		} else {
			throw new Exception("WRONG OBJECT");
		}

	}
	
	public void excelwrite(int rownumber, int cellnumber,String values) throws IOException
	{
		row=Read_Excel_File.sheet.getRow(rownumber);
		 cell = row.createCell(cellnumber);
 	     cell.setCellValue(" "); 
		
		if (cell == null)
		{
		    cell = row.createCell(rownumber);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(CMORNO);
		}
	    else
          {
	       
        	    cell = row.createCell(cellnumber);
        	    cell.setCellValue(" "); 
    			cell.setCellType(Cell.CELL_TYPE_STRING);
    			cell.setCellValue(CMORNO); 
          }
	  
		FileOutputStream output=new FileOutputStream("D:\\Eclipse workspace\\Keyword_Driven_KDMC\\K.xlsx");
		Read_Excel_File.workbook.write(output);
		output.close();
		

	}
	public void request(Properties readproperty,String Objectname,String ObjectType) throws Exception 
	{
		Select select = new Select(
				driver.findElement(this.return_the_objects(readproperty, Objectname, ObjectType)));
		List numberoptions = select.getOptions();
		for (int i = 1; i < numberoptions.size(); i++) {

			while (true) {
				try {
					Select category = new Select(driver.findElement(By.id("optcategory")));
					category.selectByIndex(5);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					continue;
				}
			}

			while (true) {
				try {
					Select choosemedicine = new Select(driver.findElement(By.id("optmedicine")));
					Thread.sleep(1000);
					choosemedicine.selectByIndex(i);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					continue;
				}
			}
			driver.findElement(By.id("txttreat")).sendKeys("10");
			Thread.sleep(3000);
			driver.findElement(By.id("btnadd")).click();
			
		}

		PO_Rno = driver.findElement(By.id("txtno")).getAttribute("value").trim();
		CMORNO = PO_Rno.toString().split("\\/")[2].trim(); 
	    
		
	
		try {

			while (true) {
				JavascriptExecutor scroll = ((JavascriptExecutor) driver);
				scroll.executeScript("window.scrollBy(0,200)", "");
				try {
					// SEND REQUEST
					driver.findElement(By.id("btnsave")).click();
					break;
				} catch (Exception e) {

					// e.printStackTrace();
					continue;
				}

			}
		} catch (Exception e) {
			throw new Exception("Request Not Sent");
		}
		Thread.sleep(1000);
		
		row=Read_Excel_File.sheet.getRow(3);
		
		System.out.println(row.getCell(2).toString());
		
		if(row.getCell(2).toString().trim().equals("Pharmacy"))
		{
		excelwrite(31, 4, CMORNO);
		excelwrite(49, 4, CMORNO);
		excelwrite(50, 4, CMORNO);
		}
		else
		{
			
		}
		/*row=Read_Excel_File.sheet.getRow(57);
		System.out.println(row.getCell(2).toString());
		if(row.getCell(2).toString().trim()=="Pharmacy")
		{
		excelwrite(86, 4, CMORNO);
		excelwrite(104, 4, CMORNO);
		excelwrite(105, 4, CMORNO);
		}
		else
		{
			
		}*/
	}
public void IssueQty() throws InterruptedException, IOException {
		
	String Stock_qty,Required_qty,PRNO;
	
	WebElement med = driver.findElement(By.id("fldproduct"));
	Select product1 = new Select(med);
	List product_list = product1.getOptions();

	for (int a = 1; a < product_list.size(); a++) {
		Thread.sleep(2000);
		product1.selectByIndex(a);
		Thread.sleep(2000);
		String Product_name = product1.getOptions().get(a).getText().toString();
		Thread.sleep(2000);
		Boolean pharmacyispresent = driver.findElements(By.xpath("/html/body/div[5]/div/div/div[2]/button"))
				.size() > 0;
		if (pharmacyispresent) {
			System.out.println(Product_name + "* Medicine stock qty is not avilable or expired");
			Thread.sleep(3000);

			// System.out.println(+a+" Medicine stock qty is not avilable or
			// expired");
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/button")).click();
			continue;
		}

		WebElement batch = (driver.findElement(By.id("fldbatch")));
		Select batchnumber = new Select(batch);
		List batchnumber_list = batchnumber.getOptions();

		Thread.sleep(2000);

		for (int q = 1; q < batchnumber_list.size(); q++) {
			batchnumber.selectByIndex(q);

			while(true)
			{
			 Stock_qty = driver.findElement(By.id("txtprice")).getAttribute("value");
			if(Stock_qty.isEmpty())
			{
				Stock_qty = driver.findElement(By.id("txtprice")).getAttribute("value");
				continue;
			}
			else
			{
				Stock_qty = driver.findElement(By.id("txtprice")).getAttribute("value");
				break;
			}
			}
			
			 Required_qty = driver.findElement(By.id("txttotal")).getAttribute("value");

			System.out.println(Product_name + "=== Stockqty :" + Stock_qty + " Required Qty :" + Required_qty);
		

			if (Integer.parseInt(Stock_qty) >= Integer.parseInt(Required_qty)) {
				Thread.sleep(2000);
				driver.findElement(By.id("txtqty")).sendKeys(String.valueOf(Required_qty));
				driver.findElement(By.id("btnadd")).click();	
				break;
			} else {
			
				System.out.println(Product_name + "=== Stockqty is less than required qty");
				System.out.println(Product_name + "=== Stock qty available is : " + Stock_qty + " Required Qty Is:"+ Required_qty);
				}
		}

		Thread.sleep(2000);
	}

	
		Thread.sleep(3000);
while(true)
{
		JavascriptExecutor scrollsub = (JavascriptExecutor) driver;
		scrollsub.executeScript("window.scrollBy(0,100)", "");
		Thread.sleep(2000);

		try {
			PRNO = driver.findElement(By.id("lblpono")).getAttribute("value");
			System.out.println(PRNO);
			driver.findElement(By.id("btnSubmit")).click();
			Thread.sleep(3000);
			break;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			continue;
		}
}
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/button")).click();
	
}
}
