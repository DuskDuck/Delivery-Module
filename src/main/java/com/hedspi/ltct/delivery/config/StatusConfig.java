package com.hedspi.ltct.delivery.config;

import com.hedspi.ltct.delivery.model.Status;
import com.hedspi.ltct.delivery.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StatusConfig {
    @Bean
    CommandLineRunner commandLineRunner(StatusRepository repository){
        return args -> {
            Status status1 = new Status(
                    1,
                    "chưa giao hàng"
            );
            Status status2 = new Status(
                    2,
                    "đang giao hàng"
            );
            Status status3 = new Status(
                    3,
                    "đã giao hàng"
            );
            Status status4 = new Status(
                    4,
                    "hủy giao hàng"
            );
            repository.saveAll(List.of(status1,status2,status3,status4));
        };
    }
}
