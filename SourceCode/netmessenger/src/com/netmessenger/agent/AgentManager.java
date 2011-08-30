package com.netmessenger.agent;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class AgentManager implements IAgentManager {

	private InputStream agentSource;
	private LinkedList<IAgent> agents = new LinkedList<IAgent>();
	
	public AgentManager(InputStream agentSource){
		this.agentSource = agentSource;
		init();
	}
	
	public List<IAgent> findAgent(IMessage message) {
		
		return agents;
	}
	
	private void init() {
		try {
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); 
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(agentSource);
		    
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			String target = "//agents/agent";
			
			XPathExpression expr = xPath.compile(target);
			NodeList nodes = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
			for(int i=0;i<nodes.getLength();i++){
				Node node = nodes.item(i);
				NamedNodeMap attributes = node.getAttributes();
				Agent agent = buildAgent(attributes);
				agents.add(agent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private Agent buildAgent(NamedNodeMap attributes)
			throws NoSuchMethodException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, Exception {
		
		String className = attributes.getNamedItem("class").getNodeValue();
		Constructor constructor = Class.forName(className).getConstructor(new Class[]{RecipientAge.class, RecipientJob.class, RecipientGender.class});
		return (Agent)constructor.newInstance(
				RecipientAge.parse(attributes.getNamedItem(
						"recipientage").getNodeValue()),
				RecipientJob.parse(attributes.getNamedItem(
						"recipientjob").getNodeValue()),
				RecipientGender.parse(attributes.getNamedItem(
						"recipientgender").getNodeValue()));
	}
}
