package org.coronaVirusTracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.coronaVirusTracker.models.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoronaVirusDataService {

	//BAD for CONCURRENCY
	private static List<LocationStats> allStats = new ArrayList<LocationStats>();

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	@SuppressWarnings("deprecation")
	@PostConstruct
	@Scheduled(cron = "* * * * * *") //schedule configuration (ss mm hh dd mm yy)
	public void fecthVirusData() throws IOException {

		List<LocationStats> newStats = new ArrayList<LocationStats>();
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(VIRUS_DATA_URL, String.class);
		StringReader csvReader = new StringReader(response);
		Iterable<CSVRecord> records = null;
		records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);

		for(CSVRecord record : records) {
			LocationStats locSta = new LocationStats();
			locSta.setState(record.get("Province/State"));
			locSta.setCountry(record.get("Country/Region"));
			locSta.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
//			System.out.println(locSta);
			newStats.add(locSta);
		}
		allStats = newStats;
	}

	public static List<LocationStats> getAllStats() {
		return allStats;
	}
}
