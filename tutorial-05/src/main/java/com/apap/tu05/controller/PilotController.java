package com.apap.tu05.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tu05.model.PilotModel;
import com.apap.tu05.service.PilotService;

/**
 * PilotController
 * @author deniprayoga
 *
 */
@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	public PilotController(PilotService pilotService) {
		this.pilotService = pilotService;
	}

	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view/{licenseNumber}")
	private String viewPilotByLicenseNumber(@PathVariable Optional<String> licenseNumber, Model model) {
		if(!licenseNumber.isPresent()) {
			model.addAttribute("licenseNumber", "licenseNumber not found");
			return "error";
		} else {
			PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			model.addAttribute("pilot", pilotModel);
			return "view-pilot";
		}
	}
}
