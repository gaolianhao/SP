package com.netmessenger.recipient;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.netmessenger.agent.Agent;
import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientGender;

public class RecipientInfoDAOTest {

	@Before
	public void clearRecipientInfo(){
		RecipientInfoDAO recipientInfoDAO = new RecipientInfoDAO("kaixin001");
		File file = new File(recipientInfoDAO.storeFileFullName());
		if(file.exists()) file.delete();
	}
	
	@Test
	public void testSaveOneRecipientInfo() {
		RecipientInfoDAO recipientInfoDAO = new RecipientInfoDAO("kaixin001");
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.gender = RecipientGender.MALE;
		recipientInfo.name = "John";
		recipientInfo.homePage = "http://xxx";
		recipientInfoDAO.add(recipientInfo);
		recipientInfoDAO.save();
		
		recipientInfoDAO = new RecipientInfoDAO("kaixin001");
		List<RecipientInfo> recipients = recipientInfoDAO.getRecipientInfoList();
		
		Assert.assertNotNull(recipients);
		Assert.assertEquals(1, recipients.size());
		Assert.assertEquals(recipientInfo.getName(), recipients.get(0).getName());
		Assert.assertEquals(recipientInfo.getGender(), recipients.get(0).getGender());
		Assert.assertEquals(recipientInfo.getHomePage(), recipients.get(0).getHomePage());
	}

	@Test
	public void testSaveMultiRecipientInfo() {
		RecipientInfoDAO recipientInfoDAO = new RecipientInfoDAO("kaixin001");
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.gender = RecipientGender.MALE;
		recipientInfo.name = "John";
		recipientInfo.homePage = "http://xxx";
		recipientInfoDAO.add(recipientInfo);

		RecipientInfo recipientInfo2 = new RecipientInfo();
		recipientInfo2.gender = RecipientGender.MALE;
		recipientInfo2.name = "John2";
		recipientInfo2.homePage = "http://xx2x";
		recipientInfoDAO.add(recipientInfo);

		recipientInfoDAO.save();
		
		recipientInfoDAO = new RecipientInfoDAO("kaixin001");
		List<RecipientInfo> recipients = recipientInfoDAO.getRecipientInfoList();
		
		Assert.assertNotNull(recipients);
		Assert.assertEquals(2, recipients.size());
	}
	
}
