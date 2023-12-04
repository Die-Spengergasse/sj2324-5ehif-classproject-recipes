package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CachedUser {
    public Key userKey;
    public String username;

    public CachedUser(Key userKey, String username) {
        this.userKey = userKey.ensureValid(KeyType.USER);
        this.username = username;
    }

    public CachedUser(UserDto userDto) {
        this(KeyType.parse(userDto.key()), userDto.username());
    }
}
