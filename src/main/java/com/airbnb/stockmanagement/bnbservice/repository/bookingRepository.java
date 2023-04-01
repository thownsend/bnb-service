package com.airbnb.stockmanagement.bnbservice.repository;

import com.airbnb.stockmanagement.bnbservice.repository.entity.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;






public interface bookingRepository extends JpaRepository <booking, Long> {

    List<booking> findByHouseId(Long houseId);

    booking findByid(Long id);

    List<booking> findByDeletedFalse();

    @Query("SELECT b FROM booking b JOIN FETCH b.houseId WHERE b.deleted = false")
    List<booking> findAllWithHouse();

    @Query("SELECT b FROM booking b JOIN FETCH b.houseId WHERE b.houseId.houseId = :houseId AND b.deleted = false AND NOT EXISTS (SELECT bb FROM booking bb WHERE bb.houseId.houseId = :houseId AND bb.startDate <= :endDate AND bb.endDate >= :startDate)")
    List<booking> findAvailableBookingsByHouseId(@Param("houseId") Long houseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<booking> findByHouseIdAndEndDateGreaterThanEqualAndStartDateLessThanEqual(Long houseId, Date startDate, Date endDate);

    @Query("SELECT b FROM booking b WHERE b.houseId.houseId = :houseId AND b.deleted = false AND b.startDate <= :endDate AND b.endDate >= :startDate")
    List<booking> findBookingsByHouseIdAndDates(@Param("houseId") Long houseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

