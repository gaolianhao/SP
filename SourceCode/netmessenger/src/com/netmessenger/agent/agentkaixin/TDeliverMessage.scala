package com.netmessenger.agent.agentkaixin
import org.openqa.selenium.WebDriver
import com.netmessenger.core.IMessage
import org.openqa.selenium.By
import com.netmessenger.agent.agentkaixin.TCommon
import org.openqa.selenium.firefox.FirefoxDriver

trait TDeliverMessage extends TCommon{

   def deliverMessage(message: IMessage): Unit = {
    val driver = new FirefoxDriver();
    login(driver);
    retriableDo(driver, () => { gotoSendMessagePage(driver) });

    var recipientNum = retriableDo(driver, () => { sendMessage(driver, message) }).asInstanceOf[Int];

    retriableDo(driver, () => { taskReport(driver, recipientNum) });
    driver.quit();
  }

  def gotoSendMessagePage(driver: WebDriver): Unit = {
    val homePage = driver.getCurrentUrl();
    driver.findElement(By.xpath("//a[text()='发短消息']")).click();
    Thread.sleep(1000);
  }

  def sendMessage(driver: WebDriver, message: IMessage): Int = {
    driver.findElement(By.xpath("//div[@id='supersuggestxx_sh']//img"))
      .click();
    Thread.sleep(1000);
    val friendList = driver
      .findElements(By
        .xpath("//ul[@id='supersuggestviewall_friends_list']//input"));
    for (i <- 0 until friendList.size()) {
      friendList.get(i).click();
    }
    Thread.sleep(1000);
    driver.findElement(
      By.xpath("//div[@id='supersuggestviewdiv_btn_confirm']//input"))
      .click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//div[@id='content_div']/textarea"))
      .sendKeys(message.getContent());
    Thread.sleep(1000);
    driver.findElement(By.xpath("//input[@name='send_separate']"))
      .click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//input[@id='sendbtn']")).click();
    Thread.sleep(2000);
    return friendList.size();

  }

  def taskReport(driver: WebDriver, recipientNum: Int): Unit = {
    val successMark = driver.findElement(By
      .xpath("//div[@class='bqd_on' and text()='发件箱']"));
    if (successMark != null && successMark.isDisplayed()) {
      System.out.println("Message sent out to " + recipientNum
        + " people");
    }
  }
}