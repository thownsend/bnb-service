package com.airbnb.stockmanagement.bnbservice.service;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;
import com.airbnb.stockmanagement.bnbservice.request.houseCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.houseUpdateRequest;

import java.util.Date;
import java.util.List;

public interface IhouseRepositoryService {
    house createHouse(Language language, houseCreateRequest houseCreateRequest);

    house getHouse(Language language, Long houseId);

    List<house> getHouses(Language language);

    house updateHouse(Language language, Long houseId, houseUpdateRequest houseUpdateRequest );

    house deleteHouse(Language language,Long houseId);

    List<house> findAvailableHouses(Date startDate, Date endDate, int houseMaxguest);


}
