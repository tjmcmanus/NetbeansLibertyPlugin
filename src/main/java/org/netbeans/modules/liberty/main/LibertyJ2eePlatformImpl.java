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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.j2ee.core.Profile;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.plugins.spi.J2eePlatformImpl;
import org.netbeans.spi.project.libraries.LibraryImplementation;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public class LibertyJ2eePlatformImpl extends J2eePlatformImpl {
    
    private static final Set<J2eeModule.Type> MODULE_TYPES = new HashSet<J2eeModule.Type>();

    static {
        MODULE_TYPES.add(J2eeModule.Type.EAR);
        MODULE_TYPES.add(J2eeModule.Type.WAR);
        MODULE_TYPES.add(J2eeModule.Type.EJB);
        MODULE_TYPES.add(J2eeModule.Type.RAR);
        MODULE_TYPES.add(J2eeModule.Type.CAR);
    }
    
    private static final Set<Profile> LIBERTY_PROFILES = new HashSet<Profile>();

    static {
        LIBERTY_PROFILES.add(Profile.JAVA_EE_6_WEB);
        LIBERTY_PROFILES.add(Profile.JAVA_EE_6_FULL);
        LIBERTY_PROFILES.add(Profile.JAVA_EE_7_WEB);
        LIBERTY_PROFILES.add(Profile.JAVA_EE_7_FULL);
    }
    
    @Override
    public boolean isToolSupported(String toolName) {
        return false;
    }
    
    @Override
    public File[] getToolClasspathEntries(String toolName) {
        return new File[0];
    }
    
    @Override
    public Set<org.netbeans.api.j2ee.core.Profile> getSupportedProfiles() {
        return Collections.unmodifiableSet(LIBERTY_PROFILES);
    }

    @Override
    public Set<org.netbeans.api.j2ee.core.Profile> getSupportedProfiles(J2eeModule.Type moduleType) {
        return Collections.unmodifiableSet(LIBERTY_PROFILES);
    }

    @Override
    public Set<J2eeModule.Type> getSupportedTypes() {
        return Collections.unmodifiableSet(MODULE_TYPES);
    }

    @Override
    public Set/*<String>*/ getSupportedJavaPlatformVersions() {
        Set versions = new HashSet();
        versions.add("1.7"); // NOI18N
        versions.add("1.8"); // NOI18N
        return versions;
    }
    
    @Override
    public java.io.File[] getPlatformRoots() {
        return new File[0];
    }
    
    @Override
    public LibraryImplementation[] getLibraries() {
        return new LibraryImplementation[0];
    }
    
    @StaticResource
    private static final String ICON = "org/netbeans/modules/liberty/main/logo.png";
    
    @Override
    public java.awt.Image getIcon() {
        return ImageUtilities.loadImage(ICON); // NOI18N
        
    }
    
    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(LibertyJ2eePlatformImpl.class, "MSG_MyServerPlatform");
    }
    
    @Override
    public JavaPlatform getJavaPlatform() {
        return JavaPlatformManager.getDefault().getDefaultPlatform();
    }

}