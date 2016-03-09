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
