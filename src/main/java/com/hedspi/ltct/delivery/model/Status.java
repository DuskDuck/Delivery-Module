package com.hedspi.ltct.delivery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_code", nullable = false)
    private Integer id;

    public Status() {
    }

    @Column(name = "`desc`")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public Integer getId() {
        return id;
    }

    public Status(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}