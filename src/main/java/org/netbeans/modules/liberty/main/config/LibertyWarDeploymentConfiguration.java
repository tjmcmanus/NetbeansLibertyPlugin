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

package org.netbeans.modules.liberty.main.config;

import java.io.OutputStream;
import org.netbeans.modules.j2ee.deployment.common.api.ConfigurationException;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.plugins.spi.config.ContextRootConfiguration;
import org.netbeans.modules.j2ee.deployment.plugins.spi.config.DeploymentPlanConfiguration;
import org.netbeans.modules.j2ee.deployment.plugins.spi.config.ModuleConfiguration;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class LibertyWarDeploymentConfiguration implements ModuleConfiguration, ContextRootConfiguration, DeploymentPlanConfiguration {

    private final J2eeModule module;

    public LibertyWarDeploymentConfiguration(J2eeModule module) {
        this.module = module;
        // TODO server specific deployment descriptor should be created (if neccessary) and loaded
    }

    @Override
    public J2eeModule getJ2eeModule() {
        return module;
    }

    @Override
    public Lookup getLookup() {
        return Lookups.fixed(new Object[] {this});
    }

    @Override
    public void dispose() {

    }

    @Override
    public String getContextRoot() throws ConfigurationException {
        // TODO implement reading of the context root
        return "/mypath"; // NOI18N
    }

    @Override
    public void setContextRoot(String arg0) throws ConfigurationException {
        // TODO implement storing of the context root
    }

    @Override
    public void save(OutputStream os) throws ConfigurationException {
        // TODO implement storing of the deployment plan
    }

}
