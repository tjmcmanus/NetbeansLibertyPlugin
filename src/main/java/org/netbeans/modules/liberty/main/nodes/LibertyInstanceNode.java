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

package org.netbeans.modules.liberty.main.nodes;

import java.awt.Component;
import java.awt.Label;
import javax.swing.JPanel;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.liberty.main.ui.LibertyWizardComponent;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class LibertyInstanceNode extends AbstractNode implements Node.Cookie {
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/liberty/main/logo.png";
    
    public LibertyInstanceNode(Lookup lookup) {
        super(new Children.Array());
        getCookieSet().add(this);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(LibertyInstanceNode.class, "TXT_MyInstanceNode");
    }
    
    @Override
    public String getShortDescription() {
        return "http://localhost:8080"; // NOI18N
    }
    
    @Override
    public javax.swing.Action[] getActions(boolean context) {
        return new javax.swing.Action[]{};
    }
    
    @Override
    public boolean hasCustomizer() {
        return true;
    }
    
    @Override
    public Component getCustomizer() {
//        JPanel panel = new JPanel();
//        panel.add(new Label("< Put your customizer implementation here! >")); // NOI18N
        return new LibertyWizardComponent();
    }

}
