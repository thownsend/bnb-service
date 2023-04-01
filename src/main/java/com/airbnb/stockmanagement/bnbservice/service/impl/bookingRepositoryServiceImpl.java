package com.airbnb.stockmanagement.bnbservice.service.impl;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.FriendlyMessageCodes;
import com.airbnb.stockmanagement.bnbservice.exception.exceptions.*;
import com.airbnb.stockmanagement.bnbservice.repository.entity.booking;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;
import com.airbnb.stockmanagement.bnbservice.repository.houseRepository;
import com.airbnb.stockmanagement.bnbservice.request.bookingCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.bookingUpdateRequest;
import com.airbnb.stockmanagement.bnbservice.service.IbookingRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class bookingRepositoryServiceImpl implements IbookingRepositoryService {
    private final com.airbnb.stockmanagement.bnbservice.repository.bookingRepository bookingRepository;

    @Autowired
    private houseRepository houseRepository;

    @Override
    public booking createBooking(Language language, bookingCreateRequest request) {
        house house = houseRepository.getByhouseIdAndDeletedFalse(request.getHouseId());

        if (house != null) {
            List<booking> overlappingBookings = bookingRepository.findBookingsByHouseIdAndDates(house.getHouseId(), request.getStartDate(), request.getEndDate());
            if (overlappingBookings.isEmpty()) {
                booking newBooking = booking.builder()
                        .houseId(house)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .guestName(request.getGuestName())
                        .deleted(false)
                        .build();

                return bookingRepository.save(newBooking);
            } else {
                throw new IllegalArgumentException("House is not available for the given dates.");
            }
        } else {
            throw new IllegalArgumentException("Invalid house ID.");
        }
    }

    @Override
    public booking updateBooking(Language language, Long id, bookingUpdateRequest bookingUpdateRequest) {
        log.debug("[{}][updateBooking] -> request: {}", this.getClass().getSimpleName(), bookingUpdateRequest);
        booking book = getBookingById(language, id);
        book.setGuestName(bookingUpdateRequest.getGuestName());
        book.setBookingDate(bookingUpdateRequest.getBookingDate());
        book.setStartDate(bookingUpdateRequest.getStartDate());
        book.setEndDate(bookingUpdateRequest.getEndDate());
        booking bookingResponse = bookingRepository.save(book);
        log.debug("[{}][updateHouse] -> response: {}", this.getClass().getSimpleName(), bookingResponse);

        return bookingResponse;
    }

    @Override
    public booking deleteBooking(Language language, Long id) {
        log.debug("[{}][deleteBooking] -> request: {}", this.getClass().getSimpleName(),id);
        booking booking;
        try {
            booking=getBookingById(language,id);
            booking.setDeleted(true);
            booking bookingResponse= bookingRepository.save(booking);
            log.debug("[{}][deleteBooking] -> response: {}", this.getClass().getSimpleName(),bookingResponse);
            return bookingResponse;
        }catch (BookingNotFoundException exx){
            throw new BookingAlreadyDeletedException(language,FriendlyMessageCodes.BOOKING_ALREADY_DELETED,"Booking already deleted product id: " +id);
    }
    }

    @Override
    public booking getBookingById(Language language, Long id) {
        log.debug("[{}][getBookingById] -> request id: {}", this.getClass().getSimpleName(), id);
        booking book = bookingRepository.findByid(id);
        if (Objects.isNull(book)) {
            throw new HouseNotCreatedException(language, FriendlyMessageCodes.BOOKING_NOT_FOUND_EXCEPTION, "Booking not found for house:" + id);
        }
        log.debug("[{}][getBookingById] -> response: {}", this.getClass().getSimpleName(), book);
        return book;
    }

    @Override
    public List<booking> getAllBookings(Language language) {
        log.debug("[{}][getAllBookings]", this.getClass().getSimpleName());
        List<booking> bookings = bookingRepository.findByDeletedFalse();
        if (bookings.isEmpty()) {
            throw new HouseNotFoundException(language, FriendlyMessageCodes.BOOKING_NOT_FOUND_EXCEPTION, "Bookingnot found.");
        }
        log.debug("[{}][getAllBookings] -> response: {}", this.getClass().getSimpleName(), bookings);
        return bookings;
    }
}



