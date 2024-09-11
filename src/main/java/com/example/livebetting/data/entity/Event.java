package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "league")
    private String league;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @OneToOne(mappedBy = "event", cascade = CascadeType.PERSIST)
    private Market market;

    @OneToMany(mappedBy = "event")
    private final Set<Ticket> tickets = new LinkedHashSet<>();

    @OneToOne(mappedBy = "event")
    private BulletinEvent bulletinEvent;
}
