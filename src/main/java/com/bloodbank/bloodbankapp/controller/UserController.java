package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.RegistrationDto;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import com.bloodbank.bloodbankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public void edit(@RequestBody User user) {
        userService.edit(user);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public User getByUser(@PathVariable("id") Long id) {
        return userService.getByUser(id);
    }

    @PostMapping("register")
    public User registerUser(@Valid @RequestBody RegistrationDto dto) { return userService.add(dto); }

    @CrossOrigin
    @GetMapping
    public List<User> getAll() { return userService.getAll(); }


    @GetMapping("/search")
    public List<User> search(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName) {
        return userService.search(firstName, lastName);
    }


}
