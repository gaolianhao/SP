package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.netmessenger.core.IMessage
import com.netmessenger.agent.base.LiteWebDriver
import scala.collection.JavaConversions._
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfo
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDAO
import com.netmessenger.agent.base.LiteWebDriver
import java.util.Properties

trait TDeliverMessage extends TCommon{

  def deliverMessage(message: IMessage, dao: IRecipientInfoDAO, prop:Properties): Unit = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    login(driver,prop);

    val num = dao.goThroughAll((recipientInfo)=>{
      sendMessage(driver, recipientInfo, message);
    });
    logger.info("prepare send message to " + num + " people");

    driver.quit();
  }

  private def selectRecipients(recipientList : List[RecipientInfo]) : List[RecipientInfo] = {
    println("prepare send message to " + recipientList.size + " people");
    return recipientList
  }
  
  private def sendMessage(driver: LiteWebDriver,recipientInfo:RecipientInfo, message: IMessage): Unit = {
    safelyRetriableDo(driver, () => {
      logger.info("send message to " + recipientInfo.name);
      
      driver.goto(recipientInfo.homePage);
      driver.click("//a[text()='发短消息']");
      driver.input("//div[@id='content_div']/textarea", buildFriendlyMessage(recipientInfo, message));
      driver.findElement("//input[@id='sendbtn']").click();
    });
  }
  
  private def buildFriendlyMessage(recipientInfo : RecipientInfo, message : IMessage) : String = {
    var niceCall = "";
    if(RecipientGender.FEMALE == recipientInfo.gender){
      niceCall = recipientInfo.name +" 美女,\n";
    }else if(RecipientGender.MALE == recipientInfo.gender){
    	niceCall = recipientInfo.name +" 帅哥,\n";
    }else{
      niceCall = "Hello " + recipientInfo.name + "\n";
    }
    
    return niceCall + message.getContent();
  }

}