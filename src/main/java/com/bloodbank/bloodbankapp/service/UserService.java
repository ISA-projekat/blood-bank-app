package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.RegistrationDto;
import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.mapper.UserMapper;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.repository.AddressRepository;
import com.bloodbank.bloodbankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public User getByUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User doesn't exist"));
    }

    public void edit(User user) {
        if (user==null) throw new UserException("Invalid user passed for edit");
        var oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserException("User you want to edit doesn't exist"));
        updateUser(oldUser, user);
        userRepository.save(oldUser);
    }

    private void updateUser(User oldUser, User user) {
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
//        oldUser.setJmbg(user.getJmbg()); // should jmbg change be allowed?
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setOccupation(user.getOccupation());
//        oldUser.setActive(user.getActive()); // should active status change be allowed?
        oldUser.setAddressWithoutId(user.getAddress());
        oldUser.setGender(user.getGender());
    }

    public User add(RegistrationDto dto) {
        var existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser != null) throw new UserException("User with that email already exists");

        User newUser = UserMapper.DtoToEntity(dto);

        addressRepository.save(newUser.getAddress());
        userRepository.save(newUser);
        return newUser;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> search(String firstName, String lastName){

        List<User> users = userRepository.search(firstName,lastName);
        if(users.isEmpty()) throw new UserException("List is empty :(");
        return users;
    }

}
