package com.Datareader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class test {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	String fil = System.getProperty("user.dir")
		+ "/src/main/resources/TestCaseRepository/TestCase.xlsx";
	File f = new File(fil);
	if (f.exists()) {
	    try {
		FileInputStream is = new FileInputStream(f);
		Workbook ws = new XSSFWorkbook(is);
		Sheet sh = ws.getSheetAt(0);
		String testGroup = "Test";
		ArrayList<String> testData = new ArrayList<String>();
		String textString = "";
		int testStart = 0;
		Cell text;
		Row row;
		int rowCount = sh.getLastRowNum();
		int colNum = getColNumOfLabel(sh, "TestGroup");
		for (int i = 1; i <= rowCount; i++) {
		    row = sh.getRow(i);
		    text = row.getCell(colNum);
		    if (text != null) {
			if (text.toString().equalsIgnoreCase(testGroup))
			    testStart = 1;
			else
			    testStart = 0;
		    }
		    if (testStart == 1) {
			for (int j = 0; j <= row.getLastCellNum(); j++) {
			    textString = textString + row.getCell(j) + ",";
			}
			testData.add(textString);
			textString = "";
		    }
		}
		for (String s : testData) {
		    System.out.println(s);
		}

	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

    private static int getColNumOfLabel(Sheet sh, String testGroup) {
	Row row = sh.getRow(0);
	int counter = 0;
	for (Cell cell : row) {
	    if (cell.toString().equalsIgnoreCase(testGroup))
		break;
	    else
		counter += 1;
	}
	return counter;
    }
}
