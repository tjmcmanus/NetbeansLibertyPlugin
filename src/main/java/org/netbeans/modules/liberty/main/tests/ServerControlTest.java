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
import org.netbeans.modules.liberty.main.LibertyInstance;
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
  	
  	LibertyInstance serverInfo = new LibertyInstance(userDir, serverOutputPath, serverName, runtimeLocation, javaHome, debugPort, true);
  	
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
