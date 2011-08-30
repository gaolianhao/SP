package com.netmessenger.agent
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import scala.collection.JavaConversions._

class LiteWebDriver (driver : WebDriver) {

  def click(xpath:String):Unit = {
    driver.findElement(By.xpath(xpath)).click();
    Thread.sleep(1000);
  }

  def click(ele:WebElement):Unit = {
    ele.click();
    Thread.sleep(1000);
  }

  def findElements(xpath:String):List[WebElement] = {
     return driver.findElements(By.xpath(xpath)).toList;
  }
  
  def findElement(xpath:String):WebElement = {
     return driver.findElement(By.xpath(xpath));
  }
  
  def goto(url : String) : Unit = {
    driver.get(url);
    Thread.sleep(2000);
  }
  
  def currentUrl : String = {
    return driver.getCurrentUrl();
  }
  
  def input(xpath:String, text:String) : Unit = {
    driver.findElement(By.xpath(xpath)).sendKeys(text);
  }
  
  def clear(xpath:String) : Unit = {
    driver.findElement(By.xpath(xpath)).clear();
  }

  def getText(xpath: String): String = {
    driver.findElement(By.xpath(xpath)).getText();
  }
  
  def quit() : Unit= {
    driver.quit();
  }
}