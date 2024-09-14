package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "market")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "home_team")
    private double homeTeam;

    @Column(name = "away_team")
    private double awayTeam;

    @Column(name = "draw")
    private double draw;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Event event;
}
