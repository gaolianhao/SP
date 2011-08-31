package com.netmessenger.agent;

import org.openqa.selenium.WebDriver;

/**
 * retry failed step 
 *
 */
public abstract class Step {

	protected WebDriver driver;
	protected abstract Object runStep() throws Exception;
	
	public Step(WebDriver driver){
		this.driver = driver;
	}
	public Object run() {
		Exception exception = null;
		String startingPage = driver.getCurrentUrl();
		int retrytimes = 2;
		for (int i = 1; i <= retrytimes ; i++) {
			try {
				Object result = runStep();
				return result;
			} catch (Exception e) {
				 exception = e;
				System.out.println("Exception occured:" + e.getMessage());
				System.out.println("Retrying the " + i + " time");
			}
		}
		throw new RuntimeException(exception);
	}

}
