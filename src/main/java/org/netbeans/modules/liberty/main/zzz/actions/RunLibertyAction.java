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

package org.netbeans.modules.liberty.main.zzz.actions;

import org.netbeans.modules.liberty.main.server.ServerStatusLookup;
import org.netbeans.modules.liberty.main.server.Startable;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.NodeAction;

@Messages("CTL_RunLibertyAction=Run")
public final class RunLibertyAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {
        ServerStatusLookup.getDefault().lookup(Startable.class).start();
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return ServerStatusLookup.getDefault().lookup(Startable.class) != null;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public String getName() {
        return Bundle.CTL_RunLibertyAction(); // NOI18N
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

}
