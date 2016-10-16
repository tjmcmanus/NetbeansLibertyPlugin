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

import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.status.ProgressObject;
import org.netbeans.modules.j2ee.deployment.plugins.api.ServerDebugInfo;
import org.netbeans.modules.j2ee.deployment.plugins.spi.StartServer;

public class LibertyStartServer extends StartServer {
    
    @Override
    public ProgressObject startDebugging(Target target) {
        return null;
    }
    
    @Override
    public boolean isDebuggable(Target target) {
        return false;
    }
    
    @Override
    public boolean isAlsoTargetServer(Target target) {
        return true;
    }
    
    @Override
    public ServerDebugInfo getDebugInfo(Target target) {
        return null;
    }
    
    @Override
    public boolean supportsStartDeploymentManager() {
        return false;
    }
    
    @Override
    public ProgressObject stopDeploymentManager() {
        return null;
    }
    
    @Override
    public ProgressObject startDeploymentManager() {
        return null;
    }
    
    @Override
    public boolean needsStartForTargetList() {
        return false;
    }
    
    @Override
    public boolean needsStartForConfigure() {
        return false;
    }
    
    @Override
    public boolean needsStartForAdminConfig() {
        return false;
    }
    
    @Override
    public boolean isRunning() {
        return false;
    }

}
