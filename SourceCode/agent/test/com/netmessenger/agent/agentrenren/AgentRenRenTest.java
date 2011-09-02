package com.netmessenger.agent.agentrenren;

import org.junit.Test;

import com.netmessenger.agent.renren.AgentRenren;
import com.netmessenger.agent.renren.datastore.RecipientInfo;
import com.netmessenger.agent.renren.datastore.RecipientInfoDAO;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.database.DBConnection;
import com.netmessenger.message.Message;

public class AgentRenRenTest {

	@Test
	public void testFuelAgent() {
		AgentRenren agentRenren = new AgentRenren();
		agentRenren.prepareRunningEnvironment();
		agentRenren.fuelAgent();
	}
	
	@Test
	public void testDeliverMessage() {
		AgentRenren agentRenren = new AgentRenren();
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"你好！");
		agentRenren.deliverMessage(message);
	}
	
	@Test
	public void testSendSingleMessage(){
		
		RecipientInfoDAO dao = new RecipientInfoDAO(DBConnection.getConnection());
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.name_$eq("");
		recipientInfo.homePage_$eq("http://www.renren.com/profile.do?portal=profileFootprint&ref=profile_footprint&id=252696093");
		
		dao.add(recipientInfo);
	}

}
