package cresc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GetUrl {
	
	public void main(String[] arg) throws IOException{
		
		
		/*String xmlFile = "D:\\Download\\fasa_infinity.xml";
	     String fileStr = FileUtils.readFileToString(new File(xmlFile));
	     extractUrls(fileStr);*/
	}
	
	public List<String> extractUrls(String input) {
       List<String> result = new ArrayList<String>();
		

        Pattern pattern = Pattern.compile(
            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
           result.add(matcher.group());
            
        }
       /* for(int i=0; i< result.size(); i++){
        	  System.out.println(result.get(i));
        }*/
        return result;
       
    }
	
	public void updateReport(String fileName, List<String> value,
			Integer integer) throws IOException {
		// try {
		FileInputStream file = new FileInputStream(new File(fileName + ".xls"));
		System.out.println("Print the value of Response code during updation:"
				+ integer);

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);

		// Update the value of cell
		for (int l = 0; l < value.size(); l++) {
			Row row = sheet.getRow(l + 1);
			Cell cell = row.getCell(3);
			if (cell == null)
				cell = row.createCell(3);
			cell.setCellValue(integer);
		}

		file.close();

		FileOutputStream outFile = new FileOutputStream(new File(fileName
				+ ".xls"));
		workbook.write(outFile);
		outFile.close();

	}
	}


