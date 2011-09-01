package com.netmessenger.agent.agentkaixin.datastore;

import java.util.List;


 trait IRecipientInfoDAO {

	def isExist(recipientInfo: RecipientInfo):Boolean;

	def add( recipientInfo:RecipientInfo):Unit;

	def countRecipients():Int;

	def goThroughAll(func:(RecipientInfo) => Unit):Int;
	
	def save():Unit;
	
	def close():Unit;

}