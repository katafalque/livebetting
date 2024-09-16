package com.example.livebetting.data.repository;

import com.example.livebetting.data.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface MarketRepository extends JpaRepository<Market, UUID> {
    Optional<Market> findByEventIdAndCreatedAt(UUID eventId, OffsetDateTime createdAt);
}
