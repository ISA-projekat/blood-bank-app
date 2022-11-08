package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.enums.Gender;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getByUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User doesn't exist"));
    }

    public void edit(User user) {
        if (user == null) throw new UserException("Invalid user passed for edit");
        var oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserException("User you want to edit doesn't exist"));
        updateUser(oldUser, user);
        userRepository.save(oldUser);
    }

    private void updateUser(User oldUser, User user) {
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setJmbg(user.getJmbg()); // should jmbg change be allowed?
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setOccupation(user.getOccupation());
        oldUser.setActive(user.getActive()); // should active status change be allowed?
        oldUser.setAddress(user.getAddress());
        oldUser.setGender(user.getGender());
    }
}
