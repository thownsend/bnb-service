package com.airbnb.stockmanagement.bnbservice.request;

import lombok.Data;

@Data
public class houseCreateRequest {
    private  String houseName;
    private String houseDescription;
    private String houseAmenities;
    private int houseMaxguest;
}

