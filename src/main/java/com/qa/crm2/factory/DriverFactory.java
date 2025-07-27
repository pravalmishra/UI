package com.qa.crm2.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.crm2.exceptions.BrowserException;
import com.qa.crm2.errors.AppError;
import com.qa.crm2.exceptions.FrameworkException;
import com.qa.crm2.logger.Log;

public class DriverFactory {
	 
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		//String browserName = System.getProperty("browser"); /If pass from command line
		
		Log.info("Browser name is : " +browserName);
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		switch(browserName.toLowerCase().trim()) {
		
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote - grid execution:
				init_remoteDriver("chrome");
			} else {
				// run it on local:
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote - grid execution:
				init_remoteDriver("firefox");
			} else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
			
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote - grid execution:
				init_remoteDriver("edge");
			} else {
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		
		case "safari":
			driver = new SafariDriver();
			break;
			
			default:
				System.out.println("Please Pass the right browser..... " +browserName);
				Log.error("Please Pass the right browser..... : " +browserName);
				throw new BrowserException("No browser Found.... " +browserName);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	
	/**
	 * run tests on selenium grid
	 * 
	 * @param browserName
	 */
	private void init_remoteDriver(String browserName) {

		System.out.println("Running tests on Remote GRID on browser: " + browserName);

		try {
			switch (browserName.toLowerCase().trim()) {
			case "chrome":

				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;

			case "firefox":

				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;

			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;

			default:
				System.out.println("plz pass thr right supported browser on GRID....");
				break;
			}

		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public Properties initProp() {
		// envName = qa, stage , prod ,uat, dev
		// mvn clean install -Denv="qa"
        // ls -alt
		// mvn --version
		FileInputStream ip = null;
		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("Running tests on Env : " + envName);

		try {
			if (envName == null) {
				System.out.println("No env is given....hence running it on QA env...");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");

			} else {

				switch (envName.toLowerCase().trim()) {

				case "qa":

					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;

				case "dev":

					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;

				case "stage":

					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;

				case "uat":

					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;

				case "prod":

					ip = new FileInputStream("./src/test/resources/config/config.config.properties");
					break;

				default:
					System.out.println("Please pass the right env name..... " + envName);
					throw new FrameworkException(AppError.ENV_NAME_NOT_FOUND + " : " + envName);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}
	
	/**
	 * take screenshot
	 */
	
	public static String getScreenshot(String methodName) {
	    String screenshotDir = System.getProperty("user.dir") + "/screenshot/";
	    new File(screenshotDir).mkdirs(); // ensure directory exists

	    File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	    String path = screenshotDir + methodName + "_" + System.currentTimeMillis() + ".png";
	    File destination = new File(path);

	    try {
	        FileHandler.copy(srcFile, destination);
	    } catch (IOException e) {
	        System.err.println("Failed to save screenshot: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return path;
	}

	
	}
