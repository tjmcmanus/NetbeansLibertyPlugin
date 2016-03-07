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
