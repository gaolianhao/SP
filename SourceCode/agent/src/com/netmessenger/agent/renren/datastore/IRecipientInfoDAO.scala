package com.netmessenger.agent.renren.datastore;

import java.util.List;


 trait IRecipientInfoDAO {

	def isExist(homePage: RecipientInfo):Boolean;

	def add( recipientInfo:RecipientInfo):Unit;

	def countRecipients():Int;

	def goThroughAll(func:(RecipientInfo) => Unit):Int;
	
	def save():Unit;
	
	def close():Unit;

}