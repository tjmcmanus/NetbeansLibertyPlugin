/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2016 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */
package org.netbeans.modules.liberty.main;

import java.io.File;

/**
 * Model for server instance,
 * to be visualized via #LibertyInstanceImplementation
 * @author Elson Yuen
 */
public class ServerInfo {

    private File userDir = null;
    private File serverOutputPath = null;
    private String serverName = null;
    private int debugPort = 7777;

    private String runtimeLocation = null;
    private File javaHome = null;

    public ServerInfo(File userDir, File serverOutputPath, String serverName, String runtimeLocation, File javaHome,
            int debugPort) {
        this.userDir = userDir;
        if (serverOutputPath == null) {
            this.serverOutputPath = new File(userDir.toString() + File.separator + "servers");
        } else {
            this.serverOutputPath = serverOutputPath;
        }
        this.serverName = serverName;
        this.runtimeLocation = runtimeLocation;
        this.javaHome = javaHome;
        this.debugPort = debugPort;
    }

    int getDebugPort() {
        return debugPort;
    }

    File getJavaHome() {
        return javaHome;
    }

    File getUserDir() {
        return userDir;
    }

    File getServerOutputPath() {
        return serverOutputPath;
    }

    String getServerName() {
        return serverName;
    }

    String getRuntimeLocation() {
        return runtimeLocation;
    }
}
