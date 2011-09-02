package com.netmessenger.agent.renren
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.agent.renren.datastore.IRecipientInfoDAO
import com.netmessenger.agent.renren.datastore.RecipientInfo
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
    Counter.number = 0;
    
    grabIndepthFriends(driver, dao, 0, grabDepth, Set());
    System.out.println("Now you have " + dao.countRecipients() + " customers in total");
    dao.save();
    driver.quit();
  }

  private def grabFirstLevelFriends(driver: SmartWebDriver, dao: IRecipientInfoDAO, friendIndex: Int): Unit = {
    safelyRetriableDo(driver, () => {
      val currentUrl = driver.currentUrl;

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
      
      val newStackSet = stackSet+currentUrl;
      saveCurrentPageRecipientInfo(driver, dao);
      
      if (currentDepth >= maxDepth) return ;
      
      //loop friends of current recipient
      var friendList = driver.findElements(friendsXPath);
      var urlList = friendList.map((ele) => {ele.getAttribute("href")});
      urlList = urlList.distinct;
      logger.info("found friends : " + urlList.size);
      
      for (i <- 0 until urlList.size) {
        if(stackSet.contains(urlList(i))) {
          println("duplicated url : " + urlList(i));
        }
        else{
          logger.info("jump to " + urlList(i));
          driver.goto(urlList(i));
          grabIndepthFriends(driver, dao, currentDepth + 1, maxDepth, newStackSet);
        }
      }
    });
  }
  
  private def parseGender(gender: String): RecipientGender = {
    if ("男生".equals(gender)) {
      return RecipientGender.MALE;
    } else if ("女生".equals(gender)) {
      return RecipientGender.FEMALE;
    } else {
      return RecipientGender.ALL;
    }
  }

  
  private def saveCurrentPageRecipientInfo(driver: SmartWebDriver, dao: IRecipientInfoDAO): Unit = {
    tryDo(() => {
      
      //save current recipient
      val name = driver.tryGetText(Array(
          "//h1[contains(@class,'username')]"));
      
      val gender = driver.tryGetText(Array(
          "//ul[@class='user-info clearfix']/li[@class='gender']/span",
          "//div[@id='basicInfo']//dl[@class='info']//dd[1]"));

      val recipientInfo = new RecipientInfo();
      recipientInfo.name = name;
      recipientInfo.gender = parseGender(gender);
      recipientInfo.homePage = driver.currentUrl;

      if (!dao.isExist(recipientInfo)) {
        dao.add(recipientInfo);
        var logger = Logger.getLogger(this.getClass().getName());
        Counter.number = Counter.number + 1;
        logger.info(Counter.number + " got recipient info : " + name);
      }
      

    }); 
  }
  
  private def friendsXPath: java.lang.String = {
    "//ul[@class='people-list']//a"
  }
}