package com.example.livebetting.data.repository;

import com.example.livebetting.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    
}
