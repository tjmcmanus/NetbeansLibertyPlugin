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

import javax.enterprise.deploy.spi.DeploymentManager;
import org.netbeans.modules.j2ee.deployment.plugins.spi.FindJSPServlet;
import org.netbeans.modules.j2ee.deployment.plugins.spi.IncrementalDeployment;
import org.netbeans.modules.j2ee.deployment.plugins.spi.OptionalDeploymentManagerFactory;
import org.netbeans.modules.j2ee.deployment.plugins.spi.StartServer;
import org.openide.WizardDescriptor.InstantiatingIterator;

public class LibertyOptionalFactory extends OptionalDeploymentManagerFactory {
    
    @Override
    public StartServer getStartServer(DeploymentManager dm) {
        return new LibertyStartServer();
    }
    
    @Override
    public IncrementalDeployment getIncrementalDeployment(DeploymentManager dm) {
        return null;
    }
    
    @Override
    public FindJSPServlet getFindJSPServlet(DeploymentManager dm) {
        return null;
    }
    
    @Override
    public InstantiatingIterator getAddInstanceIterator() {
        return new LibertyInstantiatingIterator();
    }
    
}
