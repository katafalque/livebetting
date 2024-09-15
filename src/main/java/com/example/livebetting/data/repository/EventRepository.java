package com.example.livebetting.data.repository;

import com.example.livebetting.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("SELECT e.id FROM Event e")
    Set<UUID> findAllIds();

    Set<Event> findByStartTimeAfter(OffsetDateTime offsetDateTime);
}
