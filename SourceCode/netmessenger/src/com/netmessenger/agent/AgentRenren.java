package com.netmessenger.agent;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class AgentRenren extends Agent {


	public AgentRenren(String name) {
		super(name);
	}

	@Override
	public void deliverMessage(IMessage message) {
		WebDriver driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			List<WebElement> friendList = driver.findElements(By.xpath("//ul[@class='people-list']//span[@class='headpichold']/a"));
			int recipientNum = 0;
			int friendListSize = friendList.size();
			for(int i=0; i< friendListSize; i++){
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
	
	
	class Login extends Step{
		public Login(WebDriver driver){
			super(driver);
		}
		protected Object runStep() throws Exception{
			driver.get("http://www.renren.com/");
	        WebElement username = driver.findElement(By.id("email"));
	        username.clear();
	        username.sendKeys("gaolianhao@sohu.com");
	        WebElement password = driver.findElement(By.id("password"));
	        password.sendKeys("19811011");
	        WebElement submitBt = driver.findElement(By.id("login"));
	        submitBt.click();
	        Thread.sleep(2000);
	        return null;
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
			driver.findElement(By.xpath("//div[@class='menu-title']/a[text()='首页']")).click();
			Thread.sleep(1000);
			
			List<WebElement> friendList = driver.findElements(By.xpath("//ul[@class='people-list']//span[@class='headpichold']/a"));
			friendList.get(friendIndex).click();
			Thread.sleep(1000);
			
			String friendlyMessage = buildFriendlyMessage(driver, message);
			
			driver.findElement(By.id("proTabFeedId_")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//div[@class='m-editor-textarea']/textarea[@id='cmtbody']"))
					.sendKeys(friendlyMessage);
			Thread.sleep(1000);
			
			driver.findElement(By.id("whisper")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//input[@id='commentPostBtn']")).click();
			Thread.sleep(1000);
			
			
			return true;
		}

		private String buildFriendlyMessage(WebDriver driver, IMessage message) throws Exception{
			String gender = "";
			String name = "";
			try{
				WebElement genderEle = driver.findElement(By.xpath("//ul[@class='user-info clearfix']/li[@class='gender']/span"));
				gender = genderEle.getText().trim();
				
			}catch(Exception e){
				driver.findElement(By.id("proTabInfoId_")).click();
				Thread.sleep(1000);
				try{
					gender = driver.findElement(By.xpath("//div[@id='basicInfo']//dl[@class='info']//dd[1]")).getText().trim();
				}catch(Exception e2)
				{
					
				}

			}
			
			name = driver.findElement(By.xpath("//h1[contains(@class,'username')]")).getText().trim();
			
			String callStr = "";
			if("男".equals(gender)){
				callStr = name + " 帅哥，";
			}else if("女".equals(gender)){
				callStr = name + " 美女，";
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

	@Override
	public void findAndSaveRecipientInfo() {
		// TODO Auto-generated method stub
		
	}

	}

