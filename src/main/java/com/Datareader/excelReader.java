package com.Datareader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Common.Property;

public class excelReader {
    FileInputStream iStream = null;
    FileOutputStream oStream = null;
    Property property;
    Workbook wb = null;
    Sheet sheet;
    Row row;
    Cell cell;
    ArrayList<String> testSteps;

    public excelReader(Property property) {
	this.property = property;
	testSteps = new ArrayList<String>();
    }

    private String getTestCaseSheetPath() {
	return System.getProperty("user.dir") + property.resourcePath
		+ property.testCaseFolder + "/" + property.getTestExcelName();
    }

    private String getObjectRepoPath() {
	return System.getProperty("user.dir") + property.resourcePath
		+ property.objectRepoFolder + "/"
		+ property.getObjectRepositoryName();
    }

    public ArrayList<String> getTestDataByName(String testName)
	    throws Exception {
	int testStart = 0;
	try {
	    iStream = new FileInputStream(getTestCaseSheetPath());
	    wb = new XSSFWorkbook(iStream);
	    sheet = wb.getSheetAt(0);
	    int rowCount = sheet.getLastRowNum();
	    int colCount = getColCount();
	    for (int i = 1; i <= rowCount; i++) {
		row = sheet.getRow(i);
		cell = row.getCell(0);
		if (cell != null) {
		    if (cell.toString().contains(testName))
			testStart = 1;
		    else if (testStart == 1)
			break;
		}
		if (testStart == 1) {
		    testSteps.add(getRowData(colCount));
		}
	    }
	    return testSteps;
	} catch (FileNotFoundException e) {
	    System.out.println("File not found: " + e);
	    throw e;
	} catch (Exception e) {
	    System.out.println("Error Occur:" + e);
	    throw e;
	}
    }

    private int getColCount() {
	return sheet.getRow(0).getLastCellNum();
    }

    private String getRowData(int colCount) throws Exception {
	String textString = "";
	try {
	    for (int j = 0; j <= colCount; j++) {
		textString = textString + row.getCell(j) + ",";
	    }
	    return textString;
	} catch (Exception e) {
	    System.out.println("Error Occured while reading row Data:" + e);
	    throw e;
	}

    }

    public ArrayList<String> getTestDataByGroup(String testGroup)
	    throws Exception {
	int testStart = 0;
	try {
	    iStream = new FileInputStream(getTestCaseSheetPath());
	    wb = new XSSFWorkbook(iStream);
	    sheet = wb.getSheetAt(0);
	    int rowCount = sheet.getLastRowNum();
	    int colNum = getColNumOfLabel(sheet, "TestGroup");
	    int colCount = getColCount();
	    for (int i = 1; i <= rowCount; i++) {
		row = sheet.getRow(i);
		cell = row.getCell(colNum);
		if (cell != null) {
		    if (cell.toString().equalsIgnoreCase(testGroup))
			testStart = 1;
		    else
			testStart = 0;
		}
		if (testStart == 1) {
		    testSteps.add(getRowData(colCount));
		}
	    }
	    return testSteps;
	} catch (FileNotFoundException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}
    }

    private int getColNumOfLabel(Sheet sh, String testGroup) {
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

    public HashMap<String, String> getLocatorsFromExcel(String[] locator)
	    throws Exception {
	HashMap<String, String> locatorMap = new HashMap<String, String>();
	try {
	    iStream = new FileInputStream(getObjectRepoPath());
	    wb = new XSSFWorkbook(iStream);
	    sheet = wb.getSheetAt(0);
	    int locNmColNum = getColNumOfLabel(sheet, "LocatorName");
	    int strategyColNum = getColNumOfLabel(sheet, "Strategy");
	    int locColNum = getColNumOfLabel(sheet, "Locator");
	    int rowCount = sheet.getLastRowNum();
	    for (String locatorName : locator) {
		if (!locatorName.equalsIgnoreCase(property.NA)) {
		    for (int i = 1; i <= rowCount; i++) {
			row = sheet.getRow(i);
			if (row.getCell(locNmColNum).toString()
				.equalsIgnoreCase(locatorName)) {
			    locatorMap.put(locatorName,
				    row.getCell(strategyColNum).toString()
					    + "," + row.getCell(locColNum));
			    break;
			}
		    }
		} else {
		    locatorMap.put(locatorName, property.NA);
		}

	    }
	    for (String key : locatorMap.keySet()) {
		System.out.println(key + " : " + locatorMap.get(key));
	    }
	    return locatorMap;
	} catch (Exception e) {
	    throw e;
	}
    }
}
