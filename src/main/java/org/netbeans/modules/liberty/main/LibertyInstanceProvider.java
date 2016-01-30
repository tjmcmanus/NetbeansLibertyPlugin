package org.netbeans.modules.liberty.main;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceFactory;
import org.netbeans.spi.server.ServerInstanceProvider;

public class LibertyInstanceProvider implements ServerInstanceProvider {

    private static final String serverInstanceName = "WebSphere Liberty";
    
    @Override
    public List<ServerInstance> getInstances() {
        List<ServerInstance> instances = new ArrayList<ServerInstance>();
        ServerInstance instance = ServerInstanceFactory.createServerInstance(
                new LibertyInstanceImplementation(this, serverInstanceName, serverInstanceName, true));
        instances.add(instance);
        return instances;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
    }

}