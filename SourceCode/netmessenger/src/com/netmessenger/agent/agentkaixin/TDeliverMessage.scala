package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.netmessenger.core.IMessage
import com.netmessenger.agent.LiteWebDriver
import com.netmessenger.recipient.RecipientInfoDAO
import com.netmessenger.recipient.RecipientInfo
import scala.collection.JavaConversions._
import com.netmessenger.core.recipientprofile.RecipientGender

trait TDeliverMessage extends TCommon{

  def deliverMessage(message: IMessage, dao: RecipientInfoDAO): Unit = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    login(driver);

    val recipientList = dao.getRecipientInfoList();
    val selectedRecipients = selectRecipients(recipientList.toList);
    recipientList.foreach(recipientInfo => {
      sendMessage(driver, recipientInfo, message);
    });

    driver.quit();
  }

  private def selectRecipients(recipientList : List[RecipientInfo]) : List[RecipientInfo] = {
    println("prepare send message to " + recipientList.size + " people");
    return recipientList
  }
  private def findRecipients(dao: RecipientInfoDAO): List[RecipientInfo] = {
    return dao.getRecipientInfoList().toList;
  }
  
  private def sendMessage(driver: LiteWebDriver,recipientInfo:RecipientInfo, message: IMessage): Unit = {
    safelyRetriableDo(driver, () => {
      println("send message to " + recipientInfo.getName());
      
      driver.goto(recipientInfo.getHomePage());
      driver.click("//a[text()='发短消息']");
      driver.input("//div[@id='content_div']/textarea", buildFriendlyMessage(recipientInfo, message));
      driver.findElement("//input[@id='sendbtn']").click();
    });
  }
  
  private def buildFriendlyMessage(recipientInfo : RecipientInfo, message : IMessage) : String = {
    var niceCall = "";
    if(RecipientGender.FEMALE == recipientInfo.getGender()){
      niceCall = recipientInfo.getName() +" 美女,\n";
    }else if(RecipientGender.MALE == recipientInfo.getGender()){
    	niceCall = recipientInfo.getName() +" 帅哥,\n";
    }else{
      niceCall = "Hello " + recipientInfo.getName() + "\n";
    }
    
    return niceCall + message.getContent();
  }

}