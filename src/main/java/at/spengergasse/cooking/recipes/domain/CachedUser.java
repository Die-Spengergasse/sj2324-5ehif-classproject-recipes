package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Getter
public class CachedUser {
    private Key userKey;
    private String username;

    public CachedUser(Key userKey, String username) {
        this.userKey = userKey.ensureValid(KeyType.USER);
        this.username = username;
    }

    public CachedUser(UserDto userDto) {
        this(KeyType.parse(userDto.key()), userDto.username());
    }
}
