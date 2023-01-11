package com.hedspi.ltct.delivery.controller;

import com.hedspi.ltct.delivery.exception.ApiRequestException;
import com.hedspi.ltct.delivery.model.*;
import com.hedspi.ltct.delivery.response.CommonResponse;
import com.hedspi.ltct.delivery.response.Result;
import com.hedspi.ltct.delivery.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "https://delivery-sp09.vercel.app/")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }


    //REQUIRED
    //ok
    @PostMapping(value = "/shipping_order", consumes = "application/json", produces = "application/json")
    public CommonResponse registerNewShipping(@RequestBody ShippingOrder shippingorder){
        if(shippingorder.checkValidity()) {
            shippingorder.setStatusCode(shippingService.getStatusCodebyId(1));
            shippingorder.setCreateAt(Instant.now());
            shippingorder.setUpdateAt(Instant.now());
            shippingorder.calFee();
            shippingService.addNewSorder(shippingorder);
            return new CommonResponse(new Result("200", "success", true));
        }else{
            return new CommonResponse(new Result("400", "Incorrect info format", false));
        }
    }

    //ok
    @PostMapping(path = "/shipping_fee")
    public CommonResponse calShippingFee(@RequestBody Fee fee){
        shippingService.addNewShipping(fee);
        fee.setFee(fee.Feecalculator(false));
        FeeResult feeResult = new FeeResult(
                fee.getFee(),
                fee.getFee()-fee.getInsurance_value(),
                fee.getInsurance_value(),
                0
        );
        return new CommonResponse(new Result("200","success",true),feeResult);
    }

    @GetMapping(path = "/shipping_fee")
    public CommonResponse getAllFee(){
        return shippingService.getAllFee();
    }

    //ok
    @GetMapping(path = "/shipping_order/status/{orderCode}")
    public CommonResponse getOrderStatus(@PathVariable("orderCode") String orderCode){
        return shippingService.getStatusbyCode(orderCode);
    }

    //ok
    @PutMapping(path = "/shipping_order/redeliver/{orderCode}")
    public CommonResponse Reshipping(@PathVariable("orderCode") String orderCode, @RequestBody Product product){
        return shippingService.reshipping(orderCode,product,shippingService.getStatusCodebyId(2));
    }

    //ok
    @GetMapping(path = "/shipping_order/fee/{orderCode}")
    public CommonResponse calShippingFee(@PathVariable("orderCode") String orderCode){
        return shippingService.getOrderfeebyCode(orderCode);
    }

    //ok
    @PutMapping(path = "/shipping_order/cancel/{orderCode}")
    public CommonResponse cancelOrder(@PathVariable("orderCode") String orderCode){
        return shippingService.updateOrderStatus(orderCode, shippingService.getStatusCodebyId(4));
    }

    //ok
    @PutMapping(path = "/shipping_order/return/{orderCode}")
    public CommonResponse returnOrder(@PathVariable("orderCode") String orderCode){
        return shippingService.updateOrderStatus(orderCode, shippingService.getStatusCodebyId(5));
    }

    //ok
    @PutMapping(path = "/shipping_order/confirmReceipt/{orderCode}")
    public CommonResponse confirmReceive(@PathVariable("orderCode") String orderCode){
        return shippingService.updateOrderStatus(orderCode, shippingService.getStatusCodebyId(3));
    }

    //For FE
    @GetMapping(path = "/shipping_order")
    public CommonResponse getAllshipping(){
        return shippingService.getShippingOrder();
    }

    @GetMapping(path = "/shipping_order/{orderCode}")
    public CommonResponse getOrderSpecific(@PathVariable("orderCode") String orderCode){
        return shippingService.getOrderbyCode(orderCode);
    }

    @GetMapping(path = "shipping_order/group/status/{statusId}")
    public CommonResponse getOrderbyStatus(@PathVariable("statusId") Integer Id){
        return shippingService.getSorderbyStatus(Id);
    }

    @GetMapping(path = "shipping_order/count/status/{statusId}")
    public CommonResponse countOrderbyStatus(@PathVariable("statusId") Integer Id){
        return shippingService.countSorderbyStatus(Id);
    }

    @PutMapping(path = "/shipping_order/status/{orderCode}/{statusid}")
    public CommonResponse updateOrderStatusFE(@PathVariable("orderCode") String orderCode,@PathVariable("statusid") Integer statusid){
        return shippingService.updateOrderStatusFE(orderCode, shippingService.getStatusCodebyId(statusid));
    }


}
