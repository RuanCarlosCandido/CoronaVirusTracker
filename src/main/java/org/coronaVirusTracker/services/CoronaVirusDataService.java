package org.coronaVirusTracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVRecord;
import org.coronaVirusTracker.models.LocationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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

//	@PostConstruct
	@Scheduled(cron = SCHEDULING_INTERVAL_EVERY_MINUTE)
	public Map<String, Object> fecthVirusData() throws IOException {
		LOGGER.info("Request data from url: " + VIRUS_DATA_URL);

		List<LocationStatus> newStats = new ArrayList<LocationStatus>();

		String response = exchange();

		Iterable<CSVRecord> records = convertToCSVList(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("locationStats", buildStatusList(newStats, records));
		result.put("mostCases", calculatePlacesWithMoreCases(newStats));
		result.put("lessCases", calculatePlacesWithLessCases(newStats));
		//TODO create methods to return the top 10 most cases places and top 10 less cases

		LOGGER.info("response: " + newStats);

		return result;
	}


	//TODO returns a list of all places with minimum cases
	private LocationStatus calculatePlacesWithLessCases(List<LocationStatus> newStats) {
		return newStats.stream().min((locStatTurtle, locStatHare) -> locStatTurtle.getLatestTotalCases()
				.compareTo(locStatHare.getLatestTotalCases())).get();
	}

	private LocationStatus calculatePlacesWithMoreCases(List<LocationStatus> newStats) {
		return newStats.stream().max((locStatTurtle, locStatHare) -> locStatTurtle.getLatestTotalCases()
				.compareTo(locStatHare.getLatestTotalCases())).get();
	}

	/**
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private Iterable<CSVRecord> convertToCSVList(String response) throws IOException {
		StringReader csvReader = new StringReader(response);
		Iterable<CSVRecord> records = Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(csvReader);
		return records;
	}

	/**
	 * @return
	 * @throws RestClientException
	 */
	private String exchange() throws RestClientException {
		RestTemplate restTemplate = new RestTemplate();

		String response = restTemplate.getForObject(VIRUS_DATA_URL, String.class);
		return response;
	}

	/**
	 * @param newStats
	 * @param records
	 * @return
	 * @throws NumberFormatException
	 */
	private List<LocationStatus> buildStatusList(List<LocationStatus> newStats, Iterable<CSVRecord> records)
			throws NumberFormatException {
		for (CSVRecord record : records) {
			LocationStatus locSta = new LocationStatus();
			locSta.setState(record.get(KEY_STATE));
			locSta.setCountry(record.get(KEY_COUNTRY));
			locSta.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
			newStats.add(locSta);
		}
		return newStats;
	}

	public Map<String, Object> getAllStats() throws IOException {
		return fecthVirusData();
	}
}
