package com.hedspi.ltct.delivery.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Random;

@Entity
@Table
public class Fee {
    @Id
    @SequenceGenerator(
            name = "shipment_sequence",
            sequenceName = "shipment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shipment_sequence"
    )
    private Long id;
    private Integer from_district_id;
    private Integer to_district_id;
    private String to_ward_code;
    private Integer weight;
    private Integer insurance_value;

    private Integer basefee = 5000;

    private Double dist;

    @Transient
    private Integer fee;

    public Fee() {
    }

    public Fee(Integer from_district_id, Integer to_district_id, String to_ward_code, Integer weight, Integer insurance_value) {
        this.from_district_id = from_district_id;
        this.to_district_id = to_district_id;
        this.to_ward_code = to_ward_code;
        this.weight = weight;
        this.insurance_value = insurance_value;
    }

    public Integer getFrom_district_id() {
        return from_district_id;
    }

    public void setFrom_district_id(Integer from_district_id) {
        this.from_district_id = from_district_id;
    }

    public Integer getTo_district_id() {
        return to_district_id;
    }

    public void setTo_district_id(Integer to_district_id) {
        this.to_district_id = to_district_id;
    }

    public String getTo_ward_code() {
        return to_ward_code;
    }

    public void setTo_ward_code(String to_ward_code) {
        this.to_ward_code = to_ward_code;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getDist() {return dist;}
    public void setDist(Double dist) {this.dist = dist;}

    public Integer getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(Integer insurance_value) {
        this.insurance_value = insurance_value;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer Feecalculator(Boolean reship){
        Double wfee = 0.0;
        Double dfee = 0.0;
        Double lat1 = nextDoubleBetween(20,21.5,this.from_district_id);
        Double lon1 = nextDoubleBetween(105,106,this.from_district_id);
        Double lat2 = nextDoubleBetween(20,21.5,this.to_district_id);
        Double lon2 = nextDoubleBetween(105,106,this.to_district_id);
        Double weight = Double.valueOf(this.weight);
        if(weight < 500){
            wfee = (weight/500)*2500;
        } else if (weight >= 500 && weight < 2000) {
            wfee = (weight/2000)*5000;
        } else if (weight >= 2000 && weight < 5000) {
            wfee = (weight/5000)*10000;
        } else if(weight >= 5000){
            wfee = 15000.0;
        }
        this.setDist(1000*distance(lat1,lon1,lat2,lon2,"K"));
        if (dist <= 2000){
            dfee = 10000.0;
        }else{
            dfee = (dist - 2000.0)*5;
        }
        Double finalfee =  dfee + wfee + this.basefee + insurance_value;
        System.out.println(dfee);
        System.out.println(wfee);
        System.out.println(basefee);
        System.out.println(insurance_value);
        return finalfee.intValue();
    }

    public static double nextDoubleBetween(double min, double max, Integer district_id) {
        Random random = new Random();
        random.setSeed(district_id);
        return (random.nextDouble() * (max - min)) + min;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
