package org.netbeans.modules.liberty.main.tests;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.netbeans.modules.liberty.main.jmx.JMXConnection;
import org.netbeans.modules.liberty.main.jmx.JMXConnectionException;

public class JMXConnectionTest {
	public static void main(String[] args) {
		String runtimeLocation = "C:\\myLibertyInstallPath\\wlp";
		String userDir = "usr";
		String serverName = "myServer1";
		
		Path curServerWorkAreaPath = Paths.get(runtimeLocation, userDir, "servers", serverName, "workarea");
		JMXConnection curJmxConnection = new JMXConnection(curServerWorkAreaPath);
		
		try {
	    curJmxConnection.connect();
	    
	    List<String> appNameLst = curJmxConnection.getAllApplicationNames();
	    System.out.println("Installed apps:");
	    for (String curName : appNameLst) {
	    	System.out.println(curName);
	    }
    } catch (JMXConnectionException e1) {
	    e1.printStackTrace();    
	  } catch (Exception e2) {
		    e2.printStackTrace();
    } finally {
    	if (curJmxConnection != null) {
    		curJmxConnection.disconnect();
    	}
    }
	}
}
