package com.netmessenger.database;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DBConnection {

	private Connection conn;

	public DBConnection() throws Exception{
			Class.forName("org.sqlite.JDBC");
			String dbFileName = getFileName();
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	private String getFileName() throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(this.getClass().getResource("/database.xml").getPath());

		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		String target = "//database/file";

		XPathExpression expr = xPath.compile(target);
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes.item(0).getTextContent();
	}

	
}
