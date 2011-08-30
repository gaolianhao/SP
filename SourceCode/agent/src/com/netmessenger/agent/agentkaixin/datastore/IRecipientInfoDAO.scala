package com.netmessenger.agent.agentkaixin.datastore;

import java.util.List;


 trait IRecipientInfoDAO {

	def isExist(recipientInfo: RecipientInfo):Boolean;

	def add( recipientInfo:RecipientInfo):Unit;

	def countRecipients():Int;

	def findAll():List[RecipientInfo] ;
	
	def save():Unit;
	
	def close():Unit;

}