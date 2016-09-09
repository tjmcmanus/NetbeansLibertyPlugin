package org.netbeans.modules.liberty.main;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceFactory;
import org.netbeans.spi.server.ServerInstanceProvider;

/**
 * Provides instance in Services window.
 * Registered in layer.xml file.
 * @author gwieleng
 */
public class LibertyInstanceProvider implements ServerInstanceProvider {

    private static final String TEST_SERVER_NAME = "WebSphere Liberty";

    private static final String TEST_RUNTIME_LOC = "C:\\myLibertyInstallPath\\wlp";

    @Override
    public List<ServerInstance> getInstances() {
        List<ServerInstance> instances = new ArrayList<ServerInstance>();
        //TODO: Listen to registration entries created in user directory
        //using FileChangeListener and refresh here
        //to create an instance:
        //Dummy implementation
//        ServerInstance instance = ServerInstanceFactory.createServerInstance(
//                new LibertyInstanceImplementation(this, TEST_SERVER_NAME, TEST_SERVER_NAME, TEST_RUNTIME_LOC, true));
//        instances.add(instance);
        return instances;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
    }

}
