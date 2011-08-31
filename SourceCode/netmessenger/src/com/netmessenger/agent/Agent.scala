package com.netmessenger.agent;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IMessage;
import com.netmessenger.recipient.RecipientInfoDAO;

 abstract class Agent(name:String, dao:RecipientInfoDAO) extends IAgent{
	
	def deliverMessage(message:IMessage);
	
	def findAndSaveRecipientInfo();
	
	def getName():String = name;


}
