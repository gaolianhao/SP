package com.netmessenger.agent;

import com.netmessenger.core.IAgent
import com.netmessenger.core.IMessage
import com.netmessenger.recipient.RecipientInfoDAO;
import org.openqa.selenium.WebDriver

abstract class Agent(name: String) extends IAgent with TAgentSafeOperation{

  def getName(): String = name;

}
