package com.netmessenger.agent.base
import org.apache.log4j.Logger

trait TAgentSafeOperation {

   def safelyRetriableDo(driver: LiteWebDriver, func: () => Any): Any = {
    val startingPage = driver.currentUrl;
    val retryTimes = 2;
    for (i <- 0 until retryTimes) {
      try {
        val result = func();
        return result;
      } catch {
        case e:org.openqa.selenium.NoSuchElementException => {
          
        }
        case e: Exception => {
          logger.error(e);
          System.out.println("Retrying the " + i + " time");
        }
      }
    }
    return null;
  }
  
   def tryDo(func: () => Any) : Any = {
    try{
      return func();
    }catch{
      case e: org.openqa.selenium.NoSuchElementException => {
    	return null;
      }
      case e: Exception => {
        logger.error(e);
        return null;
      }
    }
  }
   
  def logger : Logger = {
    Logger.getLogger(this.getClass().getName());
  }
}