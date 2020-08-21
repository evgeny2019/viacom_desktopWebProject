/**
 * 
 */
package viacom.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import viacom.utils.BaseClass;

/**
 * @author Evgeny
 *
 */
public class HomePage extends BaseClass {

	@FindBy (css = "ul.main_nav>li:nth-of-type(1)")
	public WebElement showsMenu;
	
	@FindBy (xpath = "(//a[contains(text(),'Trevor Noah')])[2]")
	public WebElement dailyShowOptionLink;
	
	@FindBy (css = "a.show_logo")
	public WebElement showLogo;
	
	@FindBy (css = ".s_layouts_footerList_1 a")
	public List<WebElement> footerLinks;
	
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
}
