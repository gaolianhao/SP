package com.netmessenger.core.recipientprofile;

public enum RecipientAge {

	SENIOR,
	YOUTH,
	TEENAGER,
	ALL;
	
	public static RecipientAge parse(String value) throws Exception {
		if (RecipientAge.SENIOR.toString().toLowerCase().equals(value.toLowerCase())){
			return RecipientAge.SENIOR;
		}
		if (RecipientAge.TEENAGER.toString().toLowerCase().equals(value.toLowerCase())){
			return RecipientAge.TEENAGER;
		}
		if (RecipientAge.YOUTH.toString().toLowerCase().equals(value.toLowerCase())){
			return RecipientAge.YOUTH;
		}
		if (RecipientAge.ALL.toString().toLowerCase().equals(value.toLowerCase())){
			return RecipientAge.ALL;
		}

		throw new Exception("unparsable RecipientAge:" + value);
		
	}
}
