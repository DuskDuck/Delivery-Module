package com.hedspi.ltct.delivery.service;
import com.hedspi.ltct.delivery.model.Status;
import com.hedspi.ltct.delivery.repository.StatusRepository;
import com.hedspi.ltct.delivery.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    public CommonResponse getAllStatus() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<Status> statusList = statusRepository.findAll();
            if (statusList == null)
                return commonResponse.result("400","Yêu cầu không hợp lệ!",false);

            return commonResponse.data(statusList).result("200","Thành công!",true);
        } catch (Exception e) {
            return commonResponse.result("500","Có lỗi server!",false);
        }
    }
}
