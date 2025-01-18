package com.pi.flycheap.integration.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LegObject {
    @JsonProperty("legOrigin")
    private LegDetailObject legOrigin;
    @JsonProperty("legDestination")
    private LegDetailObject legDestination;
    @JsonProperty("dates")
    private DateObject dates;
    @JsonProperty("placeOfStay")
    private String placeOfStay = null;
}
