package com.airbnb.stockmanagement.bnbservice.controller;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.exception.enums.FriendlyMessageCodes;
import com.airbnb.stockmanagement.bnbservice.exception.utils.FriendlyMessageUtils;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;
import com.airbnb.stockmanagement.bnbservice.request.houseCreateRequest;
import com.airbnb.stockmanagement.bnbservice.request.houseUpdateRequest;
import com.airbnb.stockmanagement.bnbservice.response.FriendlyMessage;
import com.airbnb.stockmanagement.bnbservice.response.InternalApiResponse;
import com.airbnb.stockmanagement.bnbservice.response.houseResponse;
import com.airbnb.stockmanagement.bnbservice.service.IhouseRepositoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/house")
@RequiredArgsConstructor

    public class houseController {
    private final IhouseRepositoryService houseRepositoryService;
    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping(value = "/{language}/create")

    public InternalApiResponse<houseResponse> createHouse(@PathVariable("language")Language language,
                                                          @RequestBody houseCreateRequest houseCreateRequest){
        log.debug("[{}][createHouse] -> request: {}",this.getClass().getSimpleName(),houseCreateRequest);
        house house= houseRepositoryService.createHouse(language,houseCreateRequest);
        houseResponse houseResponse = convertHouseResponse(house);
        log.debug("[{}][createHouse] -> response: {}",this.getClass().getSimpleName(),houseResponse);
        return InternalApiResponse.<houseResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.HOUSES_SUCCESFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(houseResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{houseId}")
    public InternalApiResponse<houseResponse> getHouse(@PathVariable("language")Language language,
                                                       @PathVariable("houseId") Long houseId){
        log.debug("[{}][getHouse] -> request houseId: {}",this.getClass().getSimpleName(),houseId);
        house house = houseRepositoryService.getHouse(language,houseId);
        houseResponse response = convertHouseResponse(house);
        log.debug("[{}][getHouse] -> response: {}",this.getClass().getSimpleName(),response);
        return InternalApiResponse.<houseResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();


    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{houseId}")
    public InternalApiResponse<houseResponse> updateHouse(@PathVariable("language")Language language,
                                                          @PathVariable("houseId") Long houseId,
                                                          @RequestBody houseUpdateRequest request)
    {
        log.debug("[{}][updateHouse] -> request: {} {}", this.getClass().getSimpleName(),houseId,request);
        house house=houseRepositoryService.updateHouse(language,houseId,request);
        houseResponse houseResponse = convertHouseResponse(house);
        log.debug("[{}][updateHouse] -> response: {}", this.getClass().getSimpleName(),houseResponse);

        return InternalApiResponse.<houseResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.HOUSES_SUCCESSFULLY_UPDATE))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(houseResponse)
                .build();
    }

    @ApiOperation(value = "This endpoint get all product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/houses")
    public InternalApiResponse<List<houseResponse>> getHouses(@PathVariable("language") Language language){
        log.debug("[{}][getHouses]", this.getClass().getSimpleName());
        List<house> houses = houseRepositoryService.getHouses(language);
        List<houseResponse> houseResponses = convertHouseResponseList(houses);
        log.debug("[{}][getHouses] -> response: {}", this.getClass().getSimpleName(),houseResponses);
        return InternalApiResponse.<List<houseResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(houseResponses)
                .build();

    }

    @ApiOperation(value = "This endpoint get query product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/available/{houseMaxguest}")
    public InternalApiResponse<List<houseResponse>> findAvailableHouses( @PathVariable("houseMaxguest") int houseMaxguest) {

        log.debug("[{}][getAvailableHouses]", this.getClass().getSimpleName());
        List<house> houses = houseRepositoryService.findAvailableHouses(houseMaxguest);
        List<houseResponse> houseResponses = convertHouseResponseList(houses);
        log.debug("[{}][getAvailableHouses] -> response: {}", this.getClass().getSimpleName(),houseResponses);
        return InternalApiResponse.<List<houseResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(houseResponses)
                .build();
    }



    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/deleted/{houseId}")
    public InternalApiResponse<houseResponse> deletedHouse(@PathVariable("language")Language language,
                                                           @PathVariable("houseId") Long houseId)
    {
        log.debug("[{}][deleteHouse] -> request houseId: {}", this.getClass().getSimpleName(),houseId);
        house house = houseRepositoryService.deleteHouse(language,houseId);
        houseResponse response = convertHouseResponse(house);
        log.debug("[{}][deleteHouses] -> response: {}", this.getClass().getSimpleName(),response);
        return  InternalApiResponse.<houseResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.HOUSES_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();

    }


    private  List<houseResponse>convertHouseResponseList(List<house> houseList){
       return  houseList.stream()
               .map(arg -> houseResponse.builder()
                       .houseId(arg.getHouseId())
                       .houseName(arg.getHouseName())
                       .houseMaxguest(arg.getHouseMaxguest())
                       .houseAmenities(arg.getHouseAmenities())
                       .houseDescription(arg.getHouseDescription())
                       .productUpdateDate(arg.getProductUpdateDate().getTime())
                       .productCreateDate(arg.getProductCreatedDate().getTime())
                       .build())
               .collect(Collectors.toList());
    }
    private houseResponse convertHouseResponse(house house) {
        return houseResponse.builder()
                .houseId(house.getHouseId())
                .houseName(house.getHouseName())
                .houseDescription(house.getHouseDescription())
                .houseAmenities(house.getHouseAmenities())
                .houseMaxguest(house.getHouseMaxguest())
                .productCreateDate(house.getProductCreatedDate().getTime())
                .productUpdateDate(house.getProductUpdateDate().getTime())
                .build();
    }



}
