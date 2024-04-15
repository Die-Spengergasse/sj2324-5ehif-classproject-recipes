package at.spengergasse.cooking.recipes.service.user;

import at.spengergasse.cooking.recipes.domain.CachedUser;

public record UserDto(
        String key,
        String username
) {
    public UserDto(CachedUser domain) {
        this(domain.getKey() + "", domain.getUsername());
    }
}
