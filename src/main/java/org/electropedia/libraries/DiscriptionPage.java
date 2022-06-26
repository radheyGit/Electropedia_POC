package org.electropedia.libraries;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.electropedia.base.TestBase;
import org.electropedia.util.XLUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DiscriptionPage extends TestBase{

	@FindBy(xpath = "//a[contains(text(),'Mathematics')]")
	private WebElement tblTitle;
	
	@FindBy(xpath = "//table[@class = 'table1']")
	private WebElement webTable;
	
	public DiscriptionPage() {
		super();
		PageFactory.initElements(driver, this);
	}

	public boolean checkTblTitle() {
		boolean isTrue = false;
		String text = tblTitle.getText();
		if(text.contains("Mathematics")) {
			isTrue = true;}
		return isTrue;
	}

	/*public void getIevRefDataFromTable() {
		String file = prop.getProperty("ExcelFile");
		String sheet = prop.getProperty("SheetName");
		int row=1;
		int column = 0;
		List<String> ievRefList = new ArrayList<String>();
		List<WebElement> rows = webTable.findElements(By.tagName("tr"));
		for(int i =4;i<rows.size();i++) {
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			if(columns.size()==3) {
				String ievRef = columns.get(0).getText();
				ievRefList.add(ievRef);
			}
		}
		
		for(String ievRef:ievRefList) {
			XLUtils.setCellData(file, sheet, row, column, ievRef);
			row++;
		}
	}
	
	public void getDescDataFromTable() {
		String file = prop.getProperty("ExcelFile");
		String sheet = prop.getProperty("SheetName");
		int row=1;
		int column = 1;
		List<String> descList = new ArrayList<String>();
		List<WebElement> rows = webTable.findElements(By.tagName("tr"));
		for(int i =4;i<rows.size();i++) {
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			if(columns.size()==3) {
				String ievRef = columns.get(2).getText();
				descList.add(ievRef);
			}
		}
		
		for(String desc:descList) {
			XLUtils.setCellData(file, sheet, row, column, desc);
			row++;
		}
	}*/
	
	public void getIevRefDataFromTable() {
		String file = prop.getProperty("ExcelFile");
		String sheetName = prop.getProperty("SheetName");
		int count = 2;
		//int row=1;
		List<String> ievRefList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();
		Map<String,Object[]> data = new LinkedHashMap<String,Object[]>();
		data.put("1", new Object[] {"IEV ref", "Discription"});
		List<WebElement> rows = webTable.findElements(By.tagName("tr"));
		for(int i =4;i<rows.size();i++) {
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			if(columns.size()==3) {
				String ievRef = columns.get(0).getText();
				ievRefList.add(ievRef);
				String desc = columns.get(2).getText();
				descList.add(desc);
				System.out.println(ievRef+"  "+desc);
				data.put(String.valueOf(count),new Object[] {ievRef, desc});
				count++;
			}
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook(); 
	    XSSFSheet sheet = workbook.createSheet(sheetName);
		
		Set<String> keyset = data.keySet();
        int rownum = 1;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            FileOutputStream out = new FileOutputStream(new File(file));
            workbook.write(out);
            out.close();
        } 
        catch (Exception e) 
        {
           log.error(e.getMessage());
        }
	}

}
