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