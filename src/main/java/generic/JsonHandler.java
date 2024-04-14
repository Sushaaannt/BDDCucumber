package generic;

import java.io.File;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

	public static JsonNode rootPage = null;
	public static String page = null;
	public static String locator = null;
	public static String value = null;
	public static String type = null;
	public static boolean flag, flagParseJsonData, flagGetJsonNode;
	public static JsonHandler instance = null;
	public static ObjectMapper mapper = null ;

	public void setApplicationPage(String page) {
		JsonHandler.page = page;
	}

	public String getApplicationPage() {
		return page;
	}

	public static JsonHandler getInstance() {
		flag = false;
		flagParseJsonData = false;
		flagGetJsonNode = false;
		if (instance == null)
			instance = new JsonHandler();
		return instance;
	}
	
	public static JsonNode loadInJsonformat() throws Throwable {
		try {
			System.out.println("user switches to page : pages/"+page+".json" );
			mapper = new ObjectMapper();
			File file = new File("src/main/resources/pages/"+page+".json");
			rootPage = mapper.readTree(file);
			return rootPage;
			
		} catch (Exception e) {
			throw new Exception("Error :  " + e.getMessage());
		}
	}

	public String getJsonData(String attribute, String identifier) throws Exception {
		try {
			JsonNode rootObject = rootPage.path(page);
			identifier = identifier.replace(" ", "");

			if (rootObject.isMissingNode()) {
				throw new Exception("page node is " + page + "Missing from" + page + ".json");
			} else {
				JsonNode crrWebElementObject = rootObject.path(identifier);

				if (crrWebElementObject.isMissingNode()) {
					throw new Exception("Web Element node is " + page + "Missing from" + page + ".json");
				} else {
					switch (attribute.toLowerCase()) {

					case "locator":
						locator = crrWebElementObject.path("locator").asText();
						return locator;

					case "value":
						value = crrWebElementObject.path("value").asText();
						return value;

					case "type":
						type = crrWebElementObject.path("type").asText();
						return locator;

					}
				}
			}

		} catch (Exception e) {
			throw new Exception("Error : Incorrect attribute Passed !  Attribute name : " + attribute);
		}
		return null;

	}

}
