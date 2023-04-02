package com.airbnb.stockmanagement.bnbservice.repository;

import com.airbnb.stockmanagement.bnbservice.enums.Language;
import com.airbnb.stockmanagement.bnbservice.repository.entity.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

public interface houseRepository extends JpaRepository<house, Long> {

    house getByhouseIdAndDeletedFalse(Long houseId);
    List<house> getAllByDeletedFalse();

    @Query("SELECT h FROM house h WHERE h.deleted = false AND h.houseMaxguest >= :houseMaxguest ")
    List<house> findAvailableHouses(@Param("houseMaxguest") int houseMaxguest);

    @Query("UPDATE house h SET h.deleted = true WHERE h.houseId = :houseId AND h.productUpdateDate BETWEEN :startDate AND :endDate")
    house markHouseUnavailableForDates(@Param("houseId") Long houseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}