package com.Actions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.Common.Property;

public class Action {
    Property property;
    public WebDriver driver = null;
    WebElement testElement = null;
    By byLocator = null;
    LocatorStrategy locatorObj = new LocatorStrategy();
    Boolean stepStatus = false;
    String dataContentOne = null;
    String dataContentTwo = null;
    String dataContentThree = null;

    public Action(Property property) {
	this.property = property;
    }

    @SuppressWarnings({})
    public Boolean performAction(String step, String locator,
	    String[] parameters, String option) {

	try {
	    setParameter(parameters);
	    if (!locator.equalsIgnoreCase(property.NA)) {
		String locStrategy = locator.split(",")[0];
		String loc = locator.split(",")[1];
		byLocator = locatorObj.getElementObject(locStrategy, loc);
	    }

	    stepStatus = executeStep(step);
	} catch (Exception e) {
	    System.out.println("Unable to locate Element: " + e);
	}
	return stepStatus;
    }

    private void setParameter(String[] parameters) {
	if (parameters != null) {
	    switch (parameters.length) {
	    case 1:
		dataContentOne = parameters[0];
		break;
	    case 2:
		dataContentOne = parameters[0];
		dataContentTwo = parameters[1];
		break;
	    case 3:
		dataContentOne = parameters[0];
		dataContentTwo = parameters[1];
		dataContentThree = parameters[2];
		break;

	    }
	}
    }

    private Boolean executeStep(String step) {
	boolean status = false;

	switch (step.toLowerCase()) {

	case "openbrowser":
	    try {
		status = openBrowser(dataContentOne);
	    } catch (Exception e) {
		System.out.println("Exception occured on Open Browser: " + e);
	    }
	    break;

	case "closebrowser":
	    try {
		status = closeBrowser();
	    } catch (Exception e) {
		System.out.println("Exception occured on Close Browser: " + e);
	    }
	    break;

	case "entertext":
	    try {
		status = enterText(dataContentOne);
	    } catch (Exception e) {
		System.out.println("Exception occured on Entering text: " + e);
	    }
	    break;
	}

	return status;
    }

    private boolean closeBrowser() {
	driver.close();
	return true;
    }

    private Boolean openBrowser(String url) throws Exception {

	try {
	    driver = new FirefoxDriver();
	    insertImplicitWait(1);
	    maximizeWindow();
	    driver.get(url);
	    return true;
	} catch (Exception e) {
	    System.out.println("Exception occur while opeing the browser:" + e);
	    throw e;
	}
    }

    private void maximizeWindow() {
	driver.manage().window().maximize();
    }

    private void insertImplicitWait(int i) {
	driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
    }

    private Boolean enterText(String text) {
	try {
	    testElement = driver.findElement(byLocator);
	    testElement.clear();
	    testElement.sendKeys(text);
	    return true;
	} catch (Exception e) {
	    System.out.println("Exception occured while entering text: " + e);
	    return false;
	}
    }

}
