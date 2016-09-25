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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.*;

/**
 *
 */
public class LibertyWizardPanel implements WizardDescriptor.AsynchronousValidatingPanel<WizardDescriptor>, ChangeListener {

    public static final String USERDIR = "userdir"; // String
    
    private LibertyInstanceManagerPanel component;
    private ChangeSupport listeners;
    private WizardDescriptor wd = null;
    
    public LibertyWizardPanel() {
        listeners = new ChangeSupport(this);
    }
    
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new LibertyInstanceManagerPanel();
            component.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, Integer.valueOf(0));
        }
        return component;
    }
    
    @Override
    public HelpCtx getHelp() {
        return new HelpCtx(LibertyWizardPanel.class.getName());
    }

    @Override
    public void readSettings(WizardDescriptor settings) {
        wd = settings;
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        if (component != null) {
            settings.putProperty(USERDIR, component.getUserDir());
            setWarningMessage("");
        }
    }

    public void setErrorMessage(String message) {
        wd.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, message);
    }
    
    public void setWarningMessage(String message) {
        wd.putProperty(WizardDescriptor.PROP_WARNING_MESSAGE, message);
    }
    
    @Override
    public boolean isValid() {
        return true;
    }
    
    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.removeChangeListener(l);
    }
    
    void fireChange() {
        listeners.fireChange();
    }

    @Override
    public void prepareValidation() {
        getComponent().setCursor(Utilities.createProgressCursor(getComponent()));
    }

    @Override
    public void validate() throws WizardValidationException {
    }
    
    public void stateChanged(ChangeEvent e) {
        fireChange();
    }
    
}
