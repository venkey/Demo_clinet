package com.example1.demo3.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example1.demo3.services.DemoDaoService;

@RestController
public class CSVController {

	private static String SAMPLE_CSV_FILE_PATH = "D:/jsonTocv.csv";
	private static String SAMPLE_CSV_FILE_PATH2 = "D:/jsonTocv2.csv";

	@Autowired
	DemoDaoService demoDaoServices;

	@RequestMapping("/test")
	public String testJson() {
		return "hi";
	}

	@GetMapping("/jsonObj")
	public String testJson(@RequestBody String jsonObj) throws JSONException, IOException {
		return demoDaoServices.jsonObjTocsv(jsonObj);
	}

	@GetMapping("/jsonUrl")
	public String jsonTocv(@RequestParam String jsonUrl) throws JSONException, IOException {
		return demoDaoServices.jsonUrlTocsv(jsonUrl);
	}

	@GetMapping("/readCsv")
	public void readExcel() throws JSONException, IOException {
		System.out.println("started");
		try {
			Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
//                    .withHeader("Name", "Email", "Phone", "Country")
//                    .withIgnoreHeaderCase()
//                    .withTrim());
			System.out.println(csvParser);
			for (CSVRecord csvRecord : csvParser) {
				System.out.println(csvParser.getCurrentLineNumber());
				String ssn = csvRecord.get(0);
				String lastName = csvRecord.get(1);
				String gender = csvRecord.get(2);
				String university = csvRecord.get(3);
				String maidenName = csvRecord.get(4);
				String age = csvRecord.get(5);
				String username = csvRecord.get(6);
				System.out.println("Record No - " + csvRecord.getRecordNumber());
				System.out.println("---------------");
				System.out.println("ssn : " + ssn);
				System.out.println("lastName : " + lastName);
				System.out.println("gender : " + gender);
				System.out.println("university : " + university);
				System.out.println("maidenName : " + maidenName);
				System.out.println("age : " + age);
				System.out.println("username : " + username);
				System.out.println("---------------\n\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@GetMapping("/writeCsv")
	public void writeExcel() throws JSONException, IOException {
		System.out.println("starts here/...");
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_PATH2));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ssn", "lastName", "gender",
					"university", "maidenName", "age", "username"));
			System.out.println(csvPrinter);
			csvPrinter.printRecord("11", "Sundar Pichai", "CEO", "Google","test","test","test","test");
			csvPrinter.printRecord("12", "Satya Nadella", "CEO", "Microsoft","test","test","test","test");
			csvPrinter.printRecord("13", "Tim cook", "CEO", "Apple","test","test","test","test");
			csvPrinter.printRecord(Arrays.asList("14", "Mark Zuckerberg", "CEO", "Facebook","test","test","test","test"));
			csvPrinter.flush();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
