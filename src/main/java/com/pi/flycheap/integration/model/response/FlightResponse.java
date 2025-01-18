package com.pi.flycheap.integration.model.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightResponse {
    private Double price;
    private String airline;
}
