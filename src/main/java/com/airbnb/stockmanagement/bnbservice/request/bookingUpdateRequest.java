package com.airbnb.stockmanagement.bnbservice.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class bookingUpdateRequest {
    private long id;

    private long houseId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    private String guestName;
    private boolean deleted;
}