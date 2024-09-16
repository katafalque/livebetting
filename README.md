# Summary

* This spring boot 3.3 project implemented with Java 17

# How to use

* [Swagger ui](localhost:8080/swagger-ui/index.html)
* Or postman file can be found in repo

# APIs

1. Signup POST (/api/auth/signup)

- Request payload

```
  {
  "first_name": "john",
  "last_name": "doe",
  "userName": "doe123",
  "password": "password"
  }
```

2. Login POST (/api/auth/login)

- Payload

```
{
    "username": "doe123",
    "password": "password"
}
```

- Response

```
  {
  "token": "Bearer dummyjwttoken"
  }
```

- Token in the response should be used as Authorization header in below requests

3. Add match to app POST (/api/event/add)

- Payload

```
  {
  "league": "league-name",
  "home_team": "home-team-name",
  "away_team": "away-team-name",
  "start_time": "start-time-of-event"
  }
```

- Response simply will be OK with Http Status 200 if successful

4. Get matches in the system GET (/api/event/)

- No payload required for this
- Response will be something like this

```
  {
    "bulletin": [
        {
            "league": "league-name",
            "eventName": "home team name - away team name",
            "startTime": "event start time",
            "markets": [
                {
                    "homeTeamOdd": odd for home team victory,
                    "drawOdd": odd for draw,
                    "awayTeamOdd": odd for away team victory,
                    "createdAt": market creation time
                }
            ]
        }
    ]
  }
```

- Application will create new markets for each event every second.

5. Place bet to system POST (/api/bet/place)

- Payload

```
 {
    "bet_amount": total money to place on one coupon,
    "multi_coupon_count": total number of coupons to place,
    "events": [
        {
            "event_id": event id from h2 db,
            "selection": "home" // either "home", "away" or "draw"
        }
    ]
}
```

- Response simply will be Ok if successful. Cannot place more than 500 coupons at a time.
- Event id should be get from h2 db which can be accessed via [h2-console](http://localhost:8080/h2-console/)
- Db username = dbc:h2:mem:testdb
- Db password = password

6. See your bets GET (/api/bet)

- No payload
- Response

```
{
    "coupons": [
        {
            "total_odd": multiplication of the odds in the tickets,
            "potential_winnings": potential winning if the all bets win in the coupon,
            "tickets": [ // tickets means matches in the coupon.
                {
                    "event_name": home team vs away team,
                    "selection": "home", // can be home, draw or away
                    "odd": odd of the selected bet.
                }
            ]
        }
    ]
}
```

- a user can have more than one coupons and coupons can have more than one matches


