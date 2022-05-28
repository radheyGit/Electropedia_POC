package com.bookerapi.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookerapi.base.TestBase;

public class XLUtils extends TestBase{
	
	public static FileInputStream file;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileOutputStream fileOut;
	
	/***
	 * @author Radhey
	 * @param xlfileName
	 * @param xlSheetName
	 * @return Total Row Count
	 */
	public static int getRowCount(String xlfileName, String xlSheetName){
		int lastRowNum = 0;
		try {
			file = new FileInputStream(xlfileName);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(xlSheetName);
			lastRowNum = sheet.getLastRowNum();
			workbook.close();
			file.close();
		} catch (Exception e) {
			log.error("Error Occured at XLUtils Class in getRowCount()"+e.getMessage());
		}
		return lastRowNum;
	}
	/***
	 * @author Radhey
	 * @param xlfileName
	 * @param xlSheetName
	 * @param rowNo
	 * @return Total Cell Count
	 */
	public static int getCellCount(String xlfileName, String xlSheetName, int rowNo) {
		int lastCellNum = 0;
		try {
			file = new FileInputStream(xlfileName);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(xlSheetName);
			lastCellNum = sheet.getRow(rowNo).getLastCellNum();
			workbook.close();
			file.close();
		} catch (Exception e) {
			log.error("Error Occured at XLUtils Class in getCellCount()"+e.getMessage());
		}
		return lastCellNum;
	}
	/***
	 * @author Radhey
	 * @param xlfileName
	 * @param xlSheetName
	 * @param rowNo
	 * @param columnNo
	 * @return Cell Data
	 */
	public static String getCellData(String xlfileName, String xlSheetName, int rowNo, int columnNo) {
		String data;
		try {
			file = new FileInputStream(xlfileName);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(xlSheetName);
			row = sheet.getRow(rowNo);
			cell = row.getCell(columnNo);
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
			workbook.close();
			file.close();
		} catch (Exception e) {
			data = "";
			log.error("Error Occured at XLUtils Class in getCellData()"+e.getMessage());
		}
		return data;
	}
	/***
	 * @author Radhey
	 * @param xlfileName
	 * @param xlSheetName
	 * @param rowNo
	 * @param columnNo
	 * @param data
	 */
	public static void setCellData(String xlfileName, String xlSheetName, int rowNo, int columnNo, String data) {
		try {
			file = new FileInputStream(xlfileName);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(xlSheetName);
			row = sheet.getRow(rowNo);
			cell = row.createCell(columnNo);
			cell.setCellValue(data);
			fileOut = new FileOutputStream("");
			workbook.write(fileOut);
			workbook.close();
			file.close();
			fileOut.close();
		} catch (Exception e) {
			log.error("Error Occured at XLUtils Class in setCellData()"+e.getMessage());
		}
	}
}
