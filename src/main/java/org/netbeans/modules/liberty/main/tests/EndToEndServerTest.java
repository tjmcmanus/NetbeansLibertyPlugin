/*
 * Copyright 2016 Netbeans Liberty Plugin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.liberty.main.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import org.netbeans.modules.liberty.main.LibertyInstance;
import org.netbeans.modules.liberty.main.ServerUtils;
import org.netbeans.modules.liberty.main.ServerUtils.ServerMode;
import org.netbeans.modules.liberty.main.config.Constants;
import org.netbeans.modules.liberty.main.config.ServerConfigurationFile;
import org.netbeans.modules.liberty.main.jmx.JMXConnection;
import org.netbeans.modules.liberty.main.jmx.JMXConnectionException;
import org.netbeans.modules.liberty.main.util.PathUtil;

public class EndToEndServerTest {
  public static void main(String[] args) {
  	String runtimeLocation = "C:\\myLibertyInstallPath\\wlp";
		String userDir = "usr";
  	File userDirFile = new File(runtimeLocation + File.separator + userDir);
		File serverOutputPath = null;
  	String serverName = "myServer1";
  	int debugPort = 7777;
  	File javaHome = new File("C:\\myJDKPath\\sdk");

  	String warName = "test_app";
  	String warFileLocation = "C:\\test_app.war";

  	LibertyInstance serverInfo = new LibertyInstance(userDirFile, serverOutputPath, serverName, runtimeLocation, javaHome, debugPort, true);
  	
  	ServerUtils serverUtil = new ServerUtils();
  	Process p = serverUtil.startServer(serverInfo, ServerMode.RUN);
  	
    boolean isContinue = true;
    int count = 5;
    while (isContinue && count-- > 0) {
    	try {
    		if (p.exitValue() <= 0) {
    			System.out.println("Server has stopped from outside: exitValue = " + p.exitValue());
    			isContinue = false;
    		}
    	} catch (IllegalThreadStateException e1) {
    		// Process not exit yet. Continue to wait.
    		try {
	  			Thread.sleep(1000);
	      } catch (InterruptedException e2) {
	        // TODO handle error.
	        e2.printStackTrace();
	      }
	    }
    }
    
    if (isContinue) {
    	// List all applications
    	listApplications(runtimeLocation, userDir, serverName);
    	
    	// Install a war
    	installWarFile(runtimeLocation, serverName, warName, warFileLocation);
    	
    	// Wait for app to get started.
    	try {
	      Thread.sleep(5000);
      } catch (InterruptedException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
    	
    	// Get the application status
    	printApplicationStatus(runtimeLocation, userDir, serverName, warName);
    	
    	// Remove a war
    	uninstallApplication(runtimeLocation, serverName, warName);
    	
    	// Stop the server after the wait time.
    	System.out.println("Stopping the server after 10 sec...");
    	serverUtil.stopServer(serverInfo);
    }
	}
  
  public static void listApplications(String runtimeLocation, String userDir, String serverName) {
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
  
  public static void printApplicationStatus(String runtimeLocation, String userDir, String serverName, String appName) {
		Path curServerWorkAreaPath = Paths.get(runtimeLocation, userDir, "servers", serverName, "workarea");
		JMXConnection curJmxConnection = new JMXConnection(curServerWorkAreaPath);
		
		try {
	    curJmxConnection.connect();
	    
	    String appState = curJmxConnection.getAppState(appName);
	    System.out.println("App state for " + appName + " is " + appState);
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
  
  public static void installWarFile(String runtimeLocation, String serverName, String appName, String warFileLocation) {
  	Path configPath = PathUtil.getConfigurationFile(runtimeLocation, serverName);
  	Path userDirPath = PathUtil.getUserDir(runtimeLocation);
  	if (configPath != null) {
  		try {
	      ServerConfigurationFile configFile = new ServerConfigurationFile(configPath.toUri(), userDirPath);
	      
	      HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put("context-root", appName);
	      boolean isModified = configFile.addApplication(appName, Constants.WEB_APPLICATION, warFileLocation, attributes);
	      
	      // Save the config.
	      if (isModified) {
	      	configFile.save();
	      }
	      System.out.println("Application has been installed: " + appName);
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
  	}
  }
  
  public static void uninstallApplication(String runtimeLocation, String serverName, String appName) {
  	Path configPath = PathUtil.getConfigurationFile(runtimeLocation, serverName);
  	Path userDirPath = PathUtil.getUserDir(runtimeLocation);
  	if (configPath != null) {
  		try {
	      ServerConfigurationFile configFile = new ServerConfigurationFile(configPath.toUri(), userDirPath);     
	      boolean isModified = configFile.removeApplication(appName);
	      
	      // Save the config.
	      if (isModified) {
	      	configFile.save();
	      }
	      System.out.println("Application has been uninstalled: " + appName);
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
  	}
  }
}
