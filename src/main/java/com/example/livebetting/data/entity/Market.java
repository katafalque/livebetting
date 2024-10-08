package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Event event;

    public double getOddByName(String selection) {
        return switch (selection) {
            case "home" -> homeTeam;
            case "draw" -> draw;
            case "away" -> awayTeam;
            default -> 1;
        };
    }
}
