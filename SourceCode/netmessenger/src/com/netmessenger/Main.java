package com.netmessenger;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessage;
import com.netmessenger.core.IMessageManager;
import com.netmessenger.ioccontainer.IocContainer;

public class Main {

	public static void main(String[] args){
		IAgentManager agentManager = IocContainer.INSTANCE.getAgentManager();
		IMessageManager messageManager = IocContainer.INSTANCE.getMessageManager(); 
		IMessage message = messageManager.findMessage();
		List<IAgent> agents = agentManager.findAgent(message);
		for(int i=0;i<agents.size();i++){
			agents.get(i).deliverMessage(message);
		}
		
	}
	
	 public static void main2(String[] args){
		 System.out.println("dddd");
		 
		// The Firefox driver supports javascript 
	        WebDriver driver = new FirefoxDriver();
	        
	        // Go to the Google Suggest home page
	        driver.get("http://www.google.com/webhp?complete=1&hl=en");
	        
	        // Enter the query string "Cheese"
	        WebElement query = driver.findElement(By.name("q"));
	        query.sendKeys("Cheese");

	        // Sleep until the div we want is visible or 5 seconds is over
	        long end = System.currentTimeMillis() + 5000;
	        while (System.currentTimeMillis() < end) {
	            WebElement resultsDiv = driver.findElement(By.className("gac_m"));

	            // If results have been returned, the results are displayed in a drop down.
	            if (resultsDiv.isDisplayed()) {
	              break;
	            }
	        }

	        // And now list the suggestions
	        List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gac_c']"));
	        
	        for (WebElement suggestion : allSuggestions) {
	            System.out.println(suggestion.getText());
	        }
	     }

	 
}
