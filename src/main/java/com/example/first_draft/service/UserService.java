package com.example.first_draft.service;

import com.example.first_draft.entity.User;
import com.example.first_draft.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUserId(long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        if (user.getAddresses() != null) {
            user.getAddresses().forEach(address -> address.setUser(user));
        }



        return userRepository.save(user);
    }

    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

}
