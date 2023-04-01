package com.airbnb.stockmanagement.bnbservice.exception.exceptions;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.IFriendlyMessageCode;
import com.airbnb.stockmanagement.bnbservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class HouseAldreadyDeletedException extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public HouseAldreadyDeletedException(Language language, IFriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[HouseAlreadyDeletedException] -> message: {} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);

    }
}
