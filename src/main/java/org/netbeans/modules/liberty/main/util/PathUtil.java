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
package org.netbeans.modules.liberty.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {
	private static String USER_DIR = "usr";
	private static String SERVERS_DIR = "servers";
	private static String SERVER_CONFIG_FILE = "server.xml";
	
	public static Path getUserDir(String installPath) {
		return Paths.get(installPath, USER_DIR);
	}
	
	public static Path getServerDir(String installPath, String serverName) {
		Path curUsrPath = getUserDir(installPath);
		return curUsrPath.resolve(SERVERS_DIR).resolve(serverName);
	}
	
	public static Path getConfigurationFile(String installPath, String serverName) {
		Path curServerPath = getServerDir(installPath, serverName);
		return curServerPath.resolve(SERVER_CONFIG_FILE);
	}

}
