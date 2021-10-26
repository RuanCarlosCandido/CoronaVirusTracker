package org.coronaVirusTracker.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoronaVirusDataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void fecthVirusData() {

		RestTemplate restTemplate = new RestTemplate();

		String response = restTemplate.getForObject(VIRUS_DATA_URL, String.class);

		System.out.println(response);
	}
}
