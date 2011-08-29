package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.recipient.RecipientInfo
import com.netmessenger.recipient.RecipientInfoDAO
import com.netmessenger.agent.LiteWebDriver

trait TFuelAgent extends TCommon {

  def fuelAgent(dao : RecipientInfoDAO) = {
    val driver = new LiteWebDriver(new FirefoxDriver());
    login(driver);
    val friendSize = countFriends(driver)
    
    var number = 0;
    for (i <- 0 until friendSize) {
      val recipientInfo = grabRecipientInfo(driver, i);
      if (!dao.isExist(recipientInfo)){
        number = number + 1;
        dao.add(recipientInfo);
      }
    }
    System.out.println("add " + number + " new customers");
    driver.quit();
    dao.save();
  }

  def grabRecipientInfo(driver: LiteWebDriver, friendIndex: Int): RecipientInfo = {
    retriableDo(driver, () => {
      
      driver.click("//a[@class='t_link']//span[text()='首页']");

      val friendList = driver.findElements("//div[@id='homeflist']//div[@class='vafcon']//a");
      friendList(friendIndex).click();

      val name = driver.getText("//div[@id='divstate0']//strong");
      val gender = driver.getText("//div[@class='sy_pr2']//tr[1]//span");

      val recipientInfo = new RecipientInfo();
      recipientInfo.setName(name);
      recipientInfo.setGender(parseGender(gender));
      recipientInfo.setHomePage(driver.currentUrl);
      return recipientInfo;
      
    }).asInstanceOf[RecipientInfo];
    
  }
  
  private def parseGender(gender: String): RecipientGender = {
    if ("男".equals(gender)) {
      return RecipientGender.MALE;
    } else if ("女".equals(gender)) {
      return RecipientGender.FEMALE;
    } else {
      return RecipientGender.ALL;
    }
  }
  
  private def countFriends(driver: com.netmessenger.agent.LiteWebDriver): Int = {
    val friendList = driver.findElements("//div[@id='homeflist']//div[@class='vafcon']//a");
    var friendSize = friendList.size;
    friendSize
  }
}