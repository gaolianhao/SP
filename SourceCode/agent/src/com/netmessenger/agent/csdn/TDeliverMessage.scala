package com.netmessenger.agent.csdn
import java.util.Properties
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.agent.renren.datastore.IRecipientInfoDAO
import com.netmessenger.agent.renren.datastore.RecipientInfo
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.core.recipientprofile.RecipientAge
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.core.IMessage
import com.netmessenger.core.recipientprofile.RecipientJob
import com.netmessenger.agent.renren.datastore.IRecipientInfoDAO
import com.netmessenger.agent.base.SmartWebDriver
import org.openqa.selenium.WebElement

trait TDeliverMessage extends TCommon {

  def deliverMessage(message: IMessage, prop: Properties): Unit = {
    val driver = new SmartWebDriver(new FirefoxDriver());
//    login(driver, prop);
//    
     driver.goto("http://www.csdn.net");
    var articles1 = driver.findElements("//dl[@class='style2 txt_black']//a").map(ele => {ele.getAttribute("href")});
     articles1 = articles1.splitAt(3)._1;
    var articles2 = driver.findElements("//dt[@class='title']//a").map(ele => {ele.getText()});
    var articles = articles1:::articles2;
    articles.foreach((articleUrl) => sendMessage(driver, articleUrl, message));
    
    driver.quit();
  }

  def sendMessage(driver: SmartWebDriver, articleUrl: String, message: IMessage): Unit = {
    safelyRetriableDo(driver, () => {
      driver.goto(articleUrl);
      driver.input("//textarea", message.getContent());
      driver.click("//input[@class='submit']");
      logger.info("message sent out");
    });
  }

}