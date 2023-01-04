package com.hedspi.ltct.delivery.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "shipping_order")
public class ShippingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_code")
    private Status statusCode;

    @Column(name = "status_detail")
    private String statusDetail;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "update_at")
    private Instant updateAt;

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public Status getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Long getId() {
        return id;
    }

    public ShippingOrder() {
    }

    public ShippingOrder(String orderCode,Status status, String statusDetail, Instant createAt, Instant updateAt) {
        this.orderCode = orderCode;
        this.statusCode = status;
        this.statusDetail = statusDetail;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public ShippingOrder(String orderCode, String statusDetail, Instant createAt, Instant updateAt) {
        this.orderCode = orderCode;
        this.statusDetail = statusDetail;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}