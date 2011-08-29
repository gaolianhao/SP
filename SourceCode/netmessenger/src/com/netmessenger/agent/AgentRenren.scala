package com.netmessenger.agent;

import java.util.List
import java.util.NoSuchElementException
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.core.IMessage
import com.netmessenger.core.recipientprofile.RecipientAge
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.recipient.RecipientInfoDAO

class AgentRenren(name: String) extends Agent(name) {

  override def deliverMessage(message: IMessage) = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    // step1
    safelyRetriableDo(driver, () => { login(driver) });

    // step2
    val friendList = driver.findElements("//ul[@class='people-list']//span[@class='headpichold']/a");
    var recipientNum = 0;
    var friendListSize = friendList.size;
    for (i <- 0 until friendListSize) {
      var isSendout = safelyRetriableDo(driver, () => { sendMessageToOneRecipient(driver, i, message) }).asInstanceOf[Boolean];
      if (isSendout) recipientNum = recipientNum + 1;
    }

    driver.quit();

  }

  override def fuelAgent = {

  }

  def login(driver: LiteWebDriver): Unit = {
    val emailXPath = "input[@id='email']"

    driver.goto("http://www.renren.com/");
    driver.clear(emailXPath);
    driver.input(emailXPath, "gaolianhao@sohu.com");
    driver.input("input[@id='password']", "19811011");
    driver.click("input[@id='login']");
    
    Thread.sleep(2000);
  }

  def sendMessageToOneRecipient(driver: LiteWebDriver, friendIndex: Int, message: IMessage): Boolean = {
    driver.click("//div[@class='menu-title']/a[text()='首页']");

    val friendList = driver.findElements("//ul[@class='people-list']//span[@class='headpichold']/a");
    friendList(friendIndex).click();

    var friendlyMessage = buildFriendlyMessage(driver, message);

    driver.click("div[@id='proTabFeedId_']");

    driver.input("//div[@class='m-editor-textarea']/textarea[@id='cmtbody']",friendlyMessage);

    driver.click("//input[@id='whisper']");

    driver.click("//input[@id='commentPostBtn']");

    return true;
  }

  private def buildFriendlyMessage(driver: LiteWebDriver, message: IMessage): String = {
    var gender = "";
    var name = "";
    try {
      val gender = driver.getText("//ul[@class='user-info clearfix']/li[@class='gender']/span");
    } catch {
      case e: Exception => {
        driver.click("input[@id='proTabInfoId_']");
        Thread.sleep(1000);
        try {
          gender = driver.getText("//div[@id='basicInfo']//dl[@class='info']//dd[1]");
        } catch {
          case e: Exception => {}
        }

      }
    }

    name = driver.getText("//h1[contains(@class,'username')]");

    var callStr = "";
    if ("男".equals(gender)) {
      callStr = name + " 帅哥，";
    } else if ("女".equals(gender)) {
      callStr = name + " 美女，";
    } else {
      callStr = "Hello,";
    }

    return callStr + "\n\r" + message.getContent();
  }

}

