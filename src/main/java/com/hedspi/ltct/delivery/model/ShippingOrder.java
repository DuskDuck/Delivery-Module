package com.hedspi.ltct.delivery.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shipping_order")
public class ShippingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_id", length = 50)
    private String orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_code")
    private Status statusCode;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "update_at")
    private Instant updateAt;

    @Column(name = "cod")
    private Integer cod;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "re-shipped")
    private Boolean reship = false;

    @Column(name = "fee")
    @Transient
    private Integer fee;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Receiver receiver;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Product> products;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Status getStatusCode() {
        return statusCode;
    }

    public StatusReport getStatus(){
        return new StatusReport(statusCode.getDesc(),updateAt);
    }
    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }

    public Instant getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getReship() {
        return reship;
    }
    public void setReship(Boolean reship) {
        this.reship = reship;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getFee() {
        return calFee();
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public ShippingOrder() {
    }

    public ShippingOrder(String orderId, Warehouse warehouse, Receiver receiver, Integer cod, Integer weight, List<Product> products) {
        this.orderId = orderId;
        this.cod = cod;
        this.warehouse = warehouse;
        this.receiver = receiver;
        this.weight = weight;
        this.products = products;
    }

    public Integer calFee(){
        Fee fee = new Fee(
                warehouse.getAddress().getDistrict(),
                receiver.getAddress().getDistrict(),
                receiver.getAddress().getWard(),
                weight,
                2000
        );
        fee.setDist(distance(warehouse.getAddress().getLatitude(),
                             warehouse.getAddress().getLongtitude(),
                             receiver.getAddress().getLatitude(),
                             receiver.getAddress().getLongtitude(),
                             "K"));
        return fee.Feecalculator(reship);
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

    public Boolean checkValidity(){
        if(this.cod < 0 || this.weight < 0 ){
            return false;
        }
        if(orderId.contains("DH")){
        }else{
            return false;
        }
        if(warehouse.getAddress().getDistrict() < 0 || receiver.getAddress().getDistrict() < 0){
            return false;
        }
        return true;
    }
}