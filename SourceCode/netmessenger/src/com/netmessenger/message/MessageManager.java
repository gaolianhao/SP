package com.netmessenger.message;

import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.IMessageManager;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class MessageManager implements IMessageManager {
	
	private LinkedList<Message> messages = new LinkedList<Message>();
	private InputStream messageFileStream;
	public MessageManager(InputStream messageFileStream) {
		this.messageFileStream = messageFileStream;
		init();
	}

	private void init() {
		try {
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(messageFileStream);
		    
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			String target = "//messages/message";
			
			XPathExpression expr = xPath.compile(target);
			NodeList nodes = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
			for(int i=0;i<nodes.getLength();i++){
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				
				Message message = new Message(
						RecipientAge.parse(attributes.getNamedItem("recipientage").getNodeValue()),
						RecipientJob.parse(attributes.getNamedItem("recipientjob").getNodeValue()),
						RecipientGender.parse(attributes.getNamedItem("recipientgender").getNodeValue()),
						attributes.getNamedItem("content").getNodeValue()
						);
				messages.add(message);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public IMessage findMessage() {
		return messages.getFirst();
	}
}
