package com.netmessenger.agent;

import org.openqa.selenium.WebDriver;

/**
 * retry failed step 
 *
 */
abstract class Step (driver : WebDriver){


	def runStep() : Any;
	
	def run() = 
	{
		
		val startingPage = driver.getCurrentUrl();
		val retryTimes = 2;
		
		for (i <- 1 until retryTimes) {
			try {
				val result = runStep();
			} catch {
			case e : Exception => {
		
				System.out.println("Exception occured:" + e.getMessage());
				System.out.println("Retrying the " + i + " time");}
				throw new RuntimeException(e);
			}
		}
		
	}

}
