package com.airbnb.stockmanagement.bnbservice.exception.exceptions;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.IFriendlyMessageCode;
import com.airbnb.stockmanagement.bnbservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class HouseNotCreatedException extends RuntimeException
{

    private  final Language language;
    private  final IFriendlyMessageCode friendlyMessageCode;

    public HouseNotCreatedException(Language language, IFriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[HouseNotCreatedExceptions] -> message {} deevloper message: {} ",FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);

    }


}
