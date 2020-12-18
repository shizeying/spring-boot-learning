package com.example.transform.domain;

import com.example.transform.Enum.EPlatform;

public final class OSInfo {
	private static final String OS = System.getProperty("os.name").toLowerCase();
	
	private static final OSInfo _instance = new OSInfo();
	
	private EPlatform platform;
	
	private OSInfo() {
	}
	
	public static boolean isLinux() {
		return OS.contains("linux");
	}
	
	public static boolean isMacOS() {
		return OS.contains("mac") && OS.indexOf("os") > 0 && !OS.contains("x");
	}
	
	public static boolean isMacOSX() {
		return OS.contains("mac") && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
	}
	
	public static boolean isWindows() {
		return OS.contains("windows");
	}
	
	public static boolean isOS2() {
		return OS.contains("os/2");
	}
	
	public static boolean isSolaris() {
		return OS.contains("solaris");
	}
	
	public static boolean isSunOS() {
		return OS.contains("sunos");
	}
	
	
	/**
	 * 获取操作系统名字
	 *
	 * @return 操作系统名
	 */
	public static EPlatform getOSName() {
		
		if (isLinux()) {
			_instance.platform = EPlatform.LINUX;
		} else if (isMacOSX() || isMacOS()) {
			_instance.platform = EPlatform.Mac_OS;
		} else if (isSolaris()) {
			_instance.platform = EPlatform.SOLARIS;
		} else if (isSunOS()) {
			_instance.platform = EPlatform.SunOS;
		} else if (isWindows()) {
			_instance.platform = EPlatform.WINDOWS;
		} else {
			_instance.platform = EPlatform.Others;
		}
		return _instance.platform;
		
	}
	
}
