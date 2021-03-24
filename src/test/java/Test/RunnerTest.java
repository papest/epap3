package Test;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@RunWith(Cucumber.class)
@CucumberOptions(
     features = "src/test/features",
     glue =  "test",
     tags ="@all",
     strict = false,
     snippets = CucumberOptions.SnippetType.UNDERSCORE

)

public class RunnerTest {
    static WebDriver driver;


    @Пусть("открыт ресурс авито")
    public void открытРесурсАвито() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver");
        // Драйвер для Windows
        //  System.setProperty("webdriver.chrome.driver","src/test/resources/webdriver/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.avito.ru/");
    }

    @ParameterType(".*")
    public CategorySelected categorySelected(String category){
        return CategorySelected.valueOf(category);
    }

    @И("в выпадающем списке категорий выбрана {categorySelected}")
    public void вВыпадающемСпискеКатегорийВыбранаОргтехника(CategorySelected option) {

        String id="category";
        Select category= new Select(driver.findElement(By.id(id)));
        category.selectByValue(option.getValue());
    }

    @И("в поле поиска введено значение  {word}")
    public void вПолеПоискаВведеноЗначение(String sendKeys) {
        String printer = "[type=\"text\"][data-marker=\"search-form/suggest\"]";
        WebElement search = driver.findElement(By.cssSelector(printer));
        search.sendKeys(sendKeys);
    }


    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        String searchForm = "[data-marker=\"search-form/region\"]";
        WebElement param = driver.findElement(By.cssSelector(searchForm));
        param.click();
    }

    @Тогда("в поле регион введено значение {word}")
    public void вПолеРегионВведеноЗначениеВладивосток(String sendKeys) throws InterruptedException {
        String city = "[placeholder=\"Город, регион или Россия\"]";
        WebElement params3 = driver.findElement(By.cssSelector(city));
        params3.sendKeys(sendKeys);
        params3.click();
        sleep(1000);
        String cssSelector="[data-marker=\"suggest(0)\"]";
        WebElement region=driver.findElement(By.cssSelector(cssSelector));
        region.click();

    }

    @И("нажата кнопка показать объявления")
    public void нажатаКнопкаПоказатьОбъявления() {
        String cssSelector = "[data-marker=\"popup-location/save-button\"]";
        driver.findElement(By.cssSelector(cssSelector)).click();

    }

    @Тогда("открылась страница по запросу {word}")
    public void открыласьСтраницаПоЗапросуПринтер(String result) {
        String cssSelector = "[data-marker=\"page-title/text\"]";
        String title= driver.findElement(By.cssSelector(cssSelector)).getText();
        if (title.contains("«" + result + "»")){
            System.out.println("Открылась страница по запросу "+result);
            }else
                {System.out.println(" Страница по запросу "+result+ "  не выведена!");
                }
        System.out.println(title);
    }

    @И("активирован чек бокс только с фотографией")
    public void активированЧекБоксТолькоСФотографией() {
        String xpath = "//*[text()=\"только с фото\"]";
        WebElement checkbox = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
        if (!checkbox.isSelected()) {
            checkbox.click();

        }
        String cssSelector="[data-marker=\"search-filters/submit-button\"]";
        WebElement saveButton=driver.findElement(By.cssSelector(cssSelector));
        saveButton.click();

    }
    @ParameterType(".*")
    public SortPrice sortPrice(String sort){
        return SortPrice.valueOf(sort);
}
    @И("в выпадающем списке сортировка выбрано значение {sortPrice}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначениеДороже(SortPrice sortPrice ) {
        String xpath = "//div[2]/select";
        Select price = new Select(driver.findElement(By.xpath(xpath)));
        price.selectByVisibleText(sortPrice.getValue());


    }



    @И("в консоль выведено значение названия и цены {int} первых товаров≠")
    public void вКонсольВыведеноЗначениеНазванияИЦеныПервыхТоваров(int arg0) {
        String cssSelector = "[data-marker=\"item\"]";
        List<WebElement> items = driver.findElements(By.cssSelector(cssSelector));
        String printerName = "h3[itemprop=\"name\"]";
        String printerPrice = "[data-marker=\"item-price\"]";
        int i = items.size();
        int k = arg0;
        if (k > i) {
            k = i;
        }

        for (int j = 0; j < k; j++) {
            System.out.println("Название принтера "
                    + items.get(j).findElement(By.cssSelector(printerName)).getText() +
                    " Цена:" + items.get(j).findElement(By.cssSelector(printerPrice)).getText());
        }
    }
}
