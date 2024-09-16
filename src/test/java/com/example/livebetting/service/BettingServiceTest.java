package com.example.livebetting.service;

import com.example.livebetting.data.entity.*;
import com.example.livebetting.data.model.dto.BetModelDto;
import com.example.livebetting.data.model.request.PlaceBetRequestModel;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.data.repository.MarketRepository;
import com.example.livebetting.data.repository.UserRepository;
import com.example.livebetting.service.impl.BettingServiceImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BettingServiceTest {
    private static Faker faker;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MarketRepository marketRepository;
    @Mock
    private EventRepository eventRepository;
    private BettingServiceImpl bettingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        bettingService = new BettingServiceImpl(userRepository, marketRepository, eventRepository);
    }

    @Test
    void should_place_bet() {
        /* Arrange */
        String username = faker.name().username();
        OffsetDateTime betPlacingTime = OffsetDateTime.now().withNano(0);

        User user = User.builder().userName(username).coupons(new LinkedHashSet<>()).build();
        Event event = Event.builder().build();
        Market market = Market.builder().build();

        PlaceBetRequestModel request = PlaceBetRequestModel.builder()
                .betAmount(faker.random().nextInt(1000))
                .multiCouponCount(faker.random().nextInt(10))
                .build();

        BetModelDto betModelDto = BetModelDto.builder()
                .eventId(UUID.randomUUID())
                .selection(faker.lorem().word())
                .build();

        List<BetModelDto> betModelDtos = new ArrayList<>();

        betModelDtos.add(betModelDto);

        request.setEvents(betModelDtos);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(marketRepository.findByEventIdAndCreatedAt(betModelDto.getEventId(), betPlacingTime))
                .thenReturn(Optional.of(market));
        when(eventRepository.findById(betModelDto.getEventId())).thenReturn(Optional.of(event));

        /* Act */
        bettingService.placeBet(request, username, betPlacingTime);

        /* Assert */
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void should_return_user_coupons() {
        /* Arrange */
        User user = User.builder()
                .id(UUID.randomUUID())
                .userName(faker.name().username())
                .coupons(new LinkedHashSet<>())
                .build();

        Coupon coupon = Coupon.builder()
                .amount(faker.random().nextInt(1000))
                .couponCount(faker.random().nextInt(10))
                .tickets(new LinkedHashSet<>())
                .id(UUID.randomUUID())
                .build();

        Ticket ticket = Ticket.builder()
                .odd(faker.random().nextDouble())
                .id(UUID.randomUUID())
                .selection(faker.lorem().word())
                .build();

        Event event = Event.builder()
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .league(faker.esports().league())
                .id(UUID.randomUUID())
                .build();

        ticket.setEvent(event);
        coupon.addTickets(Set.of(ticket));
        user.addCoupon(coupon);

        /* Act */
        when(userRepository.findByUserName(user.getUsername())).thenReturn(Optional.of(user));
        var result = bettingService.getUserCoupons(user.getUsername());

        /* Assert */
        assertThat(result).isNotNull();
        assertThat(result.getCoupons()).hasSize(user.getCoupons().size());
        var couponDto = result.getCoupons().iterator().next();
        assertThat(couponDto.getOdd()).isEqualTo(getTotalOdds(coupon.getTickets()));
        assertThat(couponDto.getPotentialWinnings())
                .isEqualTo(
                        getTotalOdds(coupon.getTickets()) * coupon.getCouponCount() * coupon.getAmount()
                );
        assertThat(couponDto.getTickets()).hasSize(coupon.getTickets().size());
        var ticketDto = couponDto.getTickets().iterator().next();
        assertThat(ticketDto.getEventName()).isEqualTo(event.getHomeTeam() + " - " + event.getAwayTeam());
        assertThat(ticketDto.getSelection()).isEqualTo(ticket.getSelection());

    }

    private double getTotalOdds(Set<Ticket> tickets) {
        return tickets.stream().map(Ticket::getOdd).reduce((double) 1, (a, b) -> a * b);
    }
}
