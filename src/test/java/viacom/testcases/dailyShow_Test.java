package viacom.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import viacom.pages.HomePage;
import viacom.utils.BaseClass;

@Listeners(viacom.utils.Listeners.class)

public class dailyShow_Test extends BaseClass {

	@Test (priority=0)
	public void verifyDailyShow() {
		HomePage home = new HomePage();
		Actions action = new Actions(driver);
		action.moveToElement(home.showsMenu).perform();
		home.dailyShowOptionLink.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(home.showLogo));
		String message = "Required title not shown";
		String actual = home.showLogo.getText();
		Assert.assertTrue(actual.startsWith("THE DAILY SHOW"), message);
		Assert.assertTrue(actual.endsWith("WITH TREVOR NOAH"), message);
	}	
		
	@Test (priority=1)
	public void getFooterLinksText() {
		HomePage home = new HomePage();
		System.out.println("********There are total footer links: " + home.footerLinks.size() + ", and they are shown below ********");
		for (WebElement we : home.footerLinks) {
			System.out.println(we.getText());
		}

		
	}
}
