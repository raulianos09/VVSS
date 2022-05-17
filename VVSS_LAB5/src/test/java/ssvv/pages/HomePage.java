package ssvv.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("https://www.emag.ro/")
public class HomePage extends PageObject {

    @FindBy(id = "searchboxTrigger")
    private WebElement searchBar;

    @FindBy(xpath = "//*[@id=\"my_wishlist\"]/span[2]")
    private WebElement favoriteBtn;

    @FindBy(name = "phone")
    private WebElement inputField;

    public void searchForObject(String objectName){
        waitForElementToLoad(searchBar);
        searchBar.clear();
        searchBar.sendKeys(objectName);
        searchBar.sendKeys(Keys.RETURN);

    }

    public void waitForElementToLoad(WebElement e){
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }

    public List<String> getItems() {
        List<WebElement> cards = getDriver().findElements(By.xpath("//*[@id=\"card_grid\"]//a"));
        return cards.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getError() {
        return getDriver().findElement(By.xpath("/html/body/div[3]/footer/div[1]/div/div/div[2]/div/form/div[1]/span")).getText();
    }

    public void getApp(String nrTelefon) {
        waitForElementToLoad(favoriteBtn);
        favoriteBtn.click();
       waitForElementToLoad(inputField);
       inputField.clear();
       inputField.sendKeys(nrTelefon + Keys.RETURN);
    }

    public String getAppFieldText(){
        waitForElementToLoad(inputField);
        return inputField.getAttribute("value");
    }
}
