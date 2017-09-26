package com.example.demo.web.rest;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.HeaderUtil;
import com.github.vaibhavsinha.kong.impl.KongClient;
import com.github.vaibhavsinha.kong.model.admin.consumer.Consumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by janier on 20/09/17.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private static final String ENTITY_NAME = "User";
    private final UserRepository userRepository;

    @Value("${kong.address}")
    private String kongAddress;

    public UserResource(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
    }

//    @PostMapping(value = "/default")
//    public ResponseEntity<User> createDefaultUser() {
//        KongClient kongClient = new KongClient(kongAddress);
//        Consumer request = new Consumer();
//        User testUser = new User();
//        testUser.setUsername("Admin2");
//        testUser.setPassword("pass");
//        request.setCustomId(String.valueOf(Math.random()));
//        request.setUsername(testUser.getUsername());
//        Consumer response = kongClient.getConsumerService().createConsumer(request);
//        testUser.setConsumerId(response.getCustomId());
//        userRepository.save(testUser);
//        return ResponseEntity.ok(testUser);
//    }

    @PostMapping(value = "/user")
    public ResponseEntity<User> processRegistrationForm(@RequestBody User user) throws URISyntaxException {
        // Search user in database by username
        User userExists = userRepository.findByUsername(user.getUsername());

        if (userExists != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The user already exists")).body(null);
        }

        //Create a new Consumer in Kong
        KongClient kongClient = new KongClient(kongAddress);
        Consumer request = new Consumer();
        request.setCustomId(String.valueOf(Math.random()));
        request.setUsername(user.getUsername());
        Consumer response = kongClient.getConsumerService().createConsumer(request);
        user.setConsumerId(response.getCustomId());
        userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + user.getId())).body(user);
    }

}
