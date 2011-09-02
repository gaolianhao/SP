package com.netmessenger.agent.base
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SmartWebDriver(driver : WebDriver) extends LiteWebDriver(driver) with TAgentSafeOperation{

  def tryGetText(xpaths : Array[String]) : String = {
    xpaths.foreach(xpath => {
    	val result = tryDo(()=>this.getText(xpath));
    	if(result != null) return result.asInstanceOf[String];
    });
    throw new Exception("text [" + xpaths.reduceLeft((a:String,b:String)=>{a + "," + b}) + "] can't be find on path : " + this.currentUrl);
  }
  
  def tryFindElements(xpaths : Array[String]) : List[WebElement] = {
    xpaths.foreach(xpath => {
    	val result = tryDo(()=>this.findElements(xpath)).asInstanceOf[List[WebElement]];
    	if(result != null) return result;
    });
    throw new Exception("elements [" + xpaths.toString() + "] can't be find on path : " + this.currentUrl);
  }
}