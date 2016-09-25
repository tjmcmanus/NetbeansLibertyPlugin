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

import org.netbeans.spi.server.ServerWizardProvider;
import org.openide.WizardDescriptor.InstantiatingIterator;

/**
 * Provides wizard in Server Manager under Tools menu. Registered in layer.xml
 * file.
 *
 * @author gwieleng
 */
public class LibertyServerWizardProvider implements ServerWizardProvider {

    private static final String TEST_SERVER_NAME = "WebSphere Liberty";

    private static final String TEST_RUNTIME_LOC = "C:\\myLibertyInstallPath\\wlp";
    
    public LibertyServerWizardProvider() {
    }

    @Override
    public String getDisplayName() {
        return "WebSphere Liberty";
    }

    @Override
    public InstantiatingIterator getInstantiatingIterator() {
        return new LibertyWizardIterator("WebSphere Liberty");
    }

}
