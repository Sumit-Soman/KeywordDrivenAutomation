package com.Actions;

import org.openqa.selenium.By;

public class LocatorStrategy {

    public LocatorStrategy() {
    }

    public By getElementObject(String locatorstrategy, String locator)
	    throws Exception {

	By by = null;
	try {
	    switch (locatorstrategy.toLowerCase()) {
	    case "id":
		by = By.id(locator);
		break;
	    case "name":
		by = By.name(locator);
		break;
	    case "class":
		by = By.className(locator);
		break;
	    case "css":
		by = By.cssSelector(locator);
		break;
	    case "xpath":
		by = By.xpath(locator);
		break;
	    case "link":
		by = By.linkText(locator);
		break;
	    case "partiallink":
		by = By.partialLinkText(locator);
		break;
	    default:
		throw new Exception("Incorrect Attribute type mentioned");
	    }
	} catch (Exception e) {
	    System.out.println("Exception while creating Element Object:" + e);
	}
	return by;

    }
}
