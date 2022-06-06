package org.coronaVirusTracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVRecord;
import org.coronaVirusTracker.models.LocationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoronaVirusDataService {

	private static final String KEY_COUNTRY = "Country/Region";

	private static final String KEY_STATE = "Province/State";

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// schedule configuration (ss mm hh dd mm yy), cron = "30 * * * * * is
	// configured to run every minute
	private static final String SCHEDULING_INTERVAL_EVERY_MINUTE = "30 * * * * *";

	@Value("${virus.data.url}")
	private String VIRUS_DATA_URL;

	@PostConstruct
	@Scheduled(cron = SCHEDULING_INTERVAL_EVERY_MINUTE)
	public List<LocationStatus> fecthVirusData() throws IOException {
		LOGGER.info("Request data from url: " + VIRUS_DATA_URL);

		List<LocationStatus> newStats = new ArrayList<LocationStatus>();
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(VIRUS_DATA_URL, String.class);
		StringReader csvReader = new StringReader(response);
		Iterable<CSVRecord> records = Builder
				.create()
				.setHeader()
				.setSkipHeaderRecord(true)
				.build()
				.parse(csvReader);
		buildStatusList(newStats, records);
		LOGGER.info("response: " + newStats);
		return newStats;
	}

	/**
	 * @param newStats
	 * @param records
	 * @throws NumberFormatException
	 */
	private void buildStatusList(List<LocationStatus> newStats, Iterable<CSVRecord> records)
			throws NumberFormatException {
		for (CSVRecord record : records) {
			LocationStatus locSta = new LocationStatus();
			locSta.setState(record.get(KEY_STATE));
			locSta.setCountry(record.get(KEY_COUNTRY));
			locSta.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
			newStats.add(locSta);
		}
	}

	public List<LocationStatus> getAllStats() throws IOException {
		return fecthVirusData();
	}
}
