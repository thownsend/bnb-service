package com.airbnb.stockmanagement.bnbservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class houseResponse {
private Long houseId;
private String houseName;
private String houseDescription;
private String houseAmenities;
private int houseMaxguest;
private Long productCreateDate;
private Long productUpdateDate;
}
