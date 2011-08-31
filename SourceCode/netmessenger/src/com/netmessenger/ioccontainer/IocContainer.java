package com.netmessenger.ioccontainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import com.netmessenger.agent.AgentManager;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessageManager;
import com.netmessenger.message.MessageManager;

public class IocContainer {
	public static IocContainer INSTANCE = new IocContainer();
	
	public IMessageManager getMessageManager(){
		return new MessageManager(getInputStreamOf("messages.xml"));
	}
	
	public IAgentManager getAgentManager(){
		return new AgentManager(getInputStreamOf("agents.xml"));
	}
	
	private InputStream getInputStreamOf(String filePath) {
		try {
			URL resource = Thread.currentThread().getContextClassLoader().getResource(filePath);
//			URL resource = this.getClass().getResource(filePath);
			String fullFilePath = resource.getFile();
			System.out.println(fullFilePath);

			return new FileInputStream(fullFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
}
