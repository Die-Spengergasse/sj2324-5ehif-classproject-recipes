package at.spengergasse.cooking.recipes.service.user;

import java.util.List;

public record UserDto(
        String key,
        String username,
        String lastname,
        String firstname,
        String email,
        String password,
        List<PreferenceDto> preferences
) {

}
