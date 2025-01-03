package com.pi.flycheap.integration.model.request;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
public class FlightRequest {
    private String startingPoint;
    private String endingPoint;
    private LocalDate startingDate;
    private LocalDate endingDate;
}
