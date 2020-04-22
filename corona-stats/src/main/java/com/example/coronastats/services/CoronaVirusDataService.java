package com.example.coronastats.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.coronastats.models.LocationStats;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> currentCoronaStats = new ArrayList<>();

    public List<LocationStats> getcurrentCoronaStats() {
        return currentCoronaStats;
    }


    @PostConstruct         	          // Enables method to run as soon as the class (@Service here) constructed/created
    @Scheduled(cron = "* * 1 * * *")  // schedule = "second minute hour day month year"), keep running the method as scheduled.. Every 1st minute of hour
    public void fetchVirusData() throws IOException, InterruptedException {
    	
        List<LocationStats> currentStats = new ArrayList<>();
        
        //------ Very conventional method of extracting data using Http Request.
        
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();								  // Creating Http request to the data source for extraction.
        
        HttpClient client = HttpClient.newHttpClient();   // Http Client to make Http request

        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());  //AN Http response in .csv 
        
        StringReader csvAsString = new StringReader(httpResponse.body());  // A String reader for the .csv
        
        // A CSV record parsed from a CSV file.
        
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvAsString); // returns all rows(as CSVRecord) except Header
        
        for (CSVRecord record : records) {
        	
            LocationStats locationStat = new LocationStats();
            
            locationStat.setState(record.get("Province/State"));  // Value mapped with corresponding column_name ("Province/State" here)
            locationStat.setCountry(record.get("Country/Region"));
            
            int casesOnLatestReport = Integer.parseInt(record.get(record.size() - 1));
            int casesOnPreviousDayReport = Integer.parseInt(record.get(record.size() - 2));
            
            locationStat.setLatestTotalCases(casesOnLatestReport);
            locationStat.setDiffFromPrevDay(casesOnLatestReport - casesOnPreviousDayReport);
            currentStats.add(locationStat);
        }
        this.currentCoronaStats = currentStats;
    }

}
