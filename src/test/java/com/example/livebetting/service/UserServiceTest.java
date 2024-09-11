package com.example.livebetting.service;


import com.example.livebetting.data.entity.User;
import com.example.livebetting.data.repository.UserRepository;
import com.example.livebetting.service.impl.UserServiceImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    private static Faker faker;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void should_save_user() {
        /* Arrange */
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        var username = faker.name().username();
        var password = faker.lorem().word();

        var user = User.builder()
                .userName(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        var userService = new UserServiceImpl(userRepository);
        
        /* Act */
        userService.saveUser(user);

        /* Assert */
        verify(userRepository, times(1)).save(user);
    }

}
