package com.netmessenger.core.recipientprofile;

public enum RecipientGender {

	MALE,
	FEMALE,
	ALL;
	
	public static RecipientGender parse(String value) throws Exception{
		if(MALE.toString().toLowerCase().equals(value.toLowerCase())){
			return MALE;
		}
		if(FEMALE.toString().toLowerCase().equals(value.toLowerCase())){
			return FEMALE;
		}
		if(ALL.toString().toLowerCase().equals(value.toLowerCase())){
			return ALL;
		}
		throw new Exception("unparsable RecipientGender:" + value);
		
	}
}
