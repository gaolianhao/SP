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
	public void testInsetOneRecipient() throws Exception {
		Connection conn = getConnection();

		RecipientInfoDBMaintance dbMaintance = new RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.name_$eq("name");
		recipientInfo.age_$eq(RecipientAge.SENIOR);
		recipientInfo.job_$eq(RecipientJob.STUDENT);
		recipientInfo.gender_$eq(RecipientGender.FEMALE);
		recipientInfo.homePage_$eq("http://homepage");
		dao.add(recipientInfo);

		List<RecipientInfo> list = dao.findAll();
		Assert.assertEquals(1, list.size());

		RecipientInfo targetRecipientInfo = list.get(0);
		Assert.assertEquals(RecipientAge.SENIOR, targetRecipientInfo.age());
		Assert.assertEquals(RecipientJob.STUDENT, targetRecipientInfo.job());
		Assert.assertEquals(RecipientGender.FEMALE, targetRecipientInfo.gender());
		Assert.assertEquals("name", targetRecipientInfo.name());

	}

	@Test
	public void testInsetMultiRecipient() throws Exception {
		Connection conn = getConnection();

		RecipientInfoDBMaintance dbMaintance = new RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);

		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.name_$eq("name");
		recipientInfo.age_$eq(RecipientAge.SENIOR);
		recipientInfo.job_$eq(RecipientJob.STUDENT);
		recipientInfo.gender_$eq(RecipientGender.FEMALE);
		recipientInfo.homePage_$eq("http://homepage");
		dao.add(recipientInfo);

		RecipientInfo recipientInfo2 = new RecipientInfo();
		recipientInfo2.name_$eq("name2");
		recipientInfo2.age_$eq(RecipientAge.SENIOR);
		recipientInfo2.job_$eq(RecipientJob.STUDENT);
		recipientInfo2.gender_$eq(RecipientGender.FEMALE);
		recipientInfo2.homePage_$eq("http://homepage2");
		dao.add(recipientInfo2);

		List<RecipientInfo> list = dao.findAll();
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void testExist() throws Exception {
		Connection conn = getConnection();

		RecipientInfoDBMaintance dbMaintance = new RecipientInfoDBMaintance(conn);
		dbMaintance.clearTable();
		dbMaintance.createTable();
		RecipientInfoDAO dao = new RecipientInfoDAO(conn);

		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.name_$eq("name");
		recipientInfo.age_$eq(RecipientAge.SENIOR);
		recipientInfo.job_$eq(RecipientJob.STUDENT);
		recipientInfo.gender_$eq(RecipientGender.FEMALE);
		recipientInfo.homePage_$eq("http://homepage");
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
