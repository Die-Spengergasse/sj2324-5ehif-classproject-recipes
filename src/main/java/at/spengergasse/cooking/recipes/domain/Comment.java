package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import java.time.ZonedDateTime;

@AllArgsConstructor
public class Comment {
    public User author;
    public String comment;
    public Comment parentComment;
    public ZonedDateTime creationTS;
}
