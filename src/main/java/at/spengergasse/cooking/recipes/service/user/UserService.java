package at.spengergasse.cooking.recipes.service.user;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@AllArgsConstructor
@Service
public class UserService {
    @Value("${recipe.user.endpoint}")
    public static String ENDPOINT;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient apiClient;

    public UserService() {
        this.apiClient = WebClient.create(UserService.ENDPOINT);
    }

    /**
     * @param id
     * @return the {@link UserDto} with the given id.
     */
    public Mono<UserDto> getUser(Key id) {
        return this.apiClient
                .get()
                .uri("user/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
