package com.hedspi.ltct.delivery.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String ward;
    private Integer district;
    private Integer province;
    private String detail;

    @Transient
    private Double latitude;
    @Transient
    private Double longtitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getLatitude() {
        return nextDoubleBetween(20,21.5,this.district);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return nextDoubleBetween(105,106,this.district);
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Address() {
    }
    public Address(String ward, Integer district, Integer province, String detail) {
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ward='" + ward + '\'' +
                ", district='" + district + '\'' +
                ", province='" + province + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    public static double nextDoubleBetween(double min, double max, Integer district_id) {
        Random random = new Random();
        random.setSeed(district_id);
        return (random.nextDouble() * (max - min)) + min;
    }
}
