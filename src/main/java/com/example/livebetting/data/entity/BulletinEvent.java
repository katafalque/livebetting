package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "bulletin_event")
public class BulletinEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bulletin bulletin;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Event event;
}
