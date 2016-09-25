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
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

/**
 *
 */
public class LibertyWizardIterator implements WizardDescriptor.AsynchronousInstantiatingIterator<WizardDescriptor> {

    private static final String TEST_SERVER_NAME = "WebSphere Liberty";

    private static final String TEST_RUNTIME_LOC = "C:\\myLibertyInstallPath\\wlp";

    private WizardDescriptor wizard;
    private LibertyWizardPanel panel;
    private int count = 0;
    private final String name;

    public LibertyWizardIterator(String name) {
        this.name = name;
    }

    public static final String PROP_DISPLAY_NAME = "ServInstWizard_displayName"; // NOI18N

    @Override
    public Set instantiate() throws IOException {
        String userDir = (String) wizard.getProperty(LibertyWizardPanel.USERDIR);
        assert userDir != null;
        LibertyInstance instance = new LibertyInstance(
                new File(userDir),
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
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        panel = null;
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

    @Override
    public String name() {
        return name;
    }

}
