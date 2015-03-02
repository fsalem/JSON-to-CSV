package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import au.com.bytecode.opencsv.CSVWriter;

/**
 *
 * This class is for retrieve JSON from URL then save some properties from this JSON to CSV file
 * @author farouq
 * 
 *
 */
public class JSONHandler {

	private Properties propertiesFile = new Properties();

	public JSONHandler() throws FileNotFoundException, IOException {
		load();
	}
	/**
	 * Load properties file which contains the url of the REST service, CSV header, CSV file name
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void load() throws FileNotFoundException, IOException {
		propertiesFile.load(new FileInputStream(
				"src/main/resources/config.properties"));
	}

	/**
	 * This method is to load the JSONArray from the REST URL
	 * @param query
	 * @return JSONArray
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public JSONArray loadJsonArray(String query) throws MalformedURLException,
			IOException, ParseException {
		String jsonObject = IOUtils.toString(new URL(propertiesFile.get(
				"api.url").toString()
				+ query).openStream());
		return (JSONArray) JSONValue.parseWithException(jsonObject);
	}

	/**
	 * Convert the JSONArray to CSV file
	 * @param jsonArray
	 * @throws IOException
	 */
	public void buildCSVFile(JSONArray jsonArray) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(propertiesFile.get(
				"csv.name").toString()));
		writer.writeNext(propertiesFile.get("csv.header").toString().split(","));
		for (int i = 0; i < jsonArray.size(); i++) {
			String[] array = new String[5];
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);

			array[0] = ((Long) jsonObject.get("_id")).toString();
			array[1] = (String) jsonObject.get("name");
			array[2] = (String) jsonObject.get("type");
			array[3] = ((Double) ((JSONObject) jsonObject.get("geo_position"))
					.get("latitude")).toString();
			array[4] = ((Double) ((JSONObject) jsonObject.get("geo_position"))
					.get("longitude")).toString();
			writer.writeNext(array);
		}
		writer.flush();
		writer.close();
	}
}
