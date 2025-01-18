package com.pi.flycheap.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.pi.flycheap.entity.Ticket;
import com.pi.flycheap.integration.model.request.DateObject;
import com.pi.flycheap.integration.model.request.FlightRequest;
import com.pi.flycheap.integration.model.request.LegDetailObject;
import com.pi.flycheap.integration.model.request.LegObject;
import com.pi.flycheap.integration.model.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkyScanner {

    private final RestTemplate jsonRestTemplate;

    @Value("${sky.scanner.base.url}")
    private String skyScannerBaseUrl;
    private final String cabinClass = "ECONOMY";
    private final String legDetailType = "entity";
    private final String dateType = "date";

    public List<FlightResponse> getFlights(Ticket ticket) throws RuntimeException{
        List<FlightResponse> flights = new ArrayList<>();
        FlightRequest request = createRequestBasedOnEntity(ticket);
        log.info("Flight request: {}", request);
        try {
            JsonNode response = jsonRestTemplate.postForObject(
                    skyScannerBaseUrl + "/g/radar/api/v2/web-unified-search/", getEntityWithHeader(request), JsonNode.class).get("itineraries").get("filterStats").get("carriers");
            log.info("Flight response: {}", response);
            for (JsonNode node : response){
                FlightResponse flightResponse = FlightResponse.builder().price(node.get("minPrice").asDouble()).airline(node.get("name").asText()).build();
                flights.add(flightResponse);
            }
            return flights;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Exception encountered");
        }
    }

    private FlightRequest createRequestBasedOnEntity(Ticket ticket){
        LegDetailObject legOrigin = LegDetailObject.builder().type(legDetailType).entityId(ticket.getStartingPointEntityId()).build();
        LegDetailObject legDestination = LegDetailObject.builder().type(legDetailType).entityId(ticket.getEndingPointEntityId()).build();

        LocalDate flightDate = ticket.getDate();
        DateObject dateObject = DateObject.builder()
                .type(dateType)
                .year(String.valueOf(flightDate.getYear()))
                .month(String.format("%02d", flightDate.getMonthValue()))
                .day(String.format("%02d", flightDate.getDayOfMonth()))
                .build();

        LegObject leg = LegObject.builder().legOrigin(legOrigin).legDestination(legDestination).dates(dateObject).build();

        return FlightRequest.builder().cabinClass(cabinClass).adults(1).legObject(leg).childAges(new ArrayList<>()).build();
    }

    private HttpEntity<Object> getEntityWithHeader(Object requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");
        headers.set("accept-language", "tr-TR,tr;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("cookie", "abgroup=16102031; __Secure-anon_token=eyJhbGciOiJSUzI1NiIsInR5cCI6Ikp...");
        headers.set("origin", "https://www.skyscanner.com.tr");
        headers.set("priority", "u=1, i");
        headers.set("referer", "https://www.skyscanner.com.tr/tasima/ucak-bileti/ista/cgn/250207?ref=hom...");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-model", "");
        headers.set("sec-ch-ua-platform", "\"macOS\"");
        headers.set("sec-fetch-dest", "empty");
        headers.set("sec-fetch-mode", "cors");
        headers.set("sec-fetch-site", "same-origin");
        headers.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130 Safari/537.36");
        headers.set("x-skyscanner-ads-sponsored-view-type", "ADS_SPONSORED_VIEW_DAY_VIEW");
        headers.set("x-skyscanner-channelid", "website");
        headers.set("x-skyscanner-consent-adverts", "true");
        headers.set("x-skyscanner-currency", "TRY");
        headers.set("x-skyscanner-locale", "tr-TR");
        headers.set("x-skyscanner-market", "TR");
        headers.set("x-skyscanner-traveller-context", "8e87f6e3-e331-4e22-8f5b-e0a8b2506edb");
        headers.set("x-skyscanner-trustedfunnelid", "3178078e-98ee-4a2f-92f1-5ca442263c1b");
        headers.set("x-skyscanner-viewid", "3178078e-98ee-4a2f-92f1-5ca442263c1b");

        return new HttpEntity<>(requestBody, headers);
    }
}
