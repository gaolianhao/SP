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

class AgentManager (agentSource : InputStream) extends IAgentManager {

	private val agents = readDataFromFile();
	
	
	def  findAgent( message:IMessage) : List[IAgent] = agents;
		
	
	private def readDataFromFile() : LinkedList[IAgent] = {
		try {
			val agents = new LinkedList[IAgent]()
			val domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); 
		    val builder = domFactory.newDocumentBuilder();
		    val doc = builder.parse(agentSource);
		    
			val factory = XPathFactory.newInstance();
			val xPath = factory.newXPath();
			var target = "//agents/agent";
			
			val expr = xPath.compile(target);
			val nodes = expr.evaluate(doc,XPathConstants.NODESET).asInstanceOf[NodeList];
			for(i <-0 until nodes.getLength()){
				var node = nodes.item(i);
				var attributes = node.getAttributes();
				val agent = buildAgent(attributes);
				agents.add(agent);
			}
			return agents;
		} catch {
		case e : Exception => 
		  	{e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	private def buildAgent( attributes:NamedNodeMap):Agent = {
		
		var className = attributes.getNamedItem("class").getNodeValue();
		val constructor = Class.forName(className).getConstructor(classOf[String]);
		
		return constructor.newInstance(attributes.getNamedItem("name").getNodeValue()).asInstanceOf[Agent];
	}
}
