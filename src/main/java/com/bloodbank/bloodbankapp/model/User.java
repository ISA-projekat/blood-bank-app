package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.Gender;
import com.bloodbank.bloodbankapp.enums.Role;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String jmbg;

    private String phoneNumber;

    private String occupation;

    private Boolean active;

    private Integer penalties;

    private boolean isLoggedBefore;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(columnDefinition = "ENUM('SYS_ADMIN', 'BLOOD_BANK_ADMIN', 'REGISTERED', 'UNREGISTERED')")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "ENUM('MALE', 'FEMALE')")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "blood_bank_id")
    private Long bloodBankId;

    public void setAddressWithoutId(Address newAddress) {
        address.setCountry(newAddress.getCountry());
        address.setCity(newAddress.getCity());
        address.setStreet(newAddress.getStreet());
        address.setNumber(newAddress.getNumber());
    }
}
