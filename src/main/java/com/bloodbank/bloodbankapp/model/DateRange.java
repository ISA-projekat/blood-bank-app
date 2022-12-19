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

    public boolean dateIsBefore(LocalDateTime other) { return other.isBefore(this.Start); }

    public boolean dateIsAfter(LocalDateTime other) { return other.isAfter(this.End); }

    public boolean dateIsDuring(LocalDateTime other) { return other.isAfter(this.Start) && other.isBefore(this.End); }

    public boolean rangeIsDuring(DateRange other) { return other.Start.isAfter(this.Start) && other.End.isBefore(this.End); }
}
