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
import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.plugins.spi.J2eePlatformImpl;
import org.netbeans.spi.project.libraries.LibraryImplementation;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public class LibertyJ2eePlatformImpl extends J2eePlatformImpl {
    
    @Override
    public boolean isToolSupported(String toolName) {
        return false;
    }
    
    @Override
    public File[] getToolClasspathEntries(String toolName) {
        return new File[0];
    }
    
    @Override
    public Set getSupportedSpecVersions() {
        Set result = new HashSet();
        result.add(J2eeModule.J2EE_14);
        //result.add(J2eeModule.JAVA_EE_5);
        return result;
    }
    
    @Override
    public java.util.Set getSupportedModuleTypes() {
        Set result = new HashSet();
        result.add(J2eeModule.WAR);
        //result.add(J2eeModule.EAR);
        //result.add(J2eeModule.EJB);
        return result;
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
    public Set getSupportedJavaPlatformVersions() {
        Set versions = new HashSet();
        versions.add("1.4"); // NOI18N
        versions.add("1.5"); // NOI18N
        return versions;
    }
    
    @Override
    public JavaPlatform getJavaPlatform() {
        return JavaPlatformManager.getDefault().getDefaultPlatform();
    }

}