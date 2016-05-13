package org.netbeans.modules.liberty.main;

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
