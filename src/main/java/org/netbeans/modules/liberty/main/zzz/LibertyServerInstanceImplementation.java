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

package org.netbeans.modules.liberty.main.zzz;

import org.netbeans.modules.liberty.main.ui.LibertyWizardComponent;
import javax.swing.JComponent;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.modules.liberty.main.ServerUtils;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.Node;

/**
 * Visual representation of an instance in the Services window
 *
 * @author gwieleng & sknitelius
 */
public final class LibertyServerInstanceImplementation implements ServerInstanceImplementation {

    private final LibertyServerInstanceProvider provider;
    private ServerInstance serverInstance;
    private LibertyWizardComponent customizer;
    private final LibertyInstance instance;
    private final ServerUtils serverUtils = new ServerUtils();

    public LibertyServerInstanceImplementation(LibertyServerInstanceProvider provider, LibertyInstance instance) {
        this.provider = provider;
        this.instance = instance;
    }
    
    @Override
    public Node getFullNode() {
        return getBasicNode();
    }

    @Override
    public Node getBasicNode() {
        return new LibertyInstanceNode(instance);
    }

    @Override
    public JComponent getCustomizer() {
        synchronized (this) {
            if (customizer == null) {
                customizer = new LibertyWizardComponent();
            }
            return customizer;
        }
    }

    @Override
    public String getDisplayName() {
        return instance.getInstanceName();
    }

    @Override
    public String getServerDisplayName() {
        return instance.getServerName();
    }

    @Override
    public boolean isRemovable() {
        return instance.isRemovable();
    }

    @Override
    public void remove() {
        this.provider.getInstances().remove(serverInstance);
    }

    public void run() {
        serverUtils.startServer(instance, ServerUtils.ServerMode.RUN);
    }

    public void debug() {
        serverUtils.startServer(instance, ServerUtils.ServerMode.DEBUG);
    }

    public void stop() {
        serverUtils.stopServer(instance);
    }

}
