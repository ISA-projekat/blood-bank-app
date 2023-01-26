package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.ChangePasswordDTO;
import com.bloodbank.bloodbankapp.dto.RegistrationDto;
import com.bloodbank.bloodbankapp.dto.UserDto;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.UserService;
import com.bloodbank.bloodbankapp.utils.MailJetMailer;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
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
    @PreAuthorize("hasRole('SYS_ADMIN') or hasRole('REGISTERED') or hasRole('BLOOD_BANK_ADMIN')")
    public User getByUser(@PathVariable("id") Long id) {
        return userService.getByUser(id);
    }

    @CrossOrigin
    @PostMapping("registerAdmin")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public User registerAdmin(@Valid @RequestBody RegistrationDto dto) {
        return userService.registerAdmin(dto);
    }


    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasRole('SYS_ADMIN') or hasRole('BLOOD_BANK_ADMIN')")
    public List<User> getAll() {
        return userService.getAll();
    }


    @CrossOrigin
    @GetMapping("/search")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public List<User> search(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName) {
        return userService.search(firstName, lastName);
    }

    @CrossOrigin
    @GetMapping("/availableAdministrators")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public List<User> getAvailableAdministrators() {
        return userService.getAvailableAdministrators();
    }

    @PostMapping("register")
    public User registerUser(@Valid @RequestBody RegistrationDto dto) {

        try {
            User user = userService.add(dto);
            MailJetMailer.SendActivateAccountMail(dto.getEmail());
            return user;
        } catch (MailjetException e) {
            return null;
        }
    }

    @PutMapping("/activate")
    @CrossOrigin
    public User activateAccount(@RequestBody String email) {
        return userService.activate(email);
    }

    @GetMapping("/check-if-first-login-completed/{email}")
    @CrossOrigin
    public Boolean CheckIfFirstLoginCompleted(@PathVariable("email") String email) {
        return userService.IsFirstTimeLoginCompleted(email);
    }

    @CrossOrigin
    @PostMapping("/admin/change-password")
    public Boolean ChangeAdminPassword(@RequestBody ChangePasswordDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new UserException("Passwords are not matching");
        }
        return userService.ChangeAdminPassword(dto);

    }

    @CrossOrigin
    @GetMapping("/donators/{bloodBankId}")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN')")
    public List<UserDto> getAlDonators(@PathVariable("bloodBankId") Long bloodBankId) {
        return userService.getAllDonators(bloodBankId);
    }

}
