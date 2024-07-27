package apiAutomation.Chapter02;

import apiAutomation.utils.BaseTest;
import apiAutomation.utils.ResourceHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SecondChapterTests extends BaseTest {

    // Abstracting the code for more readability and maintainability
    @Test
    public void abstractingTheRequestForMaintenanceAndReadability() {
        Response response = ResourceHelper.get(propertiesReader.getEndPointUrl("get_animals"));
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}