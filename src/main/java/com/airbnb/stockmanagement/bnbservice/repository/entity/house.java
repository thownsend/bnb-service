package com.airbnb.stockmanagement.bnbservice.repository.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "house",schema = "stock_management")
public class house {
    @Id
    @Column(name="house_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long houseId;

    @Column(name = "house_name")
    private String houseName;

    @Column(name = "house_description")
    private String houseDescription;

    @Column(name = "house_amenitites")
    private String houseAmenities;

    @Column(name = "house_Maxguest")
    private int houseMaxguest;

    @Builder.Default
    @Column(name = "product_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date productUpdateDate=new Date();

    @Builder.Default
    @Column(name = "product_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date productCreatedDate=new Date();

    @Column(name = "is_deleted")
    private boolean deleted;
}
