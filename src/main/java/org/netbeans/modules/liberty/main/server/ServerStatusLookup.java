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

package org.netbeans.modules.liberty.main.server;

import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class ServerStatusLookup extends AbstractLookup {

    private InstanceContent content = null;
    private static ServerStatusLookup def = new ServerStatusLookup();

    public ServerStatusLookup(InstanceContent content) {
        super(content);
        this.content = content;
    }

    public ServerStatusLookup() {
        this(new InstanceContent());
    }

    public void add(Object instance) {
        content.add(instance);
    }

    public void remove(Object instance) {
        content.remove(instance);
    }

    public static ServerStatusLookup getDefault() {
        return def;
    }

}