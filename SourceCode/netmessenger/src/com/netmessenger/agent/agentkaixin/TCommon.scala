package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.netmessenger.agent.TAgentSafeOperation

trait TCommon extends TAgentSafeOperation {
  
  def login(driver: WebDriver): Unit = {
    driver.get("http://www.kaixin001.com/");
    val username = driver.findElement(By.name("email"));
    username.clear();
    username.sendKeys("liano_x@sohu.com");
    val password = driver.findElement(By.name("password"));
    password.sendKeys("19811011");
    val submitBt = driver.findElement(By.id("btn_dl"));
    submitBt.click();
    Thread.sleep(3000);
  }
}