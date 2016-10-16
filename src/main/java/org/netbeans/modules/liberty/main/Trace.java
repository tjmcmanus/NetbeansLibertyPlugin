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

import org.netbeans.modules.liberty.main.zzz.*;

/**
 * Log messages from server instance, to be hooked up into Log action on server
 * instance visualization
 *
 * @author Elson Yuen
 */
public class Trace {

    public static final byte INFO = 0;
    public static final byte WARNING = 1;

    public static boolean ENABLED = true;

    public static void trace(byte traceLevel, String msg) {
        if (ENABLED) {
            addTracePrefix(traceLevel);
            System.out.println(msg);
        }
    }

    private static void addTracePrefix(byte traceLevel) {
        if (ENABLED) {
            switch (traceLevel) {
                case INFO:
                    System.out.print("INFO: ");
                    break;

                case WARNING:
                    System.out.print("WARNING: ");
                    break;
            }
        }
    }

    public static void trace(byte traceLevel, String msg, Throwable t) {
        if (ENABLED) {
            addTracePrefix(traceLevel);

            System.out.println(msg + ":" + t.getLocalizedMessage());
            t.printStackTrace();
        }
    }

    public static void logError(String msg, Throwable t) {
        if (ENABLED) {
            if (t != null) {
                System.out.print("ERROR:" + msg + ":" + t.getLocalizedMessage());
                t.printStackTrace();
            } else {
                System.out.print("ERROR:" + msg);
            }
        }
    }
}
