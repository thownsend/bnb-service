package com.airbnb.stockmanagement.bnbservice.controller;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.FriendlyMessageCodes;
import com.airbnb.stockmanagement.bnbservice.exception.utils.FriendlyMessageUtils;
import com.airbnb.stockmanagement.bnbservice.repository.entity.booking;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;
import com.airbnb.stockmanagement.bnbservice.request.bookingCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.bookingUpdateRequest;
import com.airbnb.stockmanagement.bnbservice.request.houseUpdateRequest;
import com.airbnb.stockmanagement.bnbservice.response.FriendlyMessage;
import com.airbnb.stockmanagement.bnbservice.response.InternalApiResponse;
import com.airbnb.stockmanagement.bnbservice.response.bookingResponse;
import com.airbnb.stockmanagement.bnbservice.response.houseResponse;
import com.airbnb.stockmanagement.bnbservice.service.IbookingRepositoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/booking")
@RequiredArgsConstructor

public class bookingController {
    private final IbookingRepositoryService bookingRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<bookingResponse> createBooking(@PathVariable("language")Language language,
                                                              @RequestBody bookingCreateRequest request){
        log.debug("[{}][createBooking] -> request: {}",this.getClass().getSimpleName(),request);
        booking booking = bookingRepositoryService.createBooking(language,request);
        bookingResponse bookingResponse = convertBookingResponse(booking);
        log.debug("[{}][createBooking] -> response: {}",this.getClass().getSimpleName(),bookingResponse);
        return InternalApiResponse.<com.airbnb.stockmanagement.bnbservice.response.bookingResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.HOUSES_SUCCESFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(bookingResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{id}")
    public InternalApiResponse<bookingResponse> getBookingById(@PathVariable("language")Language language,
                                                       @PathVariable("id") Long id){
        log.debug("[{}][getBookingById] -> request id: {}",this.getClass().getSimpleName(),id);
        booking booking = bookingRepositoryService.getBookingById(language,id);
        bookingResponse response = convertBookingResponse(booking);
        log.debug("[{}][getBookingById] -> response: {}",this.getClass().getSimpleName(),response);
        return InternalApiResponse.<bookingResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();


    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{Id}")
    public InternalApiResponse<bookingResponse> updateBooking(@PathVariable("language")Language language,
                                                          @PathVariable("Id") Long Id,
                                                          @RequestBody bookingUpdateRequest request)
    {
        log.debug("[{}][updateBooking] -> request: {} {}", this.getClass().getSimpleName(),Id,request);
        booking book=bookingRepositoryService.updateBooking(language,Id,request);
        bookingResponse response = convertBookingResponse(book);
        log.debug("[{}][updateBooking] -> response: {}", this.getClass().getSimpleName(),response);

        return InternalApiResponse.<bookingResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.BOOKING_SUCCESSFULLY_UPDATE))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @ApiOperation(value = "This endpoint get all product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/bookings")
    public InternalApiResponse<List<bookingResponse>> getAllBookings(@PathVariable("language") Language language){
        log.debug("[{}][getAllBookings]", this.getClass().getSimpleName());
        List<booking> bookings = bookingRepositoryService.getAllBookings(language);
        List<bookingResponse> responses = convertBookingResponseList(bookings);
        log.debug("[{}][getAllBookings] -> response: {}", this.getClass().getSimpleName(),responses);
        return InternalApiResponse.<List<bookingResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(responses)
                .build();

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/deleted/{id}")
    public InternalApiResponse<bookingResponse> deletedBooking(@PathVariable("language")Language language,
                                                           @PathVariable("id") Long id)
    {
        log.debug("[{}][deleteBooking] -> request id: {}", this.getClass().getSimpleName(),id);
        booking book = bookingRepositoryService.deleteBooking(language,id);
        bookingResponse response = convertBookingResponse(book);
        log.debug("[{}][deleteBooking] -> response: {}", this.getClass().getSimpleName(),response);
        return  InternalApiResponse.<bookingResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.BOOKING_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();

    }

    private  List<bookingResponse>convertBookingResponseList(List<booking> bookingList){
        return  bookingList.stream()
                .map(arg -> bookingResponse.builder()
                        .id(arg.getId())
                        .guestName(arg.getGuestName())
                        .startDate(arg.getStartDate())
                        .endDate(arg.getEndDate())
                        .createdDate(arg.getCreatedDate())
                        .bookingDate(arg.getBookingDate())
                        .houseId(arg.getHouseId().getHouseId())
                        .build())
                .collect(Collectors.toList());
    }



    private bookingResponse convertBookingResponse(booking booking) {
        return bookingResponse.builder()
                .id(booking.getId())
                .houseId(booking.getHouseId().getHouseId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .guestName(booking.getGuestName())
                .bookingDate(booking.getBookingDate())
                .createdDate(booking.getCreatedDate())
                .build();
    }
}
