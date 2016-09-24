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
