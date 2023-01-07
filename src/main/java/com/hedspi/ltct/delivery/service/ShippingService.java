package com.hedspi.ltct.delivery.service;

import com.hedspi.ltct.delivery.exception.ApiRequestException;
import com.hedspi.ltct.delivery.model.*;
import com.hedspi.ltct.delivery.repository.FeeRepository;
import com.hedspi.ltct.delivery.repository.ShippingOrderRepository;
import com.hedspi.ltct.delivery.repository.StatusRepository;
import com.hedspi.ltct.delivery.response.CommonResponse;
import com.hedspi.ltct.delivery.response.Result;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    public ShippingService(FeeRepository feeRepository, ShippingOrderRepository shippingOrderRepository, StatusRepository statusRepository) {
        this.feeRepository = feeRepository;
        this.statusRepository = statusRepository;
        this.shippingOrderRepository = shippingOrderRepository;
    }

    private final FeeRepository feeRepository;
    private final StatusRepository statusRepository;
    private final ShippingOrderRepository shippingOrderRepository;

    public List<Fee> getFee() {
        return feeRepository.findAll();
    }

    public CommonResponse getShippingOrder() {
        return new CommonResponse(new Result("200","success",true),shippingOrderRepository.findAll());
    }

    public CommonResponse getSorderbyId(Long Id){
        Optional<ShippingOrder> shippingOrder = shippingOrderRepository.findById(Id);
        if(shippingOrder.isPresent()){
            return new CommonResponse(new Result("200","success",true),shippingOrder);
        }
        return new CommonResponse(new Result("400","fail: Shipping order with id " + Id + " can not be found",false),null);
    }

    public CommonResponse getSorderbyStatus(Integer Id){
        Optional<Status> statuscodeotp = statusRepository.findById(Id);
        List<ShippingOrder> shippingOrdersList = new ArrayList<ShippingOrder>();
        if(statuscodeotp.isPresent()){
            List<ShippingOrder> orderList = shippingOrderRepository.findAll();
            for(int i = 0; i < orderList.size(); i++){
                if(orderList.get(i).getStatusCode().getId() == Id){
                    shippingOrdersList.add(orderList.get(i));
                }
            }
            return new CommonResponse(new Result("200","success",true),shippingOrdersList);
        }
        return new CommonResponse(new Result("400","fail: Shipping order with id " + Id + " can not be found",false),null);
    }

    public CommonResponse getOrderbyCode(String code){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {

            if (orderList.get(i).getOrderId().equals(code)) {

                return new CommonResponse(new Result("200","success",true),orderList.get(i));
            }
        }
        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can not be found",false));
    }

    public CommonResponse getStatusbyCode(String code){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {
            System.out.println(orderList.get(i).getOrderId());
            if (orderList.get(i).getOrderId().equals(code)) {
                return new CommonResponse(new Result("200","success",true),orderList.get(i).getStatus());
            }
        }
        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can not be found",false));
    }

    public Long getOrderIdbyCode(String code){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(code)) {
                return orderList.get(i).getId();
            }
        }
        return null;
    }

    public CommonResponse getOrderfeebyCode(String code){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {

            if (orderList.get(i).getOrderId().equals(code)) {
                orderList.get(i).calFee();
                FeeResult feeResult = new FeeResult(
                        orderList.get(i).getFee(),
                        orderList.get(i).getFee()-2000,
                        2000,
                        0
                );
                return new CommonResponse(new Result("200","success",true),feeResult);
            }
        }
        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can not be found",false));
    }

    @Transactional
    public CommonResponse reshipping(String code, Product product, Status statusCodebyId){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(code)) {
                List<Product> buffer_list = new ArrayList<Product>();
                buffer_list.add(product);
                orderList.get(i).setProducts(buffer_list);
                orderList.get(i).setStatusCode(statusCodebyId);
                orderList.get(i).setReship(true);
                orderList.get(i).calFee();
                return new CommonResponse(new Result("200","success",true));
            }
        }
        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can not be found",false));
    }

    public Status getStatusCodebyId(Integer Id){
        Status status = statusRepository.findById(Id).orElseThrow(() -> new ApiRequestException("(CODE: 400) Order with id " + Id + " does not exists"));
        return status;
    }
    public void addNewShipping(Fee fee){
        feeRepository.save(fee);
        System.out.println(fee);
    }
    public void addNewSorder(ShippingOrder shippingOrder){
        shippingOrderRepository.save(shippingOrder);
        System.out.println(shippingOrder);
    }


    @Transactional
    public CommonResponse updateOrderStatus(String code, Status statuscode){
        List<ShippingOrder> orderList = shippingOrderRepository.findAll();
        for(int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(code)) {
                if(statuscode.getId() == 2){ //redeliver
                    if(orderList.get(i).getStatusCode().getId() ==  3 || orderList.get(i).getStatusCode().getId() == 5){
                        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can't not be re-shipping",false));
                    }
                }
                if(statuscode.getId() == 3){ //received
                    if(orderList.get(i).getStatusCode().getId() ==  4 || orderList.get(i).getStatusCode().getId() == 5){
                        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " have been cancel or return",false));
                    }
                }
                if(statuscode.getId() == 4){ //cancel
                    if(orderList.get(i).getStatusCode().getId() ==  5 || orderList.get(i).getStatusCode().getId() == 3){
                        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " have been return or already received",false));
                    }
                }
                if(statuscode.getId() == 5){ //return
                    if(orderList.get(i).getStatusCode().getId() !=  3 ){
                        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " have not been received yet to return",false));
                    }
                }
                orderList.get(i).setUpdateAt(Instant.now());
                orderList.get(i).setStatusCode(statuscode);
                return new CommonResponse(new Result("200","success",true));
            }
        }
        return new CommonResponse(new Result("400","fail: Shipping order with code " + code + " can not be found",false));
    }
}
