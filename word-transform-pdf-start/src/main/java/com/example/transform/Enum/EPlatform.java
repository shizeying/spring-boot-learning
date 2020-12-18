package com.example.transform.Enum;


public enum EPlatform {
	ANY("any"),
	LINUX("Linux"),
	Mac_OS("Mac OS"),
	Mac_OS_X("Mac OS X"),
	WINDOWS("Windows"),
	OS2("OS/2"),
	SOLARIS("Solaris"),
	SunOS("SunOS"),
	MPEiX("MPE/iX"),
	HP_UX("HP-UX"),
	AIX("AIX"),
	OS390("OS/390"),
	FreeBSD("FreeBSD"),
	Irix("Irix"),
	Digital_Unix("Digital Unix"),
	NetWare_411("NetWare"),
	OSF1("OSF1"),
	OpenVMS("OpenVMS"),
	Others("Others");
	
	private EPlatform(String desc) {
		this.description = desc;
	}
	
	public String toString() {
		return description;
	}
	
	private final String description;
}

