package com.pi.flycheap.integration.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class FlightResponse {
    private Long price;
}
