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
public class LegDetailObject {
    @JsonProperty("@type")
    private String type;
    @JsonProperty("entityId")
    private String entityId;
}
