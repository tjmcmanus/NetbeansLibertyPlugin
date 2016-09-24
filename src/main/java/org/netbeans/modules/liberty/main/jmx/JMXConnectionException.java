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

/**
 * Exception class that denotes a problem with JMX Connectivity
 */
public class JMXConnectionException extends Exception {

    private static final long serialVersionUID = -5632639632836543882L;

    public JMXConnectionException() {
        super("JMX Connection Exception");
    }

    /**
     * @param message - the failure message
     * @param cause - the exception that caused the failure
     */
    public JMXConnectionException(Throwable cause) {
        super("JMX Connection Exception", cause);
    }

}
