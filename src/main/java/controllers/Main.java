package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class Main {

	/**
	 * 
	 * @param args This should contain only one string which is the query
	 */
	public static void main(String[] args) {
		// checking about the arguments 
		if (args == null || args.length != 1) {
			System.out
					.println("ERROR: Invalid arguments! Please write only one string");
			return;
		}
		try {
			JSONHandler handler = new JSONHandler();
			JSONArray jsonArray = handler.loadJsonArray(args[0]);
			if (jsonArray.isEmpty()) {
				System.out.println("No data for your query");
			} else {
				handler.buildCSVFile(jsonArray);
			}
		} catch (MalformedURLException e) {
			System.err.println("Error: Error in RESTful API ");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Config file does not exist");
		} catch (IOException e) {
			System.err.println("ERROR IO: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Error in parsing the REST response. Error: "
					+ e.getMessage());
		}
	}

}
