package com.hedspi.ltct.delivery.model;

public class FeeResult {
    private Integer totalFee;
    private Integer serviceFee;
    private Integer insuranceFee;
    private Integer pickStationFee;

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(Integer insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public Integer getPickStationFee() {
        return pickStationFee;
    }

    public void setPickStationFee(Integer pickStationFee) {
        this.pickStationFee = pickStationFee;
    }

    public FeeResult(Integer totalFee, Integer serviceFee, Integer insuranceFee, Integer pickStationFee) {
        this.totalFee = totalFee;
        this.serviceFee = serviceFee;
        this.insuranceFee = insuranceFee;
        this.pickStationFee = pickStationFee;
    }

}

