package com.netmessenger;

import java.util.List;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessage;
import com.netmessenger.core.IMessageManager;
import com.netmessenger.ioccontainer.IocContainer;

public class Main {

	private static boolean isDeliver = false;
	private static boolean isFuel = false;
	private static boolean isInitDB = false;

	public static void main(String[] args){
		
		if(args.length > 0 && "deliver".equals(args[0])){
			isDeliver = true;
		}
		else if (args.length > 0 && "fuel".equals(args[0])){
			isFuel = true;
		}
		else if (args.length > 0 && "initdb".equals(args[0])){
			isInitDB = true;
		}		
		else{
			System.out.println("one of the parameters should be input");
			System.out.println("fuel");
			System.out.println("deliver");
			System.out.println("initdb");
			return;
		}
		
		IAgentManager agentManager = IocContainer.INSTANCE.getAgentManager();
		IMessageManager messageManager = IocContainer.INSTANCE.getMessageManager(); 
		IMessage message = messageManager.findMessage();
		List<IAgent> agents = agentManager.findAgent(message);
		for(int i=0;i<agents.size();i++){
			if(isDeliver) agents.get(i).deliverMessage(message);
			if(isFuel) agents.get(i).fuelAgent();
			if(isInitDB) agents.get(i).prepareRunningEnvironment();
		}
		
	}
	
}
