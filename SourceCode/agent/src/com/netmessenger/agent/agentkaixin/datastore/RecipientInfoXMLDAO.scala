package com.netmessenger.agent.agentkaixin.datastore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmessenger.core.recipientprofile.RecipientGender;

class RecipientInfoXMLDAO extends IRecipientInfoDAO{

	var recipientList = new LinkedList[RecipientInfo]();
	var map = new HashMap[String, RecipientInfo]();

	
	def isExist(recipientInfo:RecipientInfo) : Boolean = {
		return map.containsKey(recipientInfo.homePage);
	}
	
	def  add( recipientInfo:RecipientInfo) : Unit = {
		if(map.containsKey(recipientInfo.homePage)){
			return;
		}
		this.recipientList.add(recipientInfo);
		map.put(recipientInfo.homePage, recipientInfo);
	}
	
	def goThroughAll(func:(RecipientInfo) => Unit):Int = {
		for(i<-0 until recipientList.size()){
		  func(recipientList.get(i));
		}
	  return recipientList.size();
	}
	
	def  save() : Unit={
		try {
			val docFactory = DocumentBuilderFactory.newInstance();
			val docBuilder = docFactory.newDocumentBuilder();

			// root elements
			val doc = docBuilder.newDocument();
			val rootElement = doc.createElement("recipients");
			doc.appendChild(rootElement);

			
			for(i<-0 until recipientList.size()) {
				val recipientInfo = recipientList.get(i);

				val recipient = doc.createElement("recipient");
				rootElement.appendChild(recipient);
				recipient.setAttribute("name", recipientInfo.name);
				recipient.setAttribute("gender", recipientInfo.gender.toString());
				recipient.setAttribute("homepage", recipientInfo.homePage);
			}
			
			val transformerFactory = TransformerFactory.newInstance();
			val transformer = transformerFactory.newTransformer();
			val source = new DOMSource(doc);
			
			val file = new File(storeFileFullName());
			if(!file.exists()) file.createNewFile();
			val fout = new FileOutputStream(storeFileFullName());
			val bout = new BufferedOutputStream(fout);
			val out = new OutputStreamWriter(bout, "UTF-8");
			
			val result = new StreamResult(out);
			transformer.transform(source, result);
	 
			System.out.println("File saved at " + storeFileFullName());

		} catch{
		  case e:Exception => throw new RuntimeException(e);
		}

	}

	private def readData() : Unit = {
		try {
			val file = new File(storeFileFullName());
			if(!file.exists()) return;
			
			val recipientSource = new FileInputStream(file);

			val domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			val builder = domFactory.newDocumentBuilder();
			val doc = builder.parse(recipientSource);

			val factory = XPathFactory.newInstance();
			val xPath = factory.newXPath();
			val target = "/recipients/recipient";

			val expr = xPath.compile(target);
			val nodes = expr.evaluate(doc, XPathConstants.NODESET).asInstanceOf[NodeList];
			for (i<-0 until nodes.getLength()) {
				val node = nodes.item(i);
				val attributes = node.getAttributes();
				val recipientInfo = buildRecipientInfo(attributes);
				recipientList.add(recipientInfo);
				map.put(recipientInfo.homePage,recipientInfo);
			}
		} catch{
		  case e:Exception => throw new RuntimeException(e);
		}
	}

	def  storeFileFullName():String = {
		return this.getClass().getResource("/").getPath() + "recipients_of_kaixin.xml";		 
	}

	private def  buildRecipientInfo( attributes:NamedNodeMap) :RecipientInfo = {
		val recipientInfo = new RecipientInfo();
		recipientInfo.name = attributes.getNamedItem("name").getNodeValue();
		recipientInfo.homePage = attributes.getNamedItem("homepage").getNodeValue();
		recipientInfo.gender = RecipientGender.parse(attributes.getNamedItem("gender").getNodeValue());
		return recipientInfo;
	}


	override def countRecipients():Int = {
		
		return this.recipientList.size();
	}

	override def close() : Unit = {
		
		
	}
}
