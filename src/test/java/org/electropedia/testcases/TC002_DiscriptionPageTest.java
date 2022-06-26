package org.electropedia.testcases;

import org.electropedia.base.TestBase;
import org.electropedia.libraries.DiscriptionPage;
import org.electropedia.libraries.VocabularyPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC002_DiscriptionPageTest extends TestBase{

	VocabularyPage vocabularyPage;
	DiscriptionPage discriptionPage;
	
	@BeforeClass
	public void initiation() {
		setUp();
		vocabularyPage = new VocabularyPage();
		discriptionPage = vocabularyPage.discriptionPage();
	}

	@Test
	public void checkTblTitle() {
		boolean checkTblTitle = discriptionPage.checkTblTitle();
		Assert.assertTrue(checkTblTitle);
	}
	
	@Test
	public void saveIEVRefDataIntoTable() {
		discriptionPage.getIevRefDataFromTable();
	}
}
