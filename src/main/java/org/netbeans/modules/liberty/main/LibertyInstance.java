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

package org.netbeans.modules.liberty.main;

import java.io.File;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for server instance, to be visualized via
 * #LibertyInstanceImplementation
 *
 * @author Elson Yuen, Stephan Knitelius
 */
@XmlRootElement
public class LibertyInstance {

    private File userDir = null;
    private File serverOutputPath = null;
    private String serverName = null;
    private String instanceName = null;
    private int debugPort = 7777;

    private String runtimeLocation = null;
    private File javaHome = null;
    private boolean removable = true;

    public LibertyInstance() {
    }

    public LibertyInstance(File userDir, File serverOutputPath, String serverName, String runtimeLocation, File javaHome,
            int debugPort, boolean removable) {
        this.userDir = userDir;
        if (serverOutputPath == null) {
            this.serverOutputPath = new File(userDir.toString() + File.separator + "servers");
        } else {
            this.serverOutputPath = serverOutputPath;
        }
        this.serverName = serverName;
        this.instanceName = serverName;
        this.runtimeLocation = runtimeLocation;
        this.javaHome = javaHome;
        this.debugPort = debugPort;
        this.removable = removable;
    }

    public void setUserDir(File userDir) {
        this.userDir = userDir;
    }

    public void setServerOutputPath(File serverOutputPath) {
        this.serverOutputPath = serverOutputPath;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setDebugPort(int debugPort) {
        this.debugPort = debugPort;
    }

    public void setRuntimeLocation(String runtimeLocation) {
        this.runtimeLocation = runtimeLocation;
    }

    public void setJavaHome(File javaHome) {
        this.javaHome = javaHome;
    }

    public int getDebugPort() {
        return debugPort;
    }

    public File getJavaHome() {
        return javaHome;
    }

    public File getUserDir() {
        return userDir;
    }

    public File getServerOutputPath() {
        return serverOutputPath;
    }

    public String getServerName() {
        return serverName;
    }

    public String getRuntimeLocation() {
        return runtimeLocation;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
