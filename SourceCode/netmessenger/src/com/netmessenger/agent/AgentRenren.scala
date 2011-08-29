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

class AgentRenren(name: String, dao: RecipientInfoDAO) extends Agent(name, dao) {

  override def deliverMessage(message: IMessage) = {
    val driver = new FirefoxDriver();
    // step1
    retriableDo(driver, () => { login(driver) });

    // step2
    val friendList = driver.findElements(By.xpath("//ul[@class='people-list']//span[@class='headpichold']/a"));
    var recipientNum = 0;
    var friendListSize = friendList.size();
    for (i <- 0 until friendListSize) {
      var isSendout = retriableDo(driver, () => { sendMessageToOneRecipient(driver, i, message) }).asInstanceOf[Boolean];
      if (isSendout) recipientNum = recipientNum + 1;
    }

    driver.quit();

  }

  override def fuelAgent = {

  }

  def login(driver: WebDriver): Unit = {
    driver.get("http://www.renren.com/");
    val username = driver.findElement(By.id("email"));
    username.clear();
    username.sendKeys("gaolianhao@sohu.com");
    val password = driver.findElement(By.id("password"));
    password.sendKeys("19811011");
    val submitBt = driver.findElement(By.id("login"));
    submitBt.click();
    Thread.sleep(2000);
  }

  def sendMessageToOneRecipient(driver: WebDriver, friendIndex: Int, message: IMessage): Boolean = {
    driver.findElement(By.xpath("//div[@class='menu-title']/a[text()='首页']")).click();
    Thread.sleep(1000);

    val friendList = driver.findElements(By.xpath("//ul[@class='people-list']//span[@class='headpichold']/a"));
    friendList.get(friendIndex).click();
    Thread.sleep(1000);

    var friendlyMessage = buildFriendlyMessage(driver, message);

    driver.findElement(By.id("proTabFeedId_")).click();
    Thread.sleep(1000);

    driver.findElement(By.xpath("//div[@class='m-editor-textarea']/textarea[@id='cmtbody']"))
      .sendKeys(friendlyMessage);
    Thread.sleep(1000);

    driver.findElement(By.id("whisper")).click();
    Thread.sleep(1000);

    driver.findElement(By.xpath("//input[@id='commentPostBtn']")).click();
    Thread.sleep(1000);

    return true;
  }

  private def buildFriendlyMessage(driver: WebDriver, message: IMessage): String = {
    var gender = "";
    var name = "";
    try {
      val genderEle = driver.findElement(By.xpath("//ul[@class='user-info clearfix']/li[@class='gender']/span"));
      gender = genderEle.getText().trim();
    } catch {
      case e: Exception => {
        driver.findElement(By.id("proTabInfoId_")).click();
        Thread.sleep(1000);
        try {
          gender = driver.findElement(By.xpath("//div[@id='basicInfo']//dl[@class='info']//dd[1]")).getText().trim();
        } catch {
          case e: Exception => {}
        }

      }
    }

    name = driver.findElement(By.xpath("//h1[contains(@class,'username')]")).getText().trim();

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

