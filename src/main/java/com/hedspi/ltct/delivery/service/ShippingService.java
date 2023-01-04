package com.hedspi.ltct.delivery.service;

import com.hedspi.ltct.delivery.exception.ApiRequestException;
import com.hedspi.ltct.delivery.model.Fee;
import com.hedspi.ltct.delivery.model.ShippingOrder;
import com.hedspi.ltct.delivery.model.Status;
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

    public CommonResponse getShippingOrder() { return new CommonResponse(new Result("200","success",true),shippingOrderRepository.findAll()); }

    public Fee getFeebyId(Long Id) {
        Fee fee = feeRepository.findById(Id).orElseThrow(() -> new ApiRequestException("(CODE: 400) Shipment with id " + Id + " does not exists"));
        return fee;
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

    public void deleteStudent(Long Id){
        boolean exists = shippingOrderRepository.existsById(Id);
        if(!exists) {
            throw new ApiRequestException("(CODE: 400) Shipment with id " + Id + " does not exists");
        }
        shippingOrderRepository.deleteById(Id);
    }

    @Transactional
    public void cancelOrder(Long Id,Status statuscode){
        ShippingOrder shippingOrder = shippingOrderRepository.findById(Id).orElseThrow(() -> new IllegalStateException("(CODE: 400) Order with id " + Id + " does not exists"));
        shippingOrder.setStatusCode(statuscode);
    }
    @Transactional
    public void updateSorder(Long Id, Status statuscode, String detail){
        ShippingOrder shippingOrder = shippingOrderRepository.findById(Id).orElseThrow(() -> new IllegalStateException("(CODE: 400) Order with id " + Id + " does not exists"));
        shippingOrder.setStatusCode(statuscode);

        if(detail != null && detail.length() > 0 ){
            shippingOrder.setStatusDetail(detail);
        }
        shippingOrder.setUpdateAt(Instant.now());
    }
}
