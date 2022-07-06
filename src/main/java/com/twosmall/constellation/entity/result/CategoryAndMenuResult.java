package com.twosmall.constellation.entity.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CategoryAndMenuResult {

    @JsonProperty("order")
    private Integer order;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("foodList")
    private List<FoodListDTO> foodList;

    @NoArgsConstructor
    @Data
    public static class FoodListDTO {
        @JsonProperty("classifyId")
        private Integer classifyId;
        @JsonProperty("menuName")
        private String menuName;
        @JsonProperty("photo")
        private String photo;
        @JsonProperty("price")
        private String price;
        @JsonProperty("orderNum")
        private Integer orderNum;
        @JsonProperty("id")
        private Integer id;
    }
}
