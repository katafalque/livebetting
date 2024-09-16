package com.example.livebetting.service.impl;

import com.example.livebetting.data.entity.*;
import com.example.livebetting.data.model.dto.BetModelDto;
import com.example.livebetting.data.model.dto.CouponDto;
import com.example.livebetting.data.model.dto.TicketDto;
import com.example.livebetting.data.model.request.PlaceBetRequestModel;
import com.example.livebetting.data.model.response.GetUserCouponsResponseModel;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.data.repository.MarketRepository;
import com.example.livebetting.data.repository.UserRepository;
import com.example.livebetting.service.BettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class BettingServiceImpl implements BettingService {
    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final EventRepository eventRepository;

    @Autowired
    public BettingServiceImpl(UserRepository userRepository, MarketRepository marketRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.marketRepository = marketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void placeBet(PlaceBetRequestModel placeBetRequestModel,
                         String username,
                         OffsetDateTime betPlacingTime) {
        User user = userRepository.findByUserName(username).orElseThrow();

        Set<Ticket> tickets = getTickets(placeBetRequestModel.getEvents(), betPlacingTime);
        Coupon coupon = Coupon.builder()
                .couponCount(placeBetRequestModel.getMultiCouponCount())
                .amount(placeBetRequestModel.getBetAmount())
                .build();

        coupon.addTickets(tickets);
        user.addCoupon(coupon);
        userRepository.save(user);

    }

    @Override
    public GetUserCouponsResponseModel getUserCoupons(String username) {
        User user = userRepository.findByUserName(username).orElseThrow();
        Set<Coupon> coupons = user.getCoupons();
        return GetUserCouponsResponseModel.builder()
                .coupons(getCouponDtos(coupons))
                .build();
    }

    private Set<Ticket> getTickets(List<BetModelDto> bets, OffsetDateTime betPlacingTime) {
        Set<Ticket> tickets = new LinkedHashSet<>();
        for (BetModelDto bet : bets) {
            Market market = marketRepository.findByEventIdAndCreatedAt(bet.getEventId(), betPlacingTime).orElseThrow();
            Event event = eventRepository.findById(bet.getEventId()).orElseThrow();
            tickets.add(Ticket.builder()
                    .createdAt(OffsetDateTime.now())
                    .selection(bet.getSelection())
                    .odd(market.getOddByName(bet.getSelection()))
                    .event(event)
                    .build());

        }
        return tickets;
    }

    private double getTotalOdds(Set<Ticket> tickets) {
        return tickets.stream().map(Ticket::getOdd).reduce((double) 1, (a, b) -> a * b);
    }

    private List<CouponDto> getCouponDtos(Set<Coupon> coupons) {
        List<CouponDto> couponDtos = new ArrayList<>();
        for (Coupon coupon : coupons) {
            double totalOdds = getTotalOdds(coupon.getTickets());
            couponDtos.add(
                    CouponDto.builder()
                            .odd(totalOdds)
                            .potentialWinnings(totalOdds * coupon.getCouponCount() * coupon.getAmount())
                            .tickets(getTicketDtos(coupon.getTickets()))
                            .build()
            );
        }
        return couponDtos;
    }

    private List<TicketDto> getTicketDtos(Set<Ticket> tickets) {
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Event event = ticket.getEvent();
            ticketDtos.add(
                    TicketDto.builder()
                            .eventName(event.getHomeTeam() + " - " + event.getAwayTeam())
                            .odd(ticket.getOdd())
                            .selection(ticket.getSelection())
                            .build()
            );
        }
        return ticketDtos;
    }
}
