package at.spengergasse.cooking.recipes.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

public class Comment {
    public User author;
    public String comment;
    public Optional<Comment> parentComment;
    public ZonedDateTime creationTS;
}
