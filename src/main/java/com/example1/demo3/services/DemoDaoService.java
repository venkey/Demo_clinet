package com.example1.demo3.services;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoDaoService {
	public String jsonObjTocsv(String jsonObj) throws IOException, JSONException {
		return jsonToCvConversion(jsonObj);
	}

	public String jsonUrlTocsv(String jsonUrl) throws IOException, JSONException {
		RestTemplate restTemplate = new RestTemplate();
		String jsonObj = restTemplate.getForObject(jsonUrl, String.class);
		return jsonToCvConversion(jsonObj);
	}
	
	private String jsonToCvConversion(String jsonObj) throws IOException, JSONException {
		JSONObject output;
		String status = null;
		char firstChar = jsonObj.trim().charAt(0);
		String home = System.getProperty("user.home")+"/downloads";  
		System.out.println(home);
		try {
			if (String.valueOf(firstChar).equalsIgnoreCase("{")) {
				String jsonarrayValue = jsonObj.substring(1, jsonObj.indexOf(":")).substring(0).replace("{", "").trim();
				String arrayName = jsonObj.replaceFirst(jsonarrayValue, "data");
				output = new JSONObject(arrayName);
				JSONArray docs = output.getJSONArray("data");
				File file = new File(home+"/jsonTocv.csv");
				String csv = CDL.toString(docs);
				FileUtils.writeStringToFile(file, csv);
				status = "success";
				return status;
			} else {
				status = " its not a json array";
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
