package org.electropedia.libraries;

import org.electropedia.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VocabularyPage extends TestBase{

	@FindBy(xpath = "//a[text()='102-01-01']")
	private WebElement link;
	
	public VocabularyPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean checkTitle() {
		boolean isTrue = false;
		String title = driver.getTitle();
		if(title.contains("Vocabulary")) isTrue = true; 
		return isTrue;
	}
	
	public DiscriptionPage discriptionPage() {
		link.click();
		return new DiscriptionPage();
	}
}
