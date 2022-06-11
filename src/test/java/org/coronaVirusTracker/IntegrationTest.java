package org.coronaVirusTracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.coronaVirusTracker.services.CoronaVirusDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CoronaVirusDataService coronaVirusDataService;

	@Test
	void exception_handler_should_works() throws Exception {
		when(coronaVirusDataService.getAllStats()).thenThrow(new RuntimeException("Generic Exception"));

		mockMvc.perform(get("/status")).andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof java.lang.RuntimeException))
				.andExpect(result -> assertEquals("Generic Exception", result.getResolvedException().getMessage()))
				.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("date")))
				.andDo(print());
	}

}
