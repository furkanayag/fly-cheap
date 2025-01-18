package com.pi.flycheap.integration.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateObject {
    @JsonProperty("@type")
    private String type = "date";
    @JsonProperty("year")
    private String year;
    @JsonProperty("month")
    private String month;
    @JsonProperty("day")
    private String day;
}
