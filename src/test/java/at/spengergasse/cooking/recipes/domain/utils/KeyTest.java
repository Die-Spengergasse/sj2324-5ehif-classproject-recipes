package at.spengergasse.cooking.recipes.domain.utils;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // todo: this right?
public class KeyTest {

    @Test
    public void generateKeyCheckValid() {
        final var key = KeyType.RECIPE.randomKey();

        key.ensureValid(KeyType.RECIPE);
    }

    @Test
    public void generateKeys(){
        for(int i=0; i<10; i++){
            Key key = KeyType.USER.randomKey();
            System.out.println(key);
        }
    }
    @Test
    public void parseKey() {
        final var key = KeyType.RECIPE.randomKey().toString();
        assertThat(key).isEqualTo(KeyType.parse(key).toString());
    }
}
