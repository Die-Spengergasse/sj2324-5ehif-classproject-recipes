package at.spengergasse.cooking.recipes.domain;

import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
public class Comment {
    public User author;
    public String commentKey;
    public String parentComment;
    public ZonedDateTime creationTS;
}
