package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfo
import com.netmessenger.agent.base.SmartWebDriver
import com.netmessenger.core.recipientprofile.RecipientGender
import java.util.Properties
import org.apache.log4j.Logger

trait TFuelAgent extends TCommon {

  def fuelAgent(dao : IRecipientInfoDAO, prop:Properties) = {
    val driver = new SmartWebDriver(new FirefoxDriver());
    login(driver,prop);
    val grabDepth =  prop.getProperty("grabdepth").toInt;
    val startUrl = prop.getProperty("starturl");
    driver.goto(startUrl);
    grabIndepthFriends(driver, dao, 0, grabDepth, Set());
    //grabFirstLevelFriends(driver, dao, 0);
    System.out.println("Now you have " + dao.countRecipients() + " customers in total");
    dao.save();
    driver.quit();
  }

  private def grabFirstLevelFriends(driver: SmartWebDriver, dao: IRecipientInfoDAO, friendIndex: Int): Unit = {
    safelyRetriableDo(driver, () => {
      val currentUrl = driver.currentUrl;
      val friendsXPath = "//div[@id='homeflist']//div[@class='vafcon']//a"

      var friendList = driver.findElements(friendsXPath);
      for (i <- 0 until friendList.size) {
        driver.click(friendList(i));

        saveCurrentPageRecipientInfo(driver, dao);
        
        driver.goto(currentUrl);
        friendList = driver.findElements(friendsXPath);
      }

    });

  }

  private def grabIndepthFriends(driver: SmartWebDriver, dao: IRecipientInfoDAO, currentDepth: Int, maxDepth: Int, stackSet : Set[String]): Unit = {
    safelyRetriableDo(driver, () => {
      //save current status
      val currentUrl = driver.currentUrl;
      
      //return when met duplicated target 
      if(stackSet.contains(currentUrl)) {
        println("duplicated url : " + currentUrl);
        return;
      }
      val newStackSet = stackSet+currentUrl;
      saveCurrentPageRecipientInfo(driver, dao);
      
      if (currentDepth >= maxDepth) return ;
      
      //loop friends of current recipient
      val friendsXPath = "//div[contains(@class,'vaflist2')]//a[@class='sl']"
      
      var friendList = driver.findElements(friendsXPath);
      var urlList = friendList.map((ele) => {ele.getAttribute("href")});
      urlList = urlList.distinct;
      logger.info("found friends : " + urlList.size);
      for (i <- 0 until urlList.size) {
        
        logger.info("\njump to " + urlList(i));
        driver.goto(urlList(i));
        grabIndepthFriends(driver, dao, currentDepth + 1, maxDepth, newStackSet);
      }
    });
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
  
  private def countFriends(driver: SmartWebDriver): Int = {
    val friendList = driver.findElements("//div[@id='homeflist']//div[@class='vafcon']//a");
    var friendSize = friendList.size;
    friendSize
  }
  
  private def saveCurrentPageRecipientInfo(driver: SmartWebDriver, dao: IRecipientInfoDAO): Unit = {
    tryDo(() => {
      //save current recipient
      val name = driver.tryGetText(Array(
          "//div[@id='divstate0']//strong",
          "//div[@id='divstate1']/b",
          "//div[@id='pubCnt']//strong"));
      
      val gender = driver.tryGetText(Array(
          "//div[@class='sy_pr2']//tr[1]//span",
          "//div[@class='myInfobox']//span[@class='sl'][1]"));

      val recipientInfo = new RecipientInfo();
      recipientInfo.name = name;
      recipientInfo.gender = parseGender(gender);
      recipientInfo.homePage = driver.currentUrl;
      dao.add(recipientInfo);
      
      var logger = Logger.getLogger(this.getClass().getName());
      logger.info("got recipient info : " + name);
    }); 
  }
}