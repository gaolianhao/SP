package com.netmessenger.agent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class AgentKaixin001 extends Agent {
	
	public AgentKaixin001(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender) {
		super(recipientAge, recipientJob, recipientGender);
	}

	@Override
	public void deliverMessage(IMessage message) {
        WebDriver driver = new FirefoxDriver();
        try {
        //step1
        login(driver);
        
        //step2
        gotoMessageSendingPage(driver);
        
        //step3
        List<WebElement> friendList = sendMessage(message, driver);
        
        //step4
        taskReport(driver, friendList);
        
        driver.quit();
       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void taskReport(WebDriver driver, List<WebElement> friendList) {
		WebElement successMark = driver.findElement(By.xpath("//div[@class='bqd_on' and text()='发件箱']"));
        if(successMark != null && successMark.isDisplayed()){
        	System.out.println("Message sent out to " + friendList.size() + " people");
        }
	}

	private List<WebElement> sendMessage(IMessage message, WebDriver driver)
			throws InterruptedException {
		driver.findElement(By.xpath("//div[@id='supersuggestxx_sh']//img")).click();
        Thread.sleep(1000);
        List<WebElement> friendList = driver.findElements(By.xpath("//ul[@id='supersuggestviewall_friends_list']//input"));
        for(int i=0;i<friendList.size();i++){
        	friendList.get(i).click();
        }
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@id='supersuggestviewdiv_btn_confirm']//input")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@id='content_div']/textarea")).sendKeys(message.getContent());
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='sendbtn']")).click();
        Thread.sleep(2000);
		return friendList;
	}

	private void gotoMessageSendingPage(WebDriver driver)
			throws InterruptedException {
		String homePage = driver.getCurrentUrl();
        driver.findElement(By.xpath("//a[text()='发短消息']")).click();
        Thread.sleep(1000);
	}

	private void login(WebDriver driver) throws InterruptedException {
		driver.get("http://www.kaixin001.com/");
        WebElement username = driver.findElement(By.name("email"));
        username.clear();
        username.sendKeys("liano_x@sohu.com");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("19811011");
        WebElement submitBt = driver.findElement(By.id("btn_dl"));
        submitBt.click();
        Thread.sleep(3000);
	}

}

class Login extends Step{


	public Object runStep(WebDriver driver) {
		// TODO Auto-generated method stub
		return null;
	}
	
}