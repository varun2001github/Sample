//$Id$
package com.varun;

import java.time.Duration;
import java.util.List;

public class selenium {
	public static void main(String[] args) {

        // Set the path to the ChromeDriver executable

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\2272468\\eclipse-workspace1\\hackathon\\drivers\\chromedriver.exe");



        WebDriver driver = new ChromeDriver();



        driver.get("https://www.coursera.org");

        WebElement searchInput = driver.findElement(By.xpath("//input[@id='search-bar']"));

        searchInput.sendKeys("web development");



        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(@class,'ais-InfiniteHits-item')]")));

        WebElement beginnerFilter = driver.findElement(By.xpath("//input[@id='difficulty-beginner']"));

        beginnerFilter.click();

        WebElement languageFilter = driver.findElement(By.xpath("//input[@id='language-English']"));

        languageFilter.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(@class,'ais-InfiniteHits-item')]")));

        List<WebElement> courses = driver.findElements(By.xpath("//li[contains(@class,'ais-InfiniteHits-item')]"));

        for (int i = 0; i < 2 && i < courses.size(); i++) {

            WebElement course = courses.get(i);

            String name = course.findElement(By.xpath(".//h2")).getText();

            String learningHours = course.findElement(By.xpath(".//span[contains(text(),'hours')]")).getText();

            String rating = course.findElement(By.xpath(".//span[contains(@class,'ratings-text')]")).getText();



            System.out.println("Course Name: " + name);

            System.out.println("Total Learning Hours: " + learningHours);

            System.out.println("Rating: " + rating);

            System.out.println();

        }

        driver.navigate().back();

        WebElement languageLearningLink = driver.findElement(By.xpath("//a[text()='Language Learning']"));

        languageLearningLink.click();

        List<WebElement> languages = driver.findElements(By.xpath("//div[@data-e2e='language-filter']//ul/li"));

        for (WebElement language : languages) {

            String name = language.findElement(By.xpath(".//label")).getText();

            String count = language.findElement(By.xpath(".//span")).getText();

            System.out.println("Language: " + name);

            System.out.println("Count: " + count);

            System.out.println();

        }

        driver.navigate().to("https://www.coursera.org/for-enterprise/courses-for-campus");

        WebElement emailInput = driver.findElement(By.xpath("//input[@type='email']"));

        emailInput.sendKeys("invalid_email");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-component='validation-error-message']")));

        WebElement errorMessage = driver.findElement(By.xpath("//div[@data-component='validation-error-message']"));

        String errorMessageText = errorMessage.getText();

        System.out.println("Error Message: " + errorMessageText);



          driver.quit();

        }
}
