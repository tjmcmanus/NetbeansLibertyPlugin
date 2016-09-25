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

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.spi.server.ServerWizardProvider;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.InstantiatingIterator;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;

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

    private static class LibertyWizardIterator implements InstantiatingIterator {

        private final String name;
        private Panel panel;

        public LibertyWizardIterator(String name) {
            this.name = name;
        }

        @Override
        public Set instantiate() throws IOException {
            LibertyInstance instance = new LibertyInstance(
                    new File(TEST_RUNTIME_LOC + "\\usr"), 
                    null, 
                    TEST_SERVER_NAME, 
                    TEST_RUNTIME_LOC,
                    new File(System.getProperty("java.home")), 
                    7777, 
                    true);
            LibertyInstanceManager.getDefault().add(instance);
            return Collections.singleton(instance);
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public synchronized Panel current() {
            if (panel == null) {
                panel = new LibertyWizardPanel();
            }
            return panel;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public void initialize(WizardDescriptor wizard) {
        }

        @Override
        public void uninitialize(WizardDescriptor wizard) {
        }

        @Override
        public void nextPanel() {
        }

        @Override
        public void previousPanel() {
        }

        @Override
        public void addChangeListener(ChangeListener l) {
        }

        @Override
        public void removeChangeListener(ChangeListener l) {
        }

    }

    private static class LibertyWizardPanel implements Panel {

        private LibertyInstanceManagerPanel panel;

        @Override
        public synchronized Component getComponent() {
            if (panel == null) {
                panel = new LibertyInstanceManagerPanel();
            }
            return panel;
        }

        @Override
        public HelpCtx getHelp() {
            return HelpCtx.DEFAULT_HELP;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void addChangeListener(ChangeListener l) {
        }

        @Override
        public void removeChangeListener(ChangeListener l) {
        }

        @Override
        public void readSettings(Object settings) {
        }

        @Override
        public void storeSettings(Object settings) {
        }

    }

}
