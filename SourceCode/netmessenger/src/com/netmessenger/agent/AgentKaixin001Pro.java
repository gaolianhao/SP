package com.netmessenger.agent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.recipient.RecipientInfo;

/**
 * 
 * a smarter agent that can identify user profile
 *
 */
public class AgentKaixin001Pro extends Agent {

 
	public AgentKaixin001Pro(String name) {
		super(name);
	}

	@Override
	public void deliverMessage(IMessage message) {
		WebDriver driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			List<WebElement> friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			int recipientNum = 0;
			for(int i=0; i< friendList.size(); i++){
				Boolean isSendout = (Boolean)(new SendMessageToOneRecipient(driver, i, message)).runStep();
				if(isSendout) recipientNum++;
			}
			
			// step4
			(new TaskReport(driver, recipientNum)).runStep();

			driver.quit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void findAndSaveRecipientInfo() {
		WebDriver driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			List<WebElement> friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			int number = 0;
			for(int i=0; i< friendList.size(); i++){
				Boolean isDone = (Boolean)(new GrabRecipientInfo(driver, i)).runStep();
				if(isDone) number++;
			}
			
			// step4
			System.out.println("add " + number + " new customers");

			driver.quit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class Login extends Step{
		public Login(WebDriver driver){
			super(driver);
		}
		protected Object runStep() throws Exception{
			driver.get("http://www.kaixin001.com/");
	        WebElement username = driver.findElement(By.name("email"));
	        username.clear();
	        username.sendKeys("liano_x@sohu.com");
	        WebElement password = driver.findElement(By.name("password"));
	        password.sendKeys("19811011");
	        WebElement submitBt = driver.findElement(By.id("btn_dl"));
	        submitBt.click();
	        Thread.sleep(3000);
	        return null;
		}
		
	}
	
	class GrabRecipientInfo extends Step {
		private int friendIndex;

		public GrabRecipientInfo(WebDriver driver, int friendIndex) {
			super(driver);
			this.friendIndex = friendIndex;
		}

		protected Object runStep() throws Exception {
			driver.findElement(By.xpath("//a[@class='t_link']//span[text()='首页']")).click();
			Thread.sleep(1000);
			
			List<WebElement> friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			friendList.get(friendIndex).click();
			Thread.sleep(1000);
			
			String name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			String gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			RecipientInfo recipientInfo = new RecipientInfo();
			recipientInfo.setName(name);
			recipientInfo.setGender(parseGender(gender));
			recipientInfo.setHomePage(driver.getCurrentUrl());
			dao.add(recipientInfo);
			
			Thread.sleep(1000);
			
			return true;
		}

		private RecipientGender parseGender(String gender) {
			if("男".equals(gender)){
				return RecipientGender.MALE;
			}else if("女".equals(gender)){
				return RecipientGender.FEMALE;
			}else{
				return RecipientGender.ALL;
			}
		}
		
	}
	
	class SendMessageToOneRecipient extends Step {
		private IMessage message;
		private int friendIndex;

		public SendMessageToOneRecipient(WebDriver driver, int friendIndex, IMessage message) {
			super(driver);
			this.message = message;
			this.friendIndex = friendIndex;
		}

		protected Object runStep() throws Exception {
			driver.findElement(By.xpath("//a[@class='t_link']//span[text()='首页']")).click();
			Thread.sleep(1000);
			
			List<WebElement> friendList = driver.findElements(By.xpath("//div[@id='homeflist']//div[@class='vafcon']//a"));
			friendList.get(friendIndex).click();
			Thread.sleep(1000);
			
			String name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			String gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			RecipientInfo recipientInfo = new RecipientInfo();
			recipientInfo.setName(name);
			recipientInfo.setGender(parseGender(gender));
			recipientInfo.setHomePage(driver.getCurrentUrl());
			dao.add(recipientInfo);
			
			String friendlyMessage = buildFriendlyMessage(driver, message);
			
			driver.findElement(By.xpath("//a[text()='发短消息']")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//div[@id='content_div']/textarea"))
					.sendKeys(friendlyMessage);
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//input[@id='sendbtn']")).click();
			Thread.sleep(2000);
			
			
			return true;
		}

		private RecipientGender parseGender(String gender) {
			if("男".equals(gender)){
				return RecipientGender.MALE;
			}else if("女".equals(gender)){
				return RecipientGender.FEMALE;
			}else{
				return RecipientGender.ALL;
			}
		}

		private String buildFriendlyMessage(WebDriver driver, IMessage message) {
			String name = driver.findElement(By.xpath("//div[@id='divstate0']//strong")).getText();
			String gender = driver.findElement(By.xpath("//div[@class='sy_pr2']//tr[1]//span")).getText().trim();
			
			String callStr = "";
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
	
	class TaskReport extends Step {
		private int recipientNum;

		public TaskReport(WebDriver driver, int recipientNum) {
			super(driver);
			this.recipientNum = recipientNum;
		}

		@Override
		protected Object runStep() throws Exception {
			WebElement successMark = driver.findElement(By
					.xpath("//div[@class='bqd_on' and text()='发件箱']"));
			if (successMark != null && successMark.isDisplayed()) {
				System.out.println("Message sent out to " + recipientNum
						+ " people");
			}
			return null;
		}

	}

	
}


