package com.airbnb.stockmanagement.bnbservice.request;

import lombok.Data;

import java.util.Date;

@Data
public class bookingCreateRequest {
    private long houseId;
    private Date startDate;
    private Date endDate;
    private String guestName;
}

