package com.bloodbank.bloodbankapp.dto;

import com.bloodbank.bloodbankapp.model.DateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {

    @NotBlank
    DateRange dateRange;

}
