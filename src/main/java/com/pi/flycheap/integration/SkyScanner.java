package com.pi.flycheap.integration;

import com.pi.flycheap.integration.model.request.FlightRequest;
import com.pi.flycheap.integration.model.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkyScanner {

    private final RestTemplate jsonRestTemplate;

    public List<FlightResponse> getFlights(FlightRequest flightRequest){
        return new ArrayList<>();
    }
}
