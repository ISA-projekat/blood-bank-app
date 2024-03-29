package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.RegistrationDto;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
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

    @CrossOrigin
    @PostMapping("registerAdmin")
    public User registerAdmin(@Valid @RequestBody RegistrationDto dto) { return userService.registerAdmin(dto); }


    @CrossOrigin
    @GetMapping
    public List<User> getAll() { return userService.getAll(); }


    @CrossOrigin
    @GetMapping("/search")
    public List<User> search(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName) {
        return userService.search(firstName, lastName);
    }

    @CrossOrigin
    @GetMapping ("/availableAdministrators")
    public List<User> getAvailableAdministrators(){
        return userService.getAvailableAdministrators();
    }

    @PostMapping("register")
    public User registerUser(@Valid @RequestBody RegistrationDto dto) {
        return userService.add(dto);
    }

}
