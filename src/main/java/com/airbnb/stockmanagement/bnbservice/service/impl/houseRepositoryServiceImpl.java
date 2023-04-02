package com.airbnb.stockmanagement.bnbservice.service.impl;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.FriendlyMessageCodes;
import com.airbnb.stockmanagement.bnbservice.exception.exceptions.HouseAldreadyDeletedException;
import com.airbnb.stockmanagement.bnbservice.exception.exceptions.HouseNotCreatedException;
import com.airbnb.stockmanagement.bnbservice.exception.exceptions.HouseNotFoundException;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;
import com.airbnb.stockmanagement.bnbservice.repository.houseRepository;
import com.airbnb.stockmanagement.bnbservice.request.houseCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.houseUpdateRequest;
import com.airbnb.stockmanagement.bnbservice.service.IhouseRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor

public class houseRepositoryServiceImpl implements IhouseRepositoryService {
    private  final houseRepository houseRepository;

    @Override
    public house createHouse(Language language, houseCreateRequest houseCreateRequest) {
        log.debug("[{}][createHouse] -> request: {}", this.getClass().getSimpleName(),houseCreateRequest);
        try{
            house house1=house.builder()
                    .houseName(houseCreateRequest.getHouseName())
                    .houseDescription(houseCreateRequest.getHouseDescription())
                    .houseAmenities(houseCreateRequest.getHouseAmenities())
                    .houseMaxguest(houseCreateRequest.getHouseMaxguest())
                    .deleted(false)
                    .build();
            house houseResponse = houseRepository.save(house1);
            log.debug("[{}][createHouse] -> response: {}", this.getClass().getSimpleName(),houseResponse);
            return houseResponse;
        } catch (Exception exception){
            throw new HouseNotCreatedException(language, FriendlyMessageCodes.HOUSES_NOT_CREATED_EXCEPTION,"house request: " + houseCreateRequest.toString());
        }
    }

    @Override
    public house getHouse(Language language, Long houseId) {
        log.debug("[{}][getHouse] -> request houseId{}", this.getClass().getSimpleName(),houseId);
        house house = houseRepository.getByhouseIdAndDeletedFalse(houseId);
        if (Objects.isNull(house)){
            throw new HouseNotCreatedException(language,FriendlyMessageCodes.HOUSES_NOT_FOUND_EXCEPTION,"House not found for house id: "+ houseId);
        }
        log.debug("[{}][getHouse] -> response: {}", this.getClass().getSimpleName(),house);
        return house;
    }

    @Override
    public List<house> getHouses(Language language) {
        log.debug("[{}][getHouses]", this.getClass().getSimpleName());
        List<house> houses = houseRepository.getAllByDeletedFalse();
        if (houses.isEmpty()){
            throw new HouseNotFoundException(language,FriendlyMessageCodes.HOUSES_NOT_FOUND_EXCEPTION,"Houses not found.");
        }
        log.debug("[{}][getHouses] -> response: {}", this.getClass().getSimpleName(),houses);

        return houses;
    }

    @Override
    public house updateHouse(Language language, Long houseId, houseUpdateRequest houseUpdateRequest) {

        log.debug("[{}][updateHouse] -> request: {}", this.getClass().getSimpleName(),houseUpdateRequest);
        house house=getHouse(language,houseId);
        house.setHouseName(houseUpdateRequest.getHouseName());
        house.setHouseAmenities(houseUpdateRequest.getHouseAmenities());
        house.setHouseDescription(houseUpdateRequest.getHouseDescription());
        house.setHouseMaxguest(houseUpdateRequest.getHouseMaxguest());
        house.setProductUpdateDate(new Date());

        house houseResponse=houseRepository.save(house);
        log.debug("[{}][updateHouse] -> response: {}", this.getClass().getSimpleName(),houseResponse);

        return houseResponse;
    }

    @Override
    public house deleteHouse(Language language, Long houseId) {
        log.debug("[{}][deleteHouse] -> request: {}", this.getClass().getSimpleName(),houseId);
        house house;
        try {
            house=getHouse(language,houseId);
            house.setDeleted(true);
            house.setProductUpdateDate(new Date());
            house houseResponse= houseRepository.save(house);
            log.debug("[{}][deleteHouse] -> response: {}", this.getClass().getSimpleName(),houseResponse);
            return houseResponse;
        }catch (HouseNotFoundException exx){
            throw new HouseAldreadyDeletedException(language,FriendlyMessageCodes.HOUSES_ALREADY_DELETED,"House already deleted product id: " +houseId);
        }
    }



    @Override
    public List<house>findAvailableHouses(int houseMaxguest) {
        log.debug("[{}][getAvailableHouses]", this.getClass().getSimpleName());
        List<house> houses = houseRepository.findAvailableHouses(houseMaxguest);
        log.debug("[{}][getAvailableHouses] -> response: {}", this.getClass().getSimpleName(),houses);
        return houses;
    }

}


