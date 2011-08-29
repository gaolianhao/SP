package com.netmessenger.recipient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

public class RecipientInfoDAO {

	private LinkedList<RecipientInfo> recipientList = new LinkedList< RecipientInfo>();
	private HashMap<String, RecipientInfo> map = new HashMap<String, RecipientInfo>();
	private String agentName;

	public RecipientInfoDAO(String agentName) {
		this.agentName = agentName;
		readData();
	}
	
	public boolean isExist(RecipientInfo recipientInfo){
		return map.containsKey(recipientInfo.homePage);
	}
	
	public void add(RecipientInfo recipientInfo){
		if(map.containsKey(recipientInfo.homePage)){
			return;
		}
		this.recipientList.add(recipientInfo);
		map.put(recipientInfo.homePage, recipientInfo);
	}
	
	public List<RecipientInfo> getRecipients(){
		return recipientList;
	}
	public void save() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("recipients");
			doc.appendChild(rootElement);

			
			for(int i=0;i<recipientList.size();i++) {
				RecipientInfo recipientInfo = recipientList.get(i);

				Element recipient = doc.createElement("recipient");
				rootElement.appendChild(recipient);
				recipient.setAttribute("name", recipientInfo.getName());
				recipient.setAttribute("gender", recipientInfo.getGender().toString());
				recipient.setAttribute("homepage", recipientInfo.getHomePage());
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			File file = new File(storeFileFullName());
			if(!file.exists()) file.createNewFile();
			OutputStream output = new FileOutputStream(storeFileFullName());
			StreamResult result = new StreamResult(output);
			transformer.transform(source, result);
	 
			System.out.println("File saved at " + storeFileFullName());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void readData() {
		try {
			File file = new File(storeFileFullName());
			if(!file.exists()) return;
			
			InputStream recipientSource = new FileInputStream(file);

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(recipientSource);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			String target = "/recipients/recipient";

			XPathExpression expr = xPath.compile(target);
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				NamedNodeMap attributes = node.getAttributes();
				RecipientInfo recipientInfo = buildRecipientInfo(attributes);
				recipientList.add(recipientInfo);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String storeFileFullName() {
		return this.getClass().getResource("/").getPath() + "recipients_of_" + agentName + ".xml";		 
	}

	private RecipientInfo buildRecipientInfo(NamedNodeMap attributes) throws Exception {
		RecipientInfo recipientInfo = new RecipientInfo();
		recipientInfo.setName(attributes.getNamedItem("name").getNodeValue());
		recipientInfo.setHomePage(attributes.getNamedItem("homepage").getNodeValue());
		recipientInfo.setGender(RecipientGender.parse(attributes.getNamedItem("gender").getNodeValue()));
		return recipientInfo;
	}



	public List<RecipientInfo> getRecipientInfoList() {
		
		return recipientList;
	}
}
