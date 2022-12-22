package com.bloodbank.bloodbankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentPreviewDto {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String bloodBankName;

    private Long bloodBankId;
}
