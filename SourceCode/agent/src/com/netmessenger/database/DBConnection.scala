package com.netmessenger.database
import java.sql.Connection
import java.sql.DriverManager
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory
import org.w3c.dom.NodeList
import javax.xml.xpath.XPathConstants

object DBConnection {

  def getConnection() : Connection = {
		Class.forName("org.sqlite.JDBC");
		var dbFileName = getFileName();
		var conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
		return conn;
	}
	
	def getFileName() : String = {
		Thread.currentThread().getContextClassLoader().getResource("netmessenger.db").getFile();
	}
}