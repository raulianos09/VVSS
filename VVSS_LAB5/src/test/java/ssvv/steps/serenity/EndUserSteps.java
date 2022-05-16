package ssvv.steps.serenity;

import net.thucydides.core.annotations.Step;
import ssvv.pages.HomePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EndUserSteps {

    HomePage emagHomePage;

    @Step
    public void search(String objectName) {
        emagHomePage.searchForObject(objectName);
    }

    @Step
    public void is_the_home_page() {
        emagHomePage.open();
    }

    @Step
    public void should_see_object(String description) {
        if (emagHomePage.getItems().size() == 0)
        {
            System.out.println(emagHomePage.getError());
            assertTrue(emagHomePage.getError().contains("0 rezultate pentru: "));
        }
            else{
            assertThat(emagHomePage.getItems(), hasItem(containsString(description)));
        }
    }

    @Step
    public void should_see_number(String number){
        assertEquals(emagHomePage.getAppFieldText(), number);
    }

    @Step
    public void getApp(String nrTelefon) {
        emagHomePage.getApp(nrTelefon);
    }
}