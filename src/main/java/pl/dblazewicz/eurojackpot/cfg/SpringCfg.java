package pl.dblazewicz.eurojackpot.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class SpringCfg {
    @Bean
    public Clock clock() {
        String fixedDateTime = "2023-11-05T17:40:52.329911";
        LocalDateTime localDateTime = LocalDateTime.parse(fixedDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Clock.fixed(instant, ZoneId.systemDefault());
    }
}
