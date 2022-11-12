package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import com.bloodbank.bloodbankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public void edit(@RequestBody User user) {
        userService.edit(user);
    }

    @GetMapping("/{id}")
    public User getByUser(@PathVariable("id") Long id) {
        return userService.getByUser(id);
    }
}
