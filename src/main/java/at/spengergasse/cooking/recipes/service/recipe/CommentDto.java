package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Comment;
import at.spengergasse.cooking.recipes.service.user.UserDto;

import java.time.ZonedDateTime;

public record CommentDto(
        UserDto userDto,
        String comment,
        CommentDto parent,
        ZonedDateTime creationTS

) {
    public CommentDto(Comment domain) {
        this(
                new UserDto(domain.getAuthor()),
                domain.getComment(),
                domain.getParentComment() != null ? new CommentDto((domain.getParentComment())) : null,
                domain.getCreationTS()
        );
    }
}
