package at.spengergasse.cooking.recipes.service.user;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@FeignClient(value = "users", url = "${recipe.user.endpoint}")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public UserDto getUser(@PathVariable("id") Key id);
}
