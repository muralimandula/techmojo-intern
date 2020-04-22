package com.example.coronastats.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coronastats.models.LocationStats;
import com.example.coronastats.services.CoronaVirusDataService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
    	
        List<LocationStats> allStats = coronaVirusDataService.getcurrentCoronaStats(); // Current available stats from Data Service
        
        int totalReportedCases = allStats.stream()
        		.mapToInt(eachLocationStat -> eachLocationStat.getLatestTotalCases())  // getting current cases of each location
        		.sum();
        
        int totalNewCases = allStats.stream()
        		.mapToInt(eachLocationStat -> eachLocationStat.getDiffFromPrevDay())  // getting newly added cases of each location
        		.sum();
        
        model.addAttribute("locationStats", allStats);  // Passing List of location stats (all locations) as a model
        
        model.addAttribute("worldWideCases", totalReportedCases);
        
        model.addAttribute("worldWideNewCases", totalNewCases);

        return "home";
    }
}
