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
