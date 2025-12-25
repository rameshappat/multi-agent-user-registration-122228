package com.northerntrust.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OtpService otpService;

    @Autowired
    public UserService(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Transactional
    public void registerUser(UserRegistrationRequest request) {
        // Save user to database
        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
        userRepository.save(user);

        // Generate and send OTP
        String otp = otpService.generateOtp();
        otpService.sendOtp(request.getEmail(), otp);
    }
}