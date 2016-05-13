package org.netbeans.modules.liberty.main.jmx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.netbeans.modules.liberty.main.Trace;

public class JMXConnection {
  private static final String CONNECTOR_ADDRESS_FILE_NAME = "com.ibm.ws.jmx.local.address";
  
  // JMX MBean names
  private static final String APP_MANAGEMENT_MBEAN_NAME = "WebSphere:service=com.ibm.websphere.application.ApplicationMBean,name=";
	
  protected Path serverWorkAreaPath = null;
  protected JMXConnector connector = null;
  protected MBeanServerConnection mbsc = null;
  
  public JMXConnection(Path curServerWorkAreaPath) {
    if (curServerWorkAreaPath == null)
        throw new IllegalArgumentException("Path to server work area cannot be null");

    serverWorkAreaPath = curServerWorkAreaPath;
  }

  public void connect() throws JMXConnectionException {
    long time0 = System.currentTimeMillis();
    Throwable cause = null;
    try {
        if (connector == null)
            connector = getLocalConnector();

        if (connector != null) {
            if (Trace.ENABLED)
                Trace.trace(Trace.INFO, "JMX Connector: " + connector);
            mbsc = connector.getMBeanServerConnection();
        }

        if (mbsc != null) {
            if (Trace.ENABLED)
                Trace.trace(Trace.INFO, "Time to establish JMX=" + (System.currentTimeMillis() - time0));
            return; // successful, return
        }
    } catch (Throwable t) {
        cause = t;
        try {
            if (Trace.ENABLED)
                Trace.trace(Trace.WARNING, "JMX connection failed: ", t);
            if (connector != null)
                connector.close();
        } catch (Exception e) {
            // ignore
        }
        connector = null;
    }

    // make sure everything is cleaned up when fail to connect
    disconnect();
    throw new JMXConnectionException(cause);
  }
  
  public void disconnect() {
    mbsc = null;
    try {
        if (connector != null)
            connector.close();
    } catch (Exception e) {
        // ignore
    }
    connector = null;
  }
  
  public List<String> getAllApplicationNames() throws Exception {
    if (mbsc == null)
        throw new JMXConnectionException();

    Set<ObjectName> objNames = mbsc.queryNames(new ObjectName(APP_MANAGEMENT_MBEAN_NAME + "*"), null);
    if (objNames == null) {
        throw new Exception("MBean object name query failed for pattern: " + APP_MANAGEMENT_MBEAN_NAME + "*");
    }
    if (objNames.isEmpty())
        return Collections.emptyList();
    List<String> appList = new ArrayList<String>();
    for (ObjectName name : objNames) {
        String appName = name.getKeyProperty("name");
        if (appName != null)
            appList.add(appName);
    }
    return appList;
  }
  
  public String getAppState(String appName) throws Exception {
    if (mbsc == null)
        throw new JMXConnectionException();
    return (String) mbsc.getAttribute(new ObjectName(APP_MANAGEMENT_MBEAN_NAME + appName), "State");
  }
  
  private JMXConnector getLocalConnector() throws Exception {
    Path addressFilePath = serverWorkAreaPath.resolve(CONNECTOR_ADDRESS_FILE_NAME);
    File file = addressFilePath.toFile();

    boolean exist = file.exists();
    if (!exist) {
        int i = 50; // maximum to wait 5 seconds for the JMX address
        long time = System.currentTimeMillis();
        while (!exist && i > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // do nothing
            }
            exist = file.exists();
            i--;
        }
        if (Trace.ENABLED)
            Trace.trace(Trace.INFO, "Time to obtain JMX address=" + time);
    }

    if (exist) {
        String connectorAddr = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(addressFilePath.toFile()));
            connectorAddr = br.readLine();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                // ignore
            }
        }
        if (connectorAddr != null) {
            if (Trace.ENABLED) {
                Trace.trace(Trace.INFO, "JMX connector address:  " + connectorAddr);
            }
            JMXServiceURL url = new JMXServiceURL(connectorAddr);
            return JMXConnectorFactory.connect(url);
        }
        Trace.logError("JMXConnection: JMX connector address is null.  The file is " + file.getAbsolutePath(), null);
    } else {
        Trace.logError("JMXConnection: JMX address file doesn't exist: " + file.getAbsolutePath(), null);
    }
    return null;
  }
}
