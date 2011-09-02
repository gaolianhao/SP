package com.netmessenger.agent;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessage;

public class AgentManager implements IAgentManager {

	
	private List<IAgent> agents = new LinkedList<IAgent>();
	private InputStream agentSource;
	
	public AgentManager (InputStream agentSource ){
		this.agentSource = agentSource;
		agents = readDataFromFile();;
	}
	
	public List<IAgent>  findAgent( IMessage message){
		return  agents;
	}
		
	
	private  LinkedList<IAgent> readDataFromFile()  {
		try {
			LinkedList<IAgent> agents = new LinkedList<IAgent>();
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); 
		     DocumentBuilder builder = domFactory.newDocumentBuilder();
		     Object doc = builder.parse(agentSource);
		    
			 XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			String target = "//agents/agent";
			
			XPathExpression expr = xPath.compile(target);
			NodeList nodes = (NodeList)expr.evaluate(doc,XPathConstants.NODESET);
			for(int i=0;i< nodes.getLength();i++){
				 Node node = nodes.item(i);
				 NamedNodeMap attributes = node.getAttributes();
				 if(agentEnabled(attributes)){
					 IAgent agent = buildAgent(attributes);
					 agents.add(agent);
				 }
			}
			return agents;
		} catch (Exception e){
		 
		  	e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			
		}
	}

	private IAgent buildAgent( NamedNodeMap attributes) throws Exception {
		
		 String className = attributes.getNamedItem("class").getNodeValue();
		return (IAgent)Class.forName(className).newInstance();
	}
	
	private  Boolean agentEnabled(NamedNodeMap attributes) throws Exception{
			return Boolean.parseBoolean(attributes.getNamedItem("enabled").getNodeValue());
	}
}
