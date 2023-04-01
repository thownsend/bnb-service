package com.airbnb.stockmanagement.bnbservice.request;

import lombok.Data;

@Data
public class houseUpdateRequest {
    private long houseId;
    private String houseName;
    private String houseDescription;
    private String houseAmenities;
    private int houseMaxguest;
}