package cresc1;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.Test;

public class UserPermission extends BaseClass{
	String fileLocation1 = "ExcelData/Book1.xls";
	String usertype, username, password;
	
	public void readExcel(){
		 try {
			 
				  FileInputStream fis1 = new FileInputStream(fileLocation1);
				HSSFWorkbook wb1 = new HSSFWorkbook(fis1);
				HSSFSheet sheet = wb1.getSheetAt(0);
		        for(int count = 0; count<sheet.getLastRowNum(); count++){
		        	Row row1 = sheet.getRow(count+1);
		        	Cell cell1 = row1.getCell(3);
		        	if (cell1 != null) {
		        	if(cell1.getStringCellValue().equalsIgnoreCase("Y"))
		        	{
		        		System.out.println("Testing Permissions for:" + row1.getCell(0).toString());
		        		usertype = row1.getCell(0).toString();
		        		username = row1.getCell(1).toString();;
		        		password = row1.getCell(2).toString();
		        		
		        		this.login(usertype, username, password);
		        		this.logout();
		        		
		        	}
		        	}
		        }
		           
		        fis1.close();
		    } catch (IOException e) {
		        System.out.println("Test data file not found");
		    }   
		
	}
	
 
@Test
  public void testPermission() {
	  readExcel();
	  copyFile();
	  try {
		cleanFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }

public void copyFile(){
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
	Date date = new Date();
	String fileName = "Result"+dateFormat.format(date)+".xls";
	InputStream inStream = null;
	OutputStream outStream = null;
 
    	try{
 
    	    File afile =new File(fileLocation1);
    	    File bfile =new File(fileName);
 
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length);
 
    	    }
 
    	    inStream.close();
    	    outStream.close();
 
 
    	    System.out.println("File is copied successful!");
 
    	}catch(IOException e){
    	    e.printStackTrace();
    	}
}

public void cleanFile() throws IOException{
	FileInputStream fis2 = null;
	HSSFWorkbook wb2 = null;
	System.out.println("Inside Clean File");
	try {
		fis2 = new FileInputStream(fileLocation1);
		wb2 = new HSSFWorkbook(fis2);
		HSSFSheet sheet2 = wb2.getSheetAt(1);
		HSSFCellStyle cellStyle1 = wb2.createCellStyle();
		cellStyle1 = wb2.createCellStyle();
		cellStyle1.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
     for(int count = 0; count<sheet2.getLastRowNum(); count++){
    	 Row row11 = sheet2.getRow(count+1);
    	 Cell cell = row11.getCell(0);
    	 
    	 if(cell != null){
    	
    	 Cell cell1 = row11.getCell(4);
    	 Cell cell2 = row11.getCell(6);
    	 Cell cell3 = row11.getCell(8);
    	 Cell cell4 = row11.getCell(10);
    	 
    	 cell1.setCellValue("");
    	 cell1.setCellStyle(cellStyle1);
    	
    	 cell2.setCellValue("");
    	 cell2.setCellStyle(cellStyle1);
    	
    	 cell3.setCellValue("");
    	 cell3.setCellStyle(cellStyle1);
    	
    	 cell4.setCellValue("");
    	 cell4.setCellStyle(cellStyle1);
    	 }
     }
  
	}  catch (IOException e) {
		e.printStackTrace();
	}
	fis2.close();
	FileOutputStream outFile = new FileOutputStream(new File(fileLocation));
	wb2.write(outFile);
	outFile.close();
	
}
}

