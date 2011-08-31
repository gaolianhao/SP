package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver
import java.util.Properties


trait TCommon extends TAgentSafeOperation {
  
  def login(driver: LiteWebDriver, prop:Properties): Unit = {
    val emailXPath = "//input[@name='email']"
    driver.goto("http://www.kaixin001.com/");
    driver.clear(emailXPath);
    driver.input(emailXPath,prop.getProperty("username"));
    driver.input("//input[@name='password']",prop.getProperty("password"));
    driver.click("//input[@id='btn_dl']");
  }
  

}