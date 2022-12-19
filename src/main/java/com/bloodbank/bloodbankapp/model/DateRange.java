package com.bloodbank.bloodbankapp.model;

import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;

@Value
public class DateRange {
    private LocalDateTime Start;
    private LocalDateTime End;

    public Duration duration() {
        return Duration.between(this.Start, this.End);
    }

}
