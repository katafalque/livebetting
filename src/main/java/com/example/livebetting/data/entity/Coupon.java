package com.example.livebetting.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "coupon_count")
    private int couponCount;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new LinkedHashSet<>();

    public void addTickets(Set<Ticket> ticketList) {
        if (tickets == null) {
            tickets = new LinkedHashSet<>();
        }
        tickets.addAll(ticketList);
        for (Ticket t : ticketList) {
            t.setCoupon(this);
        }
    }
}
