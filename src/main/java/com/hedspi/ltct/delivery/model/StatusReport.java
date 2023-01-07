package com.hedspi.ltct.delivery.model;

import java.time.Instant;

public class StatusReport {
    private String status;
    private Instant updateAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public StatusReport() {
    }

    public StatusReport(String status, Instant updateAt) {
        this.status = status;
        this.updateAt = updateAt;
    }
}
