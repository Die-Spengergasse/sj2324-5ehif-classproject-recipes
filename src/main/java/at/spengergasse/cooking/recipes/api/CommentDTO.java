package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.service.user.UserDto;

import java.time.ZonedDateTime;

public record CommentDTO(
        UserDto userDto,
        String comment,
        CommentDTO commentDTO,
        ZonedDateTime creationTS

) {
}
