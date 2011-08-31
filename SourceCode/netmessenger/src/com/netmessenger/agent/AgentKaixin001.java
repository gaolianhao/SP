package com.netmessenger.agent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IMessage;

public class AgentKaixin001 extends Agent {


	public AgentKaixin001(String name) {
		super(name);
	}

	@Override
	public void deliverMessage(IMessage message) {
		WebDriver driver = new FirefoxDriver();
		try {
			// step1
			(new Login(driver)).runStep();

			// step2
			(new GotoSendMessagePage(driver)).runStep();

			// step3
			int recipientNum = (Integer) (new SendMessage(driver, message))
					.runStep();

			// step4
			(new TaskReport(driver, recipientNum)).runStep();

			driver.quit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class Login extends Step {
		public Login(WebDriver driver) {
			super(driver);
		}

		protected Object runStep() throws Exception {
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

	class GotoSendMessagePage extends Step {
		public GotoSendMessagePage(WebDriver driver) {
			super(driver);
		}

		protected Object runStep() throws Exception {
			String homePage = driver.getCurrentUrl();
			driver.findElement(By.xpath("//a[text()='发短消息']")).click();
			Thread.sleep(1000);
			return null;
		}
	}

	class SendMessage extends Step {
		private IMessage message;

		public SendMessage(WebDriver driver, IMessage message) {
			super(driver);
			this.message = message;
		}

		protected Object runStep() throws Exception {
			driver.findElement(By.xpath("//div[@id='supersuggestxx_sh']//img"))
					.click();
			Thread.sleep(1000);
			List<WebElement> friendList = driver
					.findElements(By
							.xpath("//ul[@id='supersuggestviewall_friends_list']//input"));
			for (int i = 0; i < friendList.size(); i++) {
				friendList.get(i).click();
			}
			Thread.sleep(1000);
			driver.findElement(
					By.xpath("//div[@id='supersuggestviewdiv_btn_confirm']//input"))
					.click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@id='content_div']/textarea"))
					.sendKeys(message.getContent());
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='send_separate']"))
					.click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@id='sendbtn']")).click();
			Thread.sleep(2000);
			return friendList.size();
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
		
		
	}
}