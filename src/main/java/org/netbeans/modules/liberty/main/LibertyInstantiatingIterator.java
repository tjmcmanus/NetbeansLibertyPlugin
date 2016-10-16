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
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.j2ee.deployment.plugins.api.InstanceProperties;
import org.netbeans.modules.liberty.main.ui.LibertyWizardComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class LibertyInstantiatingIterator implements WizardDescriptor.InstantiatingIterator {
    
    private final static String PROP_DISPLAY_NAME = "ServInstWizard_displayName"; // NOI18N
        
    private InstallPanel panel;
    private WizardDescriptor wizard;
    
    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void previousPanel() {
    }

    @Override
    public void nextPanel() {
    }

    @Override
    public String name() {
        return NbBundle.getMessage(LibertyInstantiatingIterator.class, "MSG_InstallerName");
    }

    @Override
    public Set instantiate() throws IOException {
           Set result = new HashSet();       
           String displayName = getDisplayName();
           String url         = "deployer:myserver:localhost:8080"; // NOI18N
           String username    = "username"; // NOI18N
           String password    = "password"; // NOI18N
           try {
               InstanceProperties ip = InstanceProperties.createInstanceProperties(
                       url, username, password, displayName);
               result.add(ip);
           } catch (Exception ex) {
               DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        NbBundle.getMessage(LibertyInstantiatingIterator.class, "MSG_CreateFailed", displayName),
                        NotifyDescriptor.ERROR_MESSAGE));
           }
           return result;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Panel current() {
        if (panel == null) {
            panel = new InstallPanel();
        }
        return panel;
    }
    
    private String getDisplayName() {
        return (String)wizard.getProperty(PROP_DISPLAY_NAME);
    }
    
    private static class InstallPanel implements WizardDescriptor.Panel {
        @Override
        public void removeChangeListener(ChangeListener l) {
        }

        @Override
        public void addChangeListener(ChangeListener l) {
        }

        @Override
        public void storeSettings(Object settings) {
        }

        @Override
        public void readSettings(Object settings) {
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public HelpCtx getHelp() {
            return HelpCtx.DEFAULT_HELP;
        }

        @Override
        public Component getComponent() {
//            JPanel panel = new JPanel();
//            panel.add(new Label("< Put your installation form implementation here! >")); // NOI18N
            return new LibertyWizardComponent();
        }
    }

}