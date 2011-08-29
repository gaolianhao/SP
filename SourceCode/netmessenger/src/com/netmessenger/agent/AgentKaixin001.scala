package com.netmessenger.agent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IMessage;
import com.netmessenger.recipient.RecipientInfoDAO;

class AgentKaixin001(name: String, dao: RecipientInfoDAO) extends Agent(name, dao) {

  override def deliverMessage(message: IMessage) {
    val driver = new FirefoxDriver();

    // step1
    retriableDo(driver, () => { login(driver) });

    // step2
    retriableDo(driver, () => { gotoSendMessagePage(driver) });

    // step3
    var recipientNum = retriableDo(driver, () => { sendMessage(driver, message) }).asInstanceOf[Int];

    // step4
    retriableDo(driver, () => { taskReport(driver, recipientNum) });

    driver.quit();

  }

  def login(driver: WebDriver): Unit = {
    driver.get("http://www.kaixin001.com/");
    val username = driver.findElement(By.name("email"));
    username.clear();
    username.sendKeys("liano_x@sohu.com");
    val password = driver.findElement(By.name("password"));
    password.sendKeys("19811011");
    val submitBt = driver.findElement(By.id("btn_dl"));
    submitBt.click();
    Thread.sleep(3000);
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

  override def fuelAgent = {

  }
}