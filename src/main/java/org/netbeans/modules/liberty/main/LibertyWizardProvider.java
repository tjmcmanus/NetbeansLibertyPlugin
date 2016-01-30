package org.netbeans.modules.liberty.main;

import java.awt.Component;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.netbeans.spi.server.ServerWizardProvider;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.InstantiatingIterator;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;

public class LibertyWizardProvider implements ServerWizardProvider {

    public LibertyWizardProvider() {
    }

    @Override
    public String getDisplayName() {
        return "Liberty";
    }

    @Override
    public InstantiatingIterator getInstantiatingIterator() {
        return new LibertyWizardIterator("Liberty");
    }

    private static class LibertyWizardIterator implements InstantiatingIterator {

        private final String name;
        private Panel panel;

        public LibertyWizardIterator(String name) {
            this.name = name;
        }

        @Override
        public Set instantiate() throws IOException {
            return Collections.EMPTY_SET;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public synchronized Panel current() {
            if (panel == null) {
                panel = new LibertyWizardPanel(name);
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

        private final String name;
        private JPanel panel;

        public LibertyWizardPanel(String name) {
            this.name = name;
        }

        @Override
        public synchronized Component getComponent() {
            if (panel == null) {
                panel = new JPanel();
                panel.add(new JLabel(name));
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