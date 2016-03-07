package org.netbeans.modules.liberty.main.tests;

import java.io.File;

import org.netbeans.modules.liberty.main.ServerInfo;
import org.netbeans.modules.liberty.main.ServerUtils;
import org.netbeans.modules.liberty.main.ServerUtils.ServerMode;

public class ServerControlTest {
  public static void main(String[] args) {
  	String runtimeLocation = "C:\\myLibertyInstallPath\\wlp";
  	File userDir = new File(runtimeLocation + "\\usr");
		File serverOutputPath = null;
  	String serverName = "myServer1";
  	int debugPort = 7777;
  	
  	File javaHome = new File("C:\\myJDKPath\\sdk");
  	
  	ServerInfo serverInfo = new ServerInfo(userDir, serverOutputPath, serverName, runtimeLocation, javaHome, debugPort);
  	
  	ServerUtils serverUtil = new ServerUtils();
  	Process p = serverUtil.startServer(serverInfo, ServerMode.RUN);
  	
    boolean isContinue = true;
    int count = 15;
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
    	// Stop the server after the wait time.
    	System.out.println("Stopping the server after 10 sec...");
    	serverUtil.stopServer(serverInfo);
    }
	}
}
