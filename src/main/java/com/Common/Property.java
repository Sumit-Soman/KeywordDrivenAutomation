package com.Common;

public class Property {

    public String NA = "NA";
    public String resourcePath = "/src/main/resources/";
    public String testCaseFolder = "TestCaseRepository";
    public String configPath = System.getProperty("user.dir") + resourcePath
	    + "config.properties";
    public String testSheetname = "";
    public String objectRepositoryName = "";

    public String testExcelName;
    public String objectRepoFolder = "ObjectRepository";

    public String getTestExcelName() {
	return testExcelName;
    }

    public void setTestExcelName(String testExcelName) {
	this.testExcelName = testExcelName;
    }

    public Property() {
	// TODO Auto-generated constructor stub
    }

    public String getTestSheetname() {
	return this.testSheetname;
    }

    public void setTestSheetname(String testSheetname) {
	this.testSheetname = testSheetname;
    }

    public String getObjectRepositoryName() {
	return this.objectRepositoryName;
    }

    public void setObjectRepositoryName(String objectRepositoryName) {
	this.objectRepositoryName = objectRepositoryName;
    }

}
