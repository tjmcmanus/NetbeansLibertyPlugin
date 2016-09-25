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

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 */
public class LibertyInstanceNode extends AbstractNode {
    
    @StaticResource
    private static final String LIBERTY_ICON = "org/netbeans/modules/liberty/main/logo.png"; // NOI18N
    
    public LibertyInstanceNode(LibertyInstance instance) {
        super(Children.LEAF, Lookups.fixed(instance));
        setName(""); // NOI18N
        setDisplayName(instance.getUserDir().getPath());
        setIconBaseWithExtension(LIBERTY_ICON);
    }
    
    @Override
    public Image getIcon(int type) {
        return badgeIcon(super.getIcon(type));
    }
    
    @Override
    public Image getOpenedIcon(int type) {
        return badgeIcon(super.getOpenedIcon(type));
    }
    
    private Image badgeIcon(Image origImg) {
        return origImg;
    }
    @Override
    public Action[] getActions(boolean context) {
        return new Action[] {
//            SystemAction.get(RefreshAmazonInstanceNodeAction.class),
//            SystemAction.get(ViewAdminConsoleAction.class),
//            null,
//            SystemAction.get(RemoveAmazonInstanceAction.class),
//            null,
//            SystemAction.get(PropertiesAction.class)
        };
    }

}
