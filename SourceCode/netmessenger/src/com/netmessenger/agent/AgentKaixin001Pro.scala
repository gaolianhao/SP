package com.netmessenger.agent;

import java.util.List
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.core.IMessage
import com.netmessenger.core.recipientprofile.RecipientGender
import com.netmessenger.recipient.RecipientInfo;
import com.netmessenger.recipient.RecipientInfoDAO

/**
 * 
 * a smarter agent that can identify user profile
 *
 */
class AgentKaixin001Pro(name:String, dao:RecipientInfoDAO) extends Agent(name, dao) {

 

	override def deliverMessage(message : IMessage) = {
		val driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			var recipientNum  = 0;
			for(i <-0 until friendList.size()){
				var isSendout = (new SendMessageToOneRecipient(driver, i, message)).runStep().asInstanceOf[Boolean];
				if(isSendout) recipientNum = recipientNum + 1;
			}
			
			// step4
			(new TaskReport(driver, recipientNum)).runStep();

			driver.quit();

		} catch {
			case e:Exception => e.printStackTrace();
		}
	}
	
	
	def findAndSaveRecipientInfo= {
		val driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			var number = 0;
			for(i <- 0 until friendList.size()){
				val isDone = (new GrabRecipientInfo(driver, i)).runStep().asInstanceOf[Boolean];
				if(isDone) number = number + 1;
			}
			
			// step4
			System.out.println("add " + number + " new customers");

			driver.quit();

		} catch{
		  case e : Exception =>e.printStackTrace(); 
		}
		
	}
	
	class Login(driver:WebDriver) extends Step(driver){
		override def runStep() : Any = {
			driver.get("http://www.kaixin001.com/");
	        val username = driver.findElement(By.name("email"));
	        username.clear();
	        username.sendKeys("liano_x@sohu.com");
	        val password = driver.findElement(By.name("password"));
	        password.sendKeys("19811011");
	        val submitBt = driver.findElement(By.id("btn_dl"));
	        submitBt.click();
	        Thread.sleep(3000);
	        return null;
		}
		
	}
	
	class GrabRecipientInfo(driver:WebDriver, friendIndex: Int) extends Step(driver) {

		override def runStep() : Any = {
			driver.findElement(By.xpath("//a[@class='t_link']//span[text()='首页']")).click();
			Thread.sleep(1000);
			
			val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			friendList.get(friendIndex).click();
			Thread.sleep(1000);
			
			val name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			val gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			val recipientInfo = new RecipientInfo();
			recipientInfo.setName(name);
			recipientInfo.setGender(parseGender(gender));
			recipientInfo.setHomePage(driver.getCurrentUrl());
			dao.add(recipientInfo);
			
			Thread.sleep(1000);
			
			return true;
		}

		private def parseGender(gender : String) : RecipientGender = {
			if("男".equals(gender)){
				return RecipientGender.MALE;
			}else if("女".equals(gender)){
				return RecipientGender.FEMALE;
			}else{
				return RecipientGender.ALL;
			}
		}
		
	}
	
	class SendMessageToOneRecipient(driver:WebDriver, friendIndex : Int, message : IMessage) extends Step(driver) {

		override def runStep() : Any = {
			driver.findElement(By.xpath("//a[@class='t_link']//span[text()='首页']")).click();
			Thread.sleep(1000);
			
			val friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			friendList.get(friendIndex).click();
			Thread.sleep(1000);
			
			val name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			val gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			val recipientInfo = new RecipientInfo();
			recipientInfo.setName(name);
			recipientInfo.setGender(parseGender(gender));
			recipientInfo.setHomePage(driver.getCurrentUrl());
			dao.add(recipientInfo);
			
			val friendlyMessage = buildFriendlyMessage(driver, message);
			
			driver.findElement(By.xpath("//a[text()='发短消息']")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//div[@id='content_div']/textarea"))
					.sendKeys(friendlyMessage);
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//input[@id='sendbtn']")).click();
			Thread.sleep(2000);
			
			
			return true;
		}

		private def parseGender( gender :String) :RecipientGender ={
			if("男".equals(gender)){
				return RecipientGender.MALE;
			}else if("女".equals(gender)){
				return RecipientGender.FEMALE;
			}else{
				return RecipientGender.ALL;
			}
		}

		private def buildFriendlyMessage( driver:WebDriver,  message:IMessage):String ={
			val name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			val gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			var callStr = "";
			if("男".equals(gender)){
				callStr = name + " 小帅哥，";
			}else if("女".equals(gender)){
				callStr = name + " 小美女，";
			}else{
				callStr = "Hello,";
			}
			
			return callStr + "\n\r" + message.getContent();
		}
	}
	
	class TaskReport(driver:WebDriver, recipientNum : Int) extends Step(driver) {


		override def runStep() : Any={
			val successMark = driver.findElement(By
					.xpath("//div[@class='bqd_on' and text()='发件箱']"));
			if (successMark != null && successMark.isDisplayed()) {
				System.out.println("Message sent out to " + recipientNum
						+ " people");
			}
			return null;
		}

	}

	
}

