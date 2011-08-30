package com.netmessenger.agent.agentkaixin.datastore;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class RecipientInfoDBMaintanceTest {

	@Test
	public void testCreateAndClearTable() throws Exception{
		Class.forName("org.sqlite.JDBC");
		String dbFileName = "test.db";
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
		RecipientInfoDBMaintance dbMaintance = new  RecipientInfoDBMaintance(conn);
		dbMaintance.createTable();
		dbMaintance.clearTable();
	}

}
