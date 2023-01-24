package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {

    private LocalDateTime start;
    private LocalDateTime end;

    public Duration duration() {
        return Duration.between(this.start, this.end);
    }

    public static boolean isValid(DateRange range) {
        return range.getStart().isBefore(range.getEnd());
    }

    public boolean dateIsBefore(LocalDateTime other) {
        return other.isBefore(this.start);
    }

    public boolean dateIsAfter(LocalDateTime other) {
        return other.isAfter(this.end);
    }

    public boolean dateIsDuring(LocalDateTime other) {
        return other.isAfter(this.start) && other.isBefore(this.end);
    }

    public boolean rangeIsDuring(DateRange other) {
        return other.start.isAfter(this.start) && other.end.isBefore(this.end);
    }
}
