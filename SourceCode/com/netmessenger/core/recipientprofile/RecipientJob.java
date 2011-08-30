package com.netmessenger.core.recipientprofile;

public enum RecipientJob {

	STUDENT,
	LIBERTY,
	ALL;
	
	public static RecipientJob parse(String value) throws Exception{
		if(STUDENT.toString().toLowerCase().equals(value.toLowerCase())){
			return STUDENT;
		}
		if(LIBERTY.toString().toLowerCase().equals(value.toLowerCase())){
			return LIBERTY;
		}
		if(ALL.toString().toLowerCase().equals(value.toLowerCase())){
			return ALL;
		}
		throw new Exception("unparsable RecipientJob:" + value);
		
	}
}
