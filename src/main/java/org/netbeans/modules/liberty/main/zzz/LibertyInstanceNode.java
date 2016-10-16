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

import java.awt.Image;
import java.util.LinkedList;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.liberty.main.ServerUtils;
import org.netbeans.modules.liberty.main.zzz.actions.RunLibertyAction;
import org.netbeans.modules.liberty.main.zzz.actions.StopLibertyAction;
import org.netbeans.modules.liberty.main.server.ServerStatusLookup;
import org.netbeans.modules.liberty.main.server.Startable;
import org.netbeans.modules.liberty.main.server.Stoppable;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.WeakListeners;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 *
 */
public class LibertyInstanceNode extends AbstractNode implements LookupListener {

    private final LibertyServerStartable serverStart = new LibertyServerStartable();
    private final LibertyServerStoppable serverStop = new LibertyServerStoppable();
    private Lookup.Result<Startable> startableResult;
    private Lookup.Result<Stoppable> stoppableResult;

    private final ServerUtils serverUtils = new ServerUtils();

    @StaticResource
    private static final String LIBERTY_ICON = "org/netbeans/modules/liberty/main/logo.png"; // NOI18N
    @StaticResource
    private static final String ICONRUNNING = "org/netbeans/modules/liberty/main/server-running.png";
    @StaticResource
    private static final String ICONSTOPPED = "org/netbeans/modules/liberty/main/server-stopped.png";

    public LibertyInstanceNode(LibertyInstance instance) {
        super(Children.LEAF, Lookups.fixed(instance));
        ServerStatusLookup.getDefault().add(serverStart);
        setDisplayName(instance.getUserDir().getPath());
        setIconBaseWithExtension(LIBERTY_ICON);
        startableResult = ServerStatusLookup.getDefault().lookupResult(Startable.class);
        startableResult.addLookupListener(WeakListeners.create(LookupListener.class, this, startableResult));
        resultChanged(new LookupEvent(startableResult));
        stoppableResult = ServerStatusLookup.getDefault().lookupResult(Stoppable.class);
        stoppableResult.addLookupListener(WeakListeners.create(LookupListener.class, this, stoppableResult));
        resultChanged(new LookupEvent(stoppableResult));
    }

    @Override
    public void resultChanged(LookupEvent le) {
        fireIconChange();
    }

    private class LibertyServerStartable implements Startable {
        @Override
        public void start() {
            LibertyInstance instance = getLookup().lookup(LibertyInstance.class);
            if (instance != null) {
                serverUtils.startServer(instance, ServerUtils.ServerMode.RUN);
                ServerStatusLookup.getDefault().add(serverStop);
                ServerStatusLookup.getDefault().remove(serverStart);
            }
        }
    }

    private class LibertyServerStoppable implements Stoppable {
        @Override
        public void stop() {
            LibertyInstance instance = getLookup().lookup(LibertyInstance.class);
            if (instance != null) {
                serverUtils.stopServer(instance);
                ServerStatusLookup.getDefault().remove(serverStop);
                ServerStatusLookup.getDefault().add(serverStart);
            }
        }
    }

    @Override
    public Image getIcon(int type) {
        return switchIcon();
    }

    @Override
    public Image getOpenedIcon(int type) {
        return switchIcon();
    }

    private Image switchIcon() {
        Image original = ImageUtilities.loadImage(LIBERTY_ICON);
        if (!startableResult.allInstances().isEmpty()) {
            return ImageUtilities.mergeImages(original, ImageUtilities.loadImage(ICONSTOPPED), 7, 7);
        } else {
            return ImageUtilities.mergeImages(original, ImageUtilities.loadImage(ICONRUNNING), 7, 7);
        }
    }

    @Override
    public Action[] getActions(boolean context) {
        java.util.List actions = new LinkedList();
        actions.add(SystemAction.get(RunLibertyAction.class));
        actions.add(SystemAction.get(StopLibertyAction.class));
        return (SystemAction[]) actions.toArray(new SystemAction[actions.size()]);
    }

}
