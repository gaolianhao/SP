package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.netmessenger.core.IMessage
import com.netmessenger.agent.LiteWebDriver
import com.netmessenger.recipient.RecipientInfoDAO
import com.netmessenger.recipient.RecipientInfo
import scala.collection.JavaConversions._

trait TDeliverMessage extends TCommon{

  def deliverMessage(message: IMessage, dao: RecipientInfoDAO): Unit = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    login(driver);

    val recipientList = dao.getRecipientInfoList();
    recipientList.foreach(recipientInfo => {
      sendMessage(driver, recipientInfo, message);
    });

    driver.quit();
  }

  private def findRecipients(dao: RecipientInfoDAO): List[RecipientInfo] = {
    return dao.getRecipientInfoList().toList;
  }
  
  private def sendMessage(driver: LiteWebDriver,recipientInfo:RecipientInfo, message: IMessage): Unit = {
    safelyRetriableDo(driver, () => {
      driver.goto(recipientInfo.getHomePage());
      driver.click("//a[text()='发短消息']");
      driver.input("//div[@id='content_div']/textarea", message.getContent());
      driver.findElement("//input[@id='sendbtn']").click();
    });
  }

}