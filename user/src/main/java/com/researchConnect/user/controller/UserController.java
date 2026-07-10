package com.researchConnect.user.controller;


import com.researchConnect.user.config.JwtService;
import com.researchConnect.user.dto.AuthResponseDto;
import com.researchConnect.user.dto.LoginRequestDto;
import com.researchConnect.user.dto.UserRegistrationDto;
import com.researchConnect.user.model.User;
import com.researchConnect.user.repository.UserRepository;
import com.researchConnect.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDto userRegistrationDto){
        User user = userService.userRegistrationService(userRegistrationDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){
        String token = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

}
