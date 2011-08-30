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
		val domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		val builder = domFactory.newDocumentBuilder();
		var doc = builder.parse(java.lang.Thread.currentThread().getContextClassLoader().getResource("/database.xml").getPath());

		val factory = XPathFactory.newInstance();
		val xPath = factory.newXPath();
		val target = "//database/file";

		val expr = xPath.compile(target);
		val nodes = expr.evaluate(doc, XPathConstants.NODESET).asInstanceOf[NodeList];
		return nodes.item(0).getTextContent();
	}
}