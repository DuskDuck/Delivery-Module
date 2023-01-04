package com.hedspi.ltct.delivery.repository;


import com.hedspi.ltct.delivery.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
}
