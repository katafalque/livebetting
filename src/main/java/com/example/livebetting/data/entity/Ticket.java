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
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "selection")
    private String selection;

    @Column(name = "odd")
    private float odd;

    @Column(name = "amount")
    private int amount;

    @Column(name = "coupon_count")
    private int couponCount;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private Coupon coupon;

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;
}
