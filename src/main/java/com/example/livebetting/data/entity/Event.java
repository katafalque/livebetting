package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Market> markets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<Ticket> tickets = new LinkedHashSet<>();

    @OneToOne(mappedBy = "event")
    private BulletinEvent bulletinEvent;

    public void addMarket(Market market) {
        markets.add(market);
        market.setEvent(this);
    }
}
