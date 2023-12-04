package at.spengergasse.cooking.recipes.domain.utils;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // todo: this right?
public class KeyTest {

    @Test
    public void generateKeyCheckValid() {
        final var key = KeyType.RECIPE.randomKey();

        key.ensureValid(KeyType.RECIPE);
    }
}
