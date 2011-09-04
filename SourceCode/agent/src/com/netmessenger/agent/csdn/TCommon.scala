package com.netmessenger.agent.csdn
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.agent.base.TAgentSafeOperation
import com.netmessenger.agent.base.LiteWebDriver
import java.util.Properties
import org.apache.log4j.Logger

trait TCommon extends TAgentSafeOperation {

  def login(driver: LiteWebDriver, prop: Properties): Unit = {
    val emailXPath = "//input[@id='u']"
    driver.goto("http://passport.csdn.net/account/login");
    driver.findById("u").clear();
    driver.findById("u").sendKeys(prop.getProperty("username"));
    driver.findById("p").sendKeys(prop.getProperty("password"));
    driver.findById("aLogin").click();
    Thread.sleep(1000);
  }

  override def logger: Logger = {
    Logger.getLogger(this.getClass().getName());
  }

}

object Counter {
  var number = 0;
}