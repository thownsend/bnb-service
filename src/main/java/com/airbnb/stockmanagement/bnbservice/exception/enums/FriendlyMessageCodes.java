package com.airbnb.stockmanagement.bnbservice.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    HOUSES_NOT_CREATED_EXCEPTION(1500),
    HOUSES_SUCCESFULLY_CREATED(1501),
    HOUSES_NOT_FOUND_EXCEPTION(1502),
    HOUSES_SUCCESSFULLY_UPDATE(1503),
    HOUSES_ALREADY_DELETED(1504),
    HOUSES_SUCCESSFULLY_DELETED(1505),
    BOOKING_NOT_FOUND_EXCEPTION(1602),
    BOOKING_SUCCESSFULLY_UPDATE(1603),
    BOOKING_ALREADY_DELETED(1604),
    BOOKING_SUCCESSFULLY_DELETED(1605);
    private  final int value;
    FriendlyMessageCodes(int value){this.value=value;}

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
