package com.hedspi.ltct.delivery.controller;

import com.hedspi.ltct.delivery.exception.ApiRequestException;
import com.hedspi.ltct.delivery.model.Fee;
import com.hedspi.ltct.delivery.model.ShippingOrder;
import com.hedspi.ltct.delivery.model.Status;
import com.hedspi.ltct.delivery.response.CommonResponse;
import com.hedspi.ltct.delivery.response.Result;
import com.hedspi.ltct.delivery.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(path = "api/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {

        this.shippingService = shippingService;
    }

    @GetMapping(path = "/shipment")
    public List<Fee> getShipping() {
        return shippingService.getFee();
    }

    @GetMapping(path = "/status/{statusId}")
    public CommonResponse getShippingbyStatus(@PathVariable("statusId") Integer Id) {
        return shippingService.getSorderbyStatus(Id);
    }

    @GetMapping(path = "/shipment/{feeId}")
    public Fee getShipping(@PathVariable("feeId") Long Id) {
        return shippingService.getFeebyId(Id);
    }

    @GetMapping
    public CommonResponse getOrder() {
        return shippingService.getShippingOrder();
    }

    @GetMapping(path = "{orderId}")
    public CommonResponse getOrder(@PathVariable("orderId") Long Id) {
        return shippingService.getSorderbyId(Id);
    }

    @PostMapping
    public CommonResponse registerNewShipping(@RequestBody Fee fee){
        if(fee.getExpress() != null) {
            ShippingOrder shippingorder = new ShippingOrder(
                    "OD" + (int) ((Math.random() * (999 - 100)) + 100),
                    shippingService.getStatusCodebyId(1),
                    "Description",
                    Instant.now(),
                    Instant.now()
            );
            shippingService.addNewSorder(shippingorder);
            shippingService.addNewShipping(fee);
            return new CommonResponse(new Result("200","success",true),shippingorder);
        }
        return new CommonResponse(new Result("400","fail",false),null);
    }

    @PutMapping(path = "{orderId}")
    public CommonResponse updateSorder(@PathVariable("orderId") Long sorderId,
                              @RequestParam(required = false) Status statuscode,
                              @RequestParam(required = false) String detail){
        shippingService.updateSorder(sorderId, statuscode, detail);
        return shippingService.getSorderbyId(sorderId);
    }

    @PutMapping(path = "/cancel/{orderId}")
    public CommonResponse cancelOrder(@PathVariable("orderId") Long sorderId){
        shippingService.cancelOrder(sorderId, shippingService.getStatusCodebyId(4));
        return shippingService.getSorderbyId(sorderId);
    }

}
