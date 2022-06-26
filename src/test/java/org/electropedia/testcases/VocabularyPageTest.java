package org.electropedia.testcases;

import org.electropedia.base.TestBase;
import org.electropedia.libraries.DiscriptionPage;
import org.electropedia.libraries.VocabularyPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VocabularyPageTest extends TestBase{
	
	VocabularyPage vocabularyPage;
	DiscriptionPage checkLink;
	
	@BeforeClass
	public void initiation() {
		setUp();
		vocabularyPage = new VocabularyPage();
	}
	
	@Test(priority = 1)
	public void checkPageTitle() {
		boolean checkTitle = vocabularyPage.checkTitle();
		Assert.assertTrue(checkTitle);
	}
	
	@Test(priority = 2)
	public void checkLink() {
		checkLink = vocabularyPage.discriptionPage();
		boolean checkTblTitle = checkLink.checkTblTitle();
		Assert.assertTrue(checkTblTitle);
	}
	
}

