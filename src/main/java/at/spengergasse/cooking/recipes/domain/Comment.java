package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private CachedUser author;
    private String comment;
    private Comment parentComment;
    private ZonedDateTime creationTS;
}
