package org.coronaVirusTracker;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.coronaVirusTracker.controllers.HomeController;
import org.coronaVirusTracker.services.CoronaVirusDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {HomeController.class})
class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CoronaVirusDataService coronaVirusDataService;
	
	@Test
	void get_data_should_sucess() throws Exception {
		mockMvc.perform(get("/status")).andExpect(status().isOk()).andDo(print());
	}

}
