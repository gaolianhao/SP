package com.netmessenger.agent.agentkaixin.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class RecipientInfoDAOTest {

	@Test
	public void testInsetOneRecipient() throws Exception{
		Connection conn = getConnection();
		
		RecipientInfoDBMaintance dbMaintance = new  RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.setName("name");
		recipientInfo.setAge(RecipientAge.SENIOR);
		recipientInfo.setJob(RecipientJob.STUDENT);
		recipientInfo.setGender(RecipientGender.FEMALE);
		recipientInfo.setHomePage("http://homepage");
		dao.add(recipientInfo);
		
		List<RecipientInfo> list = dao.findAll();
		Assert.assertEquals(1, list.size());
		
		RecipientInfo targetRecipientInfo = list.get(0);
		Assert.assertEquals(RecipientAge.SENIOR, targetRecipientInfo.getAge());
		Assert.assertEquals(RecipientJob.STUDENT, targetRecipientInfo.getJob());
		Assert.assertEquals(RecipientGender.FEMALE, targetRecipientInfo.getGender());
		Assert.assertEquals("name", targetRecipientInfo.getName());
		
		
	}
	@Test
	public void testInsetMultiRecipient() throws Exception{
		Connection conn = getConnection();
		
		RecipientInfoDBMaintance dbMaintance = new  RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);
		
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.setName("name");
		recipientInfo.setAge(RecipientAge.SENIOR);
		recipientInfo.setJob(RecipientJob.STUDENT);
		recipientInfo.setGender(RecipientGender.FEMALE);
		recipientInfo.setHomePage("http://homepage");
		dao.add(recipientInfo);

		RecipientInfo recipientInfo2 = new RecipientInfo();
		recipientInfo2.setName("name2");
		recipientInfo2.setAge(RecipientAge.SENIOR);
		recipientInfo2.setJob(RecipientJob.STUDENT);
		recipientInfo2.setGender(RecipientGender.FEMALE);
		recipientInfo2.setHomePage("http://homepage");
		dao.add(recipientInfo);

		
		List<RecipientInfo> list = dao.findAll();
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testExist() throws Exception{
		Connection conn = getConnection();
		
		RecipientInfoDBMaintance dbMaintance = new  RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);
		
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.setName("name");
		recipientInfo.setAge(RecipientAge.SENIOR);
		recipientInfo.setJob(RecipientJob.STUDENT);
		recipientInfo.setGender(RecipientGender.FEMALE);
		recipientInfo.setHomePage("http://homepage");
		dao.add(recipientInfo);
		
		Assert.assertTrue(dao.isExist(recipientInfo));
	}
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String dbFileName = "test.db";
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
		return conn;
		
	}

}
