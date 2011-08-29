package com.netmessenger.agent

trait TAgentSafeOperation {

  protected def safelyRetriableDo(driver: LiteWebDriver, func: () => Any): Any = {
    val startingPage = driver.currentUrl;
    val retryTimes = 2;
    for (i <- 0 until retryTimes) {
      try {
        val result = func();
        return result;
      } catch {
        case e: Exception => {
          System.out.println("Exception occured:" + e.getMessage());
          System.out.println("Retrying the " + i + " time");
        }
      }
    }
    return null;
  }
  
  protected def tryDo(func: () => Any) : Any = {
    try{
      return func();
    }catch{
      case e:Exception => {
        return null;
      }
    }
  }
}