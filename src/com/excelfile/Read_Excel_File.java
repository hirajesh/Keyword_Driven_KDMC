package com.excelfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Read_Excel_File {

    File source;
	FileInputStream Inputstreame;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	
public Sheet ExcelReturn() throws IOException 
{

	 source=new File("D:\\Eclipse workspace\\Keyword_Driven_KDMC\\K.xlsx");
	 Inputstreame=new  FileInputStream(source);
	 workbook=new XSSFWorkbook(Inputstreame);
	 sheet=workbook.getSheetAt(0);
	 return sheet;	
}


	
}
