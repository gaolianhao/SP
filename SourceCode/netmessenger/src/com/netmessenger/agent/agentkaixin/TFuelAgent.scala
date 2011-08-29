package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.recipient.RecipientInfo
import org.openqa.selenium.WebDriver
import com.netmessenger.core.recipientprofile.RecipientGender
import org.openqa.selenium.By
import com.netmessenger.agent.agentkaixin.TCommon
import com.netmessenger.recipient.RecipientInfoDAO

trait TFuelAgent extends TCommon {

  def fuelAgent(dao : RecipientInfoDAO) = {
    val driver = new FirefoxDriver();
    login(driver);
    val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
    var number = 0;
    for (i <- 0 until friendList.size()) {
      val recipientInfo = retriableDo(driver, () => { grabRecipientInfo(driver, i) }).asInstanceOf[RecipientInfo];
      dao.add(recipientInfo);
      if (recipientInfo != null) number = number + 1;
    }
    System.out.println("add " + number + " new customers");
    driver.quit();
    dao.save();
  }
  
  def grabRecipientInfo(driver: WebDriver, friendIndex: Int): RecipientInfo = {
    driver.findElement(By.xpath("//a[@class='t_link']//span[text()='首页']")).click();
    Thread.sleep(1000);

    val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
    friendList.get(friendIndex).click();
    Thread.sleep(1000);

    val name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
    val gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();

    val recipientInfo = new RecipientInfo();
    recipientInfo.setName(name);
    recipientInfo.setGender(parseGender(gender));
    recipientInfo.setHomePage(driver.getCurrentUrl());
    return recipientInfo;
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
}