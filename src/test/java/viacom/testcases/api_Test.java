package viacom.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Listeners(viacom.utils.Listeners.class)

public class api_Test {

	@SuppressWarnings("unchecked")
	@Test
	public void viacomApi_GET () {
		
		baseURI = "https://neutron-api.viacom.tech/feeds/networkapp/intl/main/2.3";
		
	ValidatableResponse resp = RestAssured.given()
		.param ("key", "networkapp1.0")
		.param ("brand", "betplus")
		.param ("platform", "android")
		.param ("store", "google")
		.param ("region", "us")
		.param("version" ,"52.7.6")
		.param ("profile.firstInstalledAppVersion", "52.1.7")
		.param ("profile.isAuthorized", "false")
	.when()	
		.get("")
	.then()
		.statusCode(200)
		.body("data.appConfiguration.searchTypes", hasItems("series")) //the first way to validate that field contains word "series"
		.log().status()
		.log().body();

	// Parsing the response body to Java structure (JSONObject)
	JSONObject obj = new JSONObject(resp.extract().asString());
	
	//the second way to validate that field contains word "series"
	JSONArray searchTypes =  obj.getJSONObject("data")
			.getJSONObject("appConfiguration")
			.getJSONArray("searchTypes");	
	Assert.assertEquals(searchTypes.getString(0), "series");
	
	// Another way of parsing the response body to Java structure (Map)
	Map<Object, Object> parsedResponse = resp.extract().jsonPath().get("data.appConfiguration");
	
	//the third way to validate that field contains word "series"
	ArrayList<String> var = ((ArrayList<String>) parsedResponse.get("searchTypes"));
	Assert.assertTrue(var.contains("series"));
	
	// most thorough (and complicated) way of parsing the response into Java structure, where EACH field from heavily nested json object is parsed (got a template from one of the sites)
	Map <String,Object> totallyParsedResponse = jsonToMap(obj);
	
	ArrayList<String> var1 = (ArrayList<String>) ((Map<String,Object>) ((Map<String,Object>) totallyParsedResponse.get("data")).get("appConfiguration")).get("searchTypes");
	Assert.assertTrue(var1.contains("series"));
	}

	// helper methods for parsing the json object to java object
	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> parsedMap = new HashMap<String, Object>();
		if(json != JSONObject.NULL) {
			parsedMap = toMap(json);
		}
		return parsedMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);
			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}
}
