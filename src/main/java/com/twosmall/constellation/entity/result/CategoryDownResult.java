package com.twosmall.constellation.entity.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDownResult {


    @JsonProperty("title")
    private String title;

    @JsonProperty("value")
    private Integer value;
}
