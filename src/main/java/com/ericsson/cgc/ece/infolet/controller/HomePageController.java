package com.ericsson.cgc.ece.infolet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomePageController {

	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);


	@RequestMapping(value = "/ice_cream_machine_map", method = RequestMethod.GET)
	public String mapView(Model model) {
		
		return "ice_cream_machine_map";
	}

}
