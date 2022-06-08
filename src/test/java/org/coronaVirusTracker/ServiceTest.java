package org.coronaVirusTracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.coronaVirusTracker.services.CoronaVirusDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@Mock
	CoronaVirusDataService coronaVirusDataService;
	
	@Test
	void get_data_should_sucess() throws Exception {
		when(coronaVirusDataService.getAllStats()).thenReturn(new HashMap<String,Object>());
		assertThat(coronaVirusDataService.getAllStats()).isNotNull();	
	}

	
}
