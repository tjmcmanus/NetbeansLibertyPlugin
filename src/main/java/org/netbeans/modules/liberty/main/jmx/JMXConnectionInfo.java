package org.netbeans.modules.liberty.main.jmx;

public class JMXConnectionInfo {

    private final String host;
    private final String port;
    private final String user;
    private final String password;

    public JMXConnectionInfo(String curHost, String curPort, String curUser, String curPasswd) {
        host = curHost;
        port = curPort;
        user = curUser;
        password = curPasswd;
    }

    public boolean equals(JMXConnectionInfo jmxInfo) {
        if (jmxInfo == null) {
            return false;
        }
        return (isEqualString(host, jmxInfo.host)
                && isEqualString(port, jmxInfo.port)
                && isEqualString(user, jmxInfo.user)
                && isEqualString(password, jmxInfo.password));
    }

    private boolean isEqualString(String str1, String str2) {
        if (str1 == str2) {
            return true;
        }
        return (str1 != null && str1.equals(str2));
    }
}
