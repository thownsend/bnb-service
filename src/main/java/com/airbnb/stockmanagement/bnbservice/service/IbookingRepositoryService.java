package com.airbnb.stockmanagement.bnbservice.service;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.repository.entity.booking;
import com.airbnb.stockmanagement.bnbservice.request.bookingCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.bookingUpdateRequest;

import java.util.List;

public interface IbookingRepositoryService {

    booking createBooking(Language language, bookingCreateRequest bookingCreateRequest);

    booking updateBooking(Language language,Long id, bookingUpdateRequest bookingUpdateRequest);

    booking deleteBooking(Language language,Long id);

    booking getBookingById(Language language,Long id);

    List<booking> getAllBookings(Language language);
}