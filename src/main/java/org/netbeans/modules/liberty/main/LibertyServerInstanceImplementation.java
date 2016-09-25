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
import java.util.List;
import javax.swing.Action;
import javax.swing.JComponent;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 * Visual representation of an instance in the Services window
 *
 * @author gwieleng & sknitelius
 */
public final class LibertyServerInstanceImplementation implements ServerInstanceImplementation {

    private final LibertyServerInstanceProvider provider;
    private ServerInstance serverInstance;
    private LibertyInstanceManagerPanel customizer;
    private final LibertyInstance serverInfo;
    private final ServerUtils serverUtils = new ServerUtils();

    @StaticResource
    private static final String ICON = "org/netbeans/modules/liberty/main/logo.png";

    public LibertyServerInstanceImplementation(LibertyServerInstanceProvider provider, LibertyInstance serverInfo) {
        this.provider = provider;
        this.serverInfo = serverInfo;
    }

    @Override
    public Node getFullNode() {
        return new AbstractNode(Children.LEAF) {
            @Override
            public Image getIcon(int type) {
                return ImageUtilities.loadImage(ICON);
            }

            @Override
            public String getShortDescription() {
                return serverInfo.getUserDir().getPath();
            }
            
            @Override
            public String getDisplayName() {
                return serverInfo.getInstanceName();
            }

            @Override
            public Action[] getActions(boolean context) {
                List<? extends Action> libertyInstanceActions = Utilities.actionsForPath("Servers/Liberty/Actions");
                return libertyInstanceActions.toArray(new Action[libertyInstanceActions.size()]);

//                return new Action[]{
//                    new AbstractAction("Start") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            run();
//                        }
//                    },
//                    new AbstractAction("Start in Debug Mode") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            debug();
//                        }
//                    },
//                    new AbstractAction("Start in Profile Mode") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            throw new UnsupportedOperationException();
//                        }
//                    },
//                    new AbstractAction("Restart") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            throw new UnsupportedOperationException();
//                        }
//                    },
//                    new AbstractAction("Stop") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            stop();
//                        }
//                    },
//                    new AbstractAction("Refresh") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            throw new UnsupportedOperationException();
//                        }
//                    },
//                    new AbstractAction("Remove") {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            remove();
//                        }
//                    }
//                };
            }
        };
    }

    @Override
    public Node getBasicNode() {
        return new AbstractNode(Children.LEAF) {
            @Override
            public Image getIcon(int type) {
                return ImageUtilities.loadImage(ICON);
            }

            @Override
            public String getShortDescription() {
                return serverInfo.getUserDir().getPath();
            }
            
            @Override
            public String getDisplayName() {
                return serverInfo.getInstanceName();
            }
        };
    }

    @Override
    public JComponent getCustomizer() {
        synchronized (this) {
            if (customizer == null) {
                customizer = new LibertyInstanceManagerPanel();
            }
            return customizer;
        }
    }

    @Override
    public String getDisplayName() {
        return serverInfo.getInstanceName();
    }

    @Override
    public String getServerDisplayName() {
        return serverInfo.getServerName();
    }

    @Override
    public boolean isRemovable() {
        return serverInfo.isRemovable();
    }

    @Override
    public void remove() {
        this.provider.getInstances().remove(serverInstance);
    }

    public void run() {
        serverUtils.startServer(serverInfo, ServerUtils.ServerMode.RUN);
    }

    public void debug() {
        serverUtils.startServer(serverInfo, ServerUtils.ServerMode.DEBUG);
    }

    public void stop() {
        serverUtils.stopServer(serverInfo);
    }

}
