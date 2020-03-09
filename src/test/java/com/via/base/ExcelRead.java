package com.via.base;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead{
	static Sheet sheet;
	public static void excelConfig(String location, String sheetName) {
			Workbook wBook = null;
			try {
				FileInputStream fin = new FileInputStream(location);
				if(location.endsWith(".xls"))
					wBook = new HSSFWorkbook(fin);
				else if(location.endsWith(".xlsx"))
					wBook = new XSSFWorkbook(fin);
				else
					System.out.println("File specified is not an Excel File");
			} catch (Exception e) {
				e.printStackTrace();
			}
			sheet = wBook.getSheet(sheetName);
	}
	
	public static String read(int row, int col) {
			if(sheet==null) 
				return null;
			String val;
			Cell value = sheet.getRow(row).getCell(col);
			if(value==null)
				val = " ";
			else
				val = value.toString();
			if(val.endsWith(".0")) 
				val = val.split("\\.")[0];
			return val;
	}
	
	public static int numberOfColumns() {
		if(sheet != null) 
			return sheet.getRow(0).getLastCellNum();
		return 0;
	}
	
	public static int numberOfRows() {
		if(sheet != null) 
			return sheet.getLastRowNum();
		return 0;
	}
}