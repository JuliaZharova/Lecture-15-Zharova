import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.netty.handler.codec.http.multipart.DiskFileUpload.prefix;

public class Scenario {
    static WebDriver driver;
    static final Color RGB_COLOUR_LABEL_BACKGROUND_CHOICE_ROZETKA = Color.fromString("rgb(49, 163, 219)");
    static final Color RGB_COLOUR_LABEL_TEXT = Color.fromString("rgb(255, 255, 255)");
    static final Color RGB_COLOUR_LABEL_BACKGROUND_TOP_SALE = Color.fromString("rgb(255, 169, 0)");
    static final Color HEX_COLOUR_LABEL_BACKGROUND_DISCOUNT = Color.fromString("#f84147");
    static final Color HEX_COLOUR_LABEL_TEXT_DISCOUNT = Color.fromString("#fff");

    public static void propertyAndDriver () {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }
    public static void scenario1 () {
      propertyAndDriver();
        try {
            openRozetkaProductCategory();

            checkLabelChoiceRozetka();

            checkLabelTopSale();

            checkLabelDiscount();
        } finally {
            driver.quit();
        }
    }

    public static void openRozetkaProductCategory(){
        driver.get("https://rozetka.com.ua/");
        System.out.println(driver.getCurrentUrl());

        WebElement categoryName = driver.findElement(By.linkText("Бытовая техника"));
        System.out.println("Category name is " + categoryName);
        categoryName.click();
        System.out.println(driver.getCurrentUrl());

        WebElement productCategory = driver.findElement(By.xpath("//img[@alt=\"Уход за домом и одеждой\"]"));
        System.out.println("Product category is " + productCategory);
        System.out.println(driver.getCurrentUrl());
        productCategory.click();
    }

    public static void checkLabelChoiceRozetka() {
        WebElement labelChoiceRozetka = driver.findElement(By.xpath("//li[1]//div[2]/span"));
        System.out.println(labelChoiceRozetka.getText());
        Color backgroundColorLabelChoiceRozetka = Color.fromString(labelChoiceRozetka.getCssValue("background-color"));
        System.out.println("Label Choice Rozetka is blue: " + backgroundColorLabelChoiceRozetka.equals(RGB_COLOUR_LABEL_BACKGROUND_CHOICE_ROZETKA));
        Color labelText = Color.fromString(labelChoiceRozetka.getCssValue("color"));
        System.out.println("Label text is white: " + labelText.equals(RGB_COLOUR_LABEL_TEXT));
        System.out.println();
    }

    public static void checkLabelTopSale() {
        WebElement labelTopSale = driver.findElement(By.xpath("//li[2]//div[2]/span"));
        System.out.println(labelTopSale.getText());
        Color backgroundColorLabelTopSale = Color.fromString(labelTopSale.getCssValue("background-color"));
        System.out.println("Label Top Sale is orange: " + backgroundColorLabelTopSale.equals(RGB_COLOUR_LABEL_BACKGROUND_TOP_SALE));
        Color labelTextTopSale = Color.fromString(labelTopSale.getCssValue("color"));
        System.out.println("Label text is white: " + labelTextTopSale.equals(RGB_COLOUR_LABEL_TEXT));
        System.out.println();
    }

    public static void checkLabelDiscount() {
        WebElement labelDiscount = driver.findElement(By.xpath("//li[5]//div[2]/span"));
        System.out.println(labelDiscount.getText());
        Color backgroundColorLabelDiscount = Color.fromString(labelDiscount.getCssValue("background-color"));
        System.out.println("Label Top Sale is red: " + backgroundColorLabelDiscount.equals(HEX_COLOUR_LABEL_BACKGROUND_DISCOUNT));
        Color labelTextDiscount = Color.fromString(labelDiscount.getCssValue("color"));
        System.out.println("Label text is white: " + labelDiscount.equals(HEX_COLOUR_LABEL_TEXT_DISCOUNT));
    }

    public static void scenario2 () {
        propertyAndDriver();
        try {
            openRozetkaProductCategory();

            selectElement();

            Actions actionProvider = new Actions(driver);
            actionProvider.moveToElement(driver.findElement(By.xpath("//img[@src=\"https://content1.rozetka.com.ua/goods/images/big_tile/123733145.jpg\"]"))).build().perform();

            WebElement chosenProduct = driver.findElement(By.xpath("//img[@src=\"https://content1.rozetka.com.ua/goods/images/big_tile/123733145.jpg\"]"));
            System.out.println(chosenProduct.isEnabled());
            chosenProduct.click();
            System.out.println(driver.getCurrentUrl());

            WebElement buyButton = driver.findElement(By.xpath("//button[@class=\"buy-button button button_with_icon button_color_green button_size_large ng-star-inserted\"]"));
            buyButton.click();

            WebElement cart = driver.findElement(By.xpath("//single-modal-window/div[3]"));
            System.out.println(driver.getTitle());

            getScreenShot(driver);
        } finally {
           driver.quit();
        }
    }

    public static void selectElement() {
        WebElement selectedElement = driver.findElement(By.xpath("//select"));
        Select selectObject = new Select(selectedElement);
        List<WebElement> allSelectedOptions = selectObject.getAllSelectedOptions();
        List<WebElement> allAvailableOptions = selectObject.getOptions();
        selectObject.selectByIndex(5);
        String actionSelectedOption = selectObject.getFirstSelectedOption().getText();
        System.out.println(actionSelectedOption);
    }


        public static void getScreenShot(WebDriver driver){
            TakesScreenshot scr = ((TakesScreenshot) driver);
            byte[] screenshot = scr.getScreenshotAs(OutputType.BYTES);

            String fileName = "screenshot.png";
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                fileOutputStream.write(screenshot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
