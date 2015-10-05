package com.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class Utilities {
    Property property;

    public Utilities(Property property) {
	this.property = property;
    }

    public void setAllConfigProperties() {
	Properties prop = new Properties();
	InputStream iStream = null;
	try {
	    iStream = new FileInputStream(new File(property.configPath));
	    prop.load(iStream);
	    // Set<Object> keys = getAllKeys(prop);
	    property.setTestSheetname(prop.getProperty("testSheetname"));
	    property.setObjectRepositoryName(prop
		    .getProperty("objectRepositoryName"));
	    property.setTestExcelName(prop.getProperty("testExcelName"));
	    property.getTestExcelName();
	} catch (FileNotFoundException e) {
	    System.out.println("Exception: " + e);
	} catch (Exception e) {
	    System.out
		    .println("Exception occured while reading the config file: "
			    + e);
	}
    }

    private Set<Object> getAllKeys(Properties prop) {
	return prop.keySet();
    }
}
