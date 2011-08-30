package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver

trait TCommon extends TAgentSafeOperation {
  
  def login(driver: LiteWebDriver): Unit = {
    val emailXPath = "//input[@name='email']"
    driver.goto("http://www.kaixin001.com/");
    driver.clear(emailXPath);
    driver.input(emailXPath,"liano_x@sohu.com");
    driver.input("//input[@name='password']", "19811011");
    driver.click("//input[@id='btn_dl']");
  }
}