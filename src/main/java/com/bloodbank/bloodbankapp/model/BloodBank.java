package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blood_bank")
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double rating;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long equipmentSets;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany
    @JoinColumn(name = "blood_bank_id")
    private List<User> administrators;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id")
    private List<BloodStock> bloodStocks;

    public void updateAddress(Address newAddress) {
        address.setCountry(newAddress.getCountry());
        address.setCity(newAddress.getCity());
        address.setStreet(newAddress.getStreet());
        address.setNumber(newAddress.getNumber());
    }

    public void updateBloodStock(BloodStock bloodStock) {
        if (bloodStocks == null) {
            bloodStocks = new ArrayList<>();
        }
        if (bloodStocks.stream().noneMatch(bs -> bs.getType().equals(bloodStock.getType()) && bs.getRhFactor().equals(bloodStock.getRhFactor()))) {
            bloodStock.setBloodBankId(id);
            bloodStocks.add(bloodStock);
            return;
        }
        for (var bs : bloodStocks) {
            if (bs.getType().equals(bloodStock.getType()) && bs.getRhFactor().equals(bloodStock.getRhFactor())) {
                bs.setQuantity(bs.getQuantity() + bloodStock.getQuantity());
            }
        }
    }
}
