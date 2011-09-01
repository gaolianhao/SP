package com.netmessenger.agent.agentkaixin
import java.util.Properties
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfo
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.core.recipientprofile.RecipientAge
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.core.IMessage
import com.netmessenger.core.recipientprofile.RecipientJob

trait TDeliverMessage extends TCommon{

  def deliverMessage(message: IMessage, dao: IRecipientInfoDAO, prop:Properties): Unit = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    login(driver,prop);
    Counter.number = 0;
    dao.goThroughAll((recipientInfo)=>{
      sendMessage(driver, recipientInfo, message);
    });
    logger.info("message sent to " +  Counter.number + " people");

    driver.quit();
  }
  
  private def sendMessage(driver: LiteWebDriver,recipientInfo:RecipientInfo, message: IMessage): Unit = {
    //filter people
    if(shouldSkip(message, recipientInfo)){
      return;
    }
    
    safelyRetriableDo(driver, () => {
      logger.info("send message to " + recipientInfo.name);
      
      driver.goto(recipientInfo.homePage);
      driver.click("//a[text()='发短消息']");
      var finalMsg = message.getContent();
      if(message.autoDecor()){
        finalMsg = buildFriendlyMessage(recipientInfo, message);
      }
      driver.input("//div[@id='content_div']/textarea", finalMsg);
      driver.findElement("//input[@id='sendbtn']").click();
      
       Counter.number =  Counter.number + 1;
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

  private def shouldSkip(message:IMessage, recipientInfo:RecipientInfo):Boolean = {
    if(message.getRecipientAge() != RecipientAge.ALL && message.getRecipientAge() != recipientInfo.age){
    	return true;
    }
    if(message.getRecipientJob() != RecipientJob.ALL && message.getRecipientJob() != recipientInfo.job){
    	return true;
    }
    if(message.getRecipientGender() != RecipientGender.ALL && message.getRecipientGender() != recipientInfo.gender){
    	return true;
    }
    return false;
    
    
    
  }
}