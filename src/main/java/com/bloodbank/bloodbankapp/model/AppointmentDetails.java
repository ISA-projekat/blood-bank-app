//<<<<<<< Updated upstream
//package com.bloodbank.bloodbankapp.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class AppointmentDetails extends Entity{
//
//    private String description;
//}
//=======
//package com.bloodbank.bloodbankapp.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "appointment_details")
//public class AppointmentDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String description;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "appointment_id")
//    private Appointment appointment;
//}
