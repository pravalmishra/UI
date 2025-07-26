package com.qa.crm2.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.crm2.base.BaseTest;
import com.qa.crm2.constants.AppConstants;
import com.qa.crm2.utils.ExcelUtil;

public class ProductPageInfoTest extends BaseTest {
	
	    //AAA --> UTs Arrange Act Assert
		//TC  --> only one hard assertion or can have multiple soft assertions
		
		@BeforeClass
		public void infoPageSetup() {
			accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
		
		@DataProvider
		public Object[][] getProductSearchData() {
			return new Object[][] {
				{"macbook", "MacBook Pro"},
				{"imac", "iMac"},
				{"samsung", "Samsung SyncMaster 941BW"},
				{"samsung", "Samsung Galaxy Tab 10.1"},

			};
		}
		
		@Test(dataProvider = "getProductSearchData")
		public void productHeaderTest(String searchKey, String productName) {
			searchResultsPage = accPage.doSearch(searchKey);
			productInfoPage = searchResultsPage.selectProduct(productName);
			Assert.assertEquals(productInfoPage.getProductHeader(), productName);
		}
		
		
		@DataProvider
		public Object[][] getProductImagesData() {
			return new Object[][] {
				{"macbook", "MacBook Pro", 4},
				{"imac", "iMac", 3},
				{"samsung", "Samsung SyncMaster 941BW", 1},
				{"samsung", "Samsung Galaxy Tab 10.1", 7},

			};
		} 
		
		@Test(dataProvider = "getProductImagesData")
		public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
			searchResultsPage = accPage.doSearch(searchKey);
			productInfoPage = searchResultsPage.selectProduct(productName);
			Assert.assertEquals(productInfoPage.getProductImagesCount(), imagesCount);
		}   
		
		
//		@Test()
//		public void productHeaderTest() {
//			searchResultsPage = accPage.doSearch("macbook");
//			productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//			Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
//		}
//		
//		@Test()
//		public void productImagesCountTest() {
//			searchResultsPage = accPage.doSearch("macbook");
//			productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//			Assert.assertEquals(productInfoPage.getProductImagesCount(), 4);
//		}
		
		
		//******** Read data from excel file ***********//
		
		@DataProvider
		public Object[][] getProductImagesDataFromExcel() {
			return ExcelUtil.getTestData(AppConstants.PRODUCT_DATA_SHEET_NAME);
		}
		
		@Test(dataProvider = "getProductImagesDataFromExcel")
		public void productImagesCountTest(String searchKey, String productName, String imagesCount) {
			searchResultsPage = accPage.doSearch(searchKey);
			productInfoPage = searchResultsPage.selectProduct(productName);
			Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imagesCount));
			
		}
		
		
		
		@Test
		public void productInfoTest() {
			searchResultsPage = accPage.doSearch("macbook");
			productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
			Map<String, String> productActDetailsMap = productInfoPage.getProductDetailsMap();
			softAssert.assertEquals(productActDetailsMap.get("Brand"), "Apple");
			softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 18");
			softAssert.assertEquals(productActDetailsMap.get("Availability"), "Out Of Stock");
			softAssert.assertEquals(productActDetailsMap.get("productprice"), "$2,000.00");
			softAssert.assertEquals(productActDetailsMap.get("extaxprice"), "$2,000.00");
			softAssert.assertAll();
			
		}
		
		@Test
		public void addItemTest() {
			searchResultsPage = accPage.doSearch("macbook");
			productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
			productInfoPage.increaseItem();
			String accVal = productInfoPage.addToCart();
			System.out.println("Add to cart value :" +accVal);
			//Assert.assertEquals(accVal, "shopping cart!");
			//productInfoPage.clickOnShoppingCart();
		}
		
		
		
		

}
