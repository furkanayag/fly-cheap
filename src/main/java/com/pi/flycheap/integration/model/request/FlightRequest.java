package com.pi.flycheap.integration.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequest {
    @JsonProperty("cabinClass")
    private String cabinClass;
    @JsonProperty("childAges")
    private ArrayList<Integer> childAges;
    @JsonProperty("adults")
    private Integer adults;
    @JsonProperty("legs")
    private LegObject legObject;
}
