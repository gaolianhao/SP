package com.netmessenger.database;

import org.junit.Assert;
import org.junit.Test;

public class DBConnectionTest {

	@Test
	public void test() throws Exception {
		Assert.assertNotNull(DBConnection.getConnection());
	}

}
