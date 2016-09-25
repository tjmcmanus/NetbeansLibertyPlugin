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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.api.server.properties.InstanceProperties;
import org.netbeans.api.server.properties.InstancePropertiesManager;
import org.netbeans.spi.server.ServerInstanceFactory;
import org.openide.util.ChangeSupport;

/**
 * Manager of all Liberty server instances registered in the IDE (usually just one).
 */
public class LibertyInstanceManager {

    private static final String LIBERTY_IP_NAMESPACE = "liberty.websphere"; // NOI18N
    
//    static final String PREFIX = "org.netbeans.modules.websphere.liberty"; // NOI18N
    static final String USERNAME = "username"; // NOI18N
    static final String PASSWORD = "password"; // NOI18N
    private static final String NAME = "name"; // NOI18N
//    private static final String ADMIN_URL = "admin-url"; // NOI18N
    private static final String DATA_CENTER = "data-center"; // NOI18N
    private static final String IDENTITY_DOMAIN = "identity-domain"; // NOI18N
    private static final String JAVA_SERVICE_NAME = "java-service-name"; // NOI18N
    private static final String DATABASE_SERVICE_NAME = "db-service-name"; // NOI18N
    private static final String ON_PREMISE_SERVICE_INSTANCE_ID = "on-premise"; // NOI18N
    
    private static LibertyInstanceManager instance;
    private List<LibertyInstance> instances = new ArrayList<LibertyInstance>();
    private ChangeSupport listeners;
   
    private static final String TEST_SERVER_NAME = "WebSphere Liberty";

    private static final String TEST_RUNTIME_LOC = "C:\\myLibertyInstallPath\\wlp";
    
    private static final Logger LOG = Logger.getLogger(LibertyInstanceManager.class.getSimpleName());
    
    
    public static synchronized LibertyInstanceManager getDefault() {
        if (instance == null) {
            instance = new LibertyInstanceManager();
        }
        return instance;
    }
    
    private LibertyInstanceManager() {
        listeners = new ChangeSupport(this);
        init();
    }
    
    private void init() {
       instances.addAll(load());
       notifyChange();
    }
    
    private void notifyChange() {
       listeners.fireChange();
    }

    public List<LibertyInstance> getInstances() {
        return instances;
    }
    
    public void add(LibertyInstance ai) {
        store(ai);
        instances.add(ai);
        notifyChange();
    }
    
    private void store(LibertyInstance ai) {
        InstanceProperties props = InstancePropertiesManager.getInstance().createProperties(LIBERTY_IP_NAMESPACE);
        saveUsernameAndPassword(ai);
//        props.putString(ADMIN_URL, ai.getAdminURL());
//        props.putString(DATA_CENTER, ai.getDataCenter());
//        props.putString(IDENTITY_DOMAIN, ai.getIdentityDomain());
//        props.putString(JAVA_SERVICE_NAME, ai.getJavaServiceName());
//        props.putString(DATABASE_SERVICE_NAME, ai.getDatabaseServiceName());
        props.putString(NAME, ai.getInstanceName());
//        if (ai.getOnPremiseServerInstanceId() != null) {
//            props.putString(ON_PREMISE_SERVICE_INSTANCE_ID, ai.getOnPremiseServerInstanceId());
//        }
    }
        
    public void update(LibertyInstance ai) {
        for(InstanceProperties props : InstancePropertiesManager.getInstance().getProperties(LIBERTY_IP_NAMESPACE)) {
            String name = props.getString(NAME, null); // NOI18N
            if (name.equals(ai.getInstanceName())) {
//                props.putString(ADMIN_URL, ai.getAdminURL());
//                props.putString(DATA_CENTER, ai.getDataCenter());
//                props.putString(IDENTITY_DOMAIN, ai.getIdentityDomain());
//                props.putString(JAVA_SERVICE_NAME, ai.getJavaServiceName());
//                props.putString(DATABASE_SERVICE_NAME, ai.getDatabaseServiceName());
//                if (ai.getOnPremiseServerInstanceId() == null) {
//                    props.removeKey(ON_PREMISE_SERVICE_INSTANCE_ID);
//                } else {
//                    props.putString(ON_PREMISE_SERVICE_INSTANCE_ID, ai.getOnPremiseServerInstanceId());
//                }
                saveUsernameAndPassword(ai);
                notifyChange();
                break;
            }
        }
    }
    
    public boolean exist(String adminURL, String identityDomain, String javaServiceName, String user) {
//        for (LibertyInstance oi : getInstances()) {
////            if (adminURL.equals(oi.getAdminURL())
////                    && identityDomain.equals(oi.getIdentityDomain())
////                    && javaServiceName.equals(oi.getJavaServiceName())
////                    && user.equals(oi.getUser())) {
////                return true;
////            }
//        }
        return true;
    }
    
    private static void saveUsernameAndPassword(LibertyInstance ai) {
//        Keyring.save(PREFIX+USERNAME+"."+ai.getName(), ai.getUser().toCharArray(), "Liberty Username"); // NOI18N
//        Keyring.save(PREFIX+PASSWORD+"."+ai.getName(), ai.getPassword().toCharArray(), "Liberty Password"); // NOI18N
    }
    
    private static List<LibertyInstance> load() {
        List<LibertyInstance> result = new ArrayList<LibertyInstance>();
        for(InstanceProperties props : InstancePropertiesManager.getInstance().getProperties(LIBERTY_IP_NAMESPACE)) {
            String name = props.getString(NAME, null); // NOI18N
            assert name != null : "Instance without name";
//            String adminURL = props.getString(ADMIN_URL, ""); // NOI18N
//
//            String identityDomain = props.getString(IDENTITY_DOMAIN, "undefined"); // NOI18N
//            String javaServiceName = props.getString(JAVA_SERVICE_NAME, "undefined"); // NOI18N
//            String databaseServiceName = props.getString(DATABASE_SERVICE_NAME, ""); // NOI18N
//            String onPremise = props.getString(ON_PREMISE_SERVICE_INSTANCE_ID, null); // NOI18N
//            String dataCenter = props.getString(DATA_CENTER, "us1"); // NOI18N
//            String sdkFolder = CloudSDKHelper.getSDKFolder();
        LibertyInstance serverInfo = new LibertyInstance(
                new File(TEST_RUNTIME_LOC + "\\usr"), 
                null, 
                TEST_SERVER_NAME, 
                TEST_RUNTIME_LOC,
                new File(System.getProperty("java.home")), 
                7777, 
                true);
          result.add(serverInfo);
        }
        return result;
    }

    public void addChangeListener(ChangeListener l) {
        listeners.addChangeListener(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        listeners.removeChangeListener(l);
    }

    void remove(LibertyInstance ai) {
        for (InstanceProperties props : InstancePropertiesManager.getInstance().getProperties(LIBERTY_IP_NAMESPACE)) {
            if (ai.getInstanceName().equals(props.getString(NAME, null))) { // NOI18N
                props.remove();
                break;
            }
        }
        instances.remove(ai);
//        ai.deregisterJ2EEServerInstances();
        notifyChange();
    }
}
