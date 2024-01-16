package at.spengergasse.cooking.recipes.domain.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // todo: this right?
public class KeyTest {

    @Test
    public void generateKeyCheckValid() {
        final var key = Key.randomKey(Key.RECIPE_PREFIX, Key.RECIPE_LENGTH);

        key.ensureValid();
    }
}
