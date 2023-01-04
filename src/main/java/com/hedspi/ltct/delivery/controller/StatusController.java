package com.hedspi.ltct.delivery.controller;

import com.hedspi.ltct.delivery.model.Status;
import com.hedspi.ltct.delivery.response.CommonResponse;
import com.hedspi.ltct.delivery.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping (value = "/status")
    public CommonResponse getAllStatus(){
        return statusService.getAllStatus();
    }
}
