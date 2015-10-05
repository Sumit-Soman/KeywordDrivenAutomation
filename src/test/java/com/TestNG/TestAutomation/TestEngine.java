package com.TestNG.TestAutomation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.Actions.Action;
import com.Common.Property;
import com.Common.Utilities;
import com.Datareader.excelReader;

public class TestEngine {

    // WebDriver driver;
    Action action;
    Utilities utililty;
    excelReader reader;
    Property property;
    Boolean testStatus = false;
    public Map<String, ArrayList<String>> completeData = new TreeMap<String, ArrayList<String>>();
    // ArrayList<String> executionTestIDs = new ArrayList<String>();
    ArrayList<String> testData = new ArrayList<String>();
    public HashMap<String, String> locatorMap;

    public TestEngine() {
	property = new Property();
	action = new Action(property);
	utililty = new Utilities(property);
	reader = new excelReader(property);

    }

    /**
     * @return the suite of tests being tested
     */
    @BeforeSuite
    public void beforeSuit() {
	utililty.setAllConfigProperties();
    }

    @BeforeTest
    @Parameters({ "TestName", "TestGroup", "browser" })
    public void beforeTest(String testName, String testGroup, String browser) {
	property.getTestExcelName();
	try {
	    if (testName != "" && testName != null)
		testData = reader.getTestDataByName(testName);
	    else if (testGroup != "" || testGroup != null)
		testData = reader.getTestDataByGroup(testGroup);

	    completeData.put(testGroup, testData);
	} catch (Exception e) {
	    System.out.println("Error occur" + e);
	}

    }

    @org.testng.annotations.Test
    public void execution() {
	try {
	    for (String key : completeData.keySet()) {
		ArrayList<String> testArray = completeData.get(key);
		testStatus = executeTestCase(testArray);

	    }
	    // stepStatus = action.performAction("openBrowser", "NA", param);
	    // param[0] = "laptop table";
	    // String locator = "fk-top-search-box";
	    // stepStatus = action.performAction("enterText", locator, param);
	    // stepStatus = action.performAction("closeBrowser", "NA", null);
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    @SuppressWarnings("unused")
    private boolean executeTestCase(ArrayList<String> testArray)
	    throws Exception {
	String testName = testArray.get(0).split(",")[0];
	String testStep = "";
	String locator[] = null;
	String parameter[];
	String testGroup = testArray.get(0).split(",")[5];
	String option = "";
	String text[];
	boolean status = false;
	try {
	    getAllLocators(testArray);
	    for (int i = 0; i <= testArray.size() - 1; i++) {
		text = testArray.get(i).split(",");
		testStep = text[1];
		parameter = text[4].split("#");
		option = text[6];
		if (!action.performAction(testStep, locatorMap.get(text[3]),
			parameter, option))
		    return false;
	    }
	} catch (Exception e) {
	    throw e;
	}
	return true;
    }

    private void getAllLocators(ArrayList<String> testArray) throws Exception {
	String[] locator = new String[testArray.size()];

	try {
	    for (int o = 0; o <= testArray.size() - 1; o++) {
		locator[o] = testArray.get(o).split(",")[3];
	    }
	    locatorMap = reader.getLocatorsFromExcel(locator);
	} catch (Exception e) {
	    throw e;
	}
    }

    @AfterTest
    public void afterSuit() {
	action.driver.quit();
	Assert.assertEquals(Boolean.TRUE, testStatus);
    }

}
