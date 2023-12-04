package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUser() {
        // Arrange
        Key userId = KeyType.USER.randomKey(); // Replace with a valid user ID
        UserDto mockUserDto = new UserDto("user_id", "username", "lastname", "firstname", "email", "password", null);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("user/{id}", userId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserDto.class)).thenReturn(Mono.just(mockUserDto));

        // Act
        Mono<UserDto> result = userService.getUser(userId);

        // Assert
        UserDto userDto = result.block(); // block to get the result from the Mono
        assertNotNull(userDto);
        assertEquals(mockUserDto, userDto);
    }
}