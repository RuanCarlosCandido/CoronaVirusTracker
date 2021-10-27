package org.coronaVirusTracker.controllers;

import org.coronaVirusTracker.services.CoronaVirusDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller //NOT a RESTCONTROLLER(Returning a REST response converted in a JSON response)
public class HomeController {

	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		System.out.println(CoronaVirusDataService.getAllStats());
		model.addAttribute("locationStats",CoronaVirusDataService.getAllStats());
		return "home";
	}
	
}
