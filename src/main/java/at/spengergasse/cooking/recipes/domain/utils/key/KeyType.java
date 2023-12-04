package at.spengergasse.cooking.recipes.domain.utils.key;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.IllegalFormatException;
import java.util.Random;

@Getter
@AllArgsConstructor
public enum KeyType {
    USER("USR", 10),
    INGREDIENT("ING", 10),
    RECIPE("REC", 10);

    private static final Random RANDOM = new Random();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    protected static final Mod37_2 MOD_37_2 = new Mod37_2();

    private final String prefix;
    private final int length;

    /**
     * Generates a random {@link Key} of this type.
     * @return the generated {@link Key}.
     */
    public Key randomKey() {
        final String value = KeyType.generateRandomPart(this.getLength());

        return new Key(this.getPrefix(), value, KeyType.generateCheckDigits(this.getPrefix(), value));
    }

    /**
     * @param prefix    The prefix used for the digits.
     * @param value     The value used for digits.
     * @return the random check-digits for the given prefix and value.
     */
    private static String generateCheckDigits(String prefix, String value) {
        return KeyType.MOD_37_2.compute(prefix + value);
    }

    /**
     * Generates the random part of a key with given length and charset {@link KeyType#CHARACTERS}.
     * @return the generated random part.
     */
    private static String generateRandomPart(int length) {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < length ; i++) {
            builder.append(KeyType.CHARACTERS.charAt(KeyType.RANDOM.nextInt(KeyType.CHARACTERS.length())));
        }

        return builder.toString();
    }

    /**
     * @param key                       the raw key.
     * @return the parsed key.
     * @throws IllegalFormatException   if the given string valid.
     */
    public static Key parse(String key) throws IllegalArgumentException {
        for(KeyType type : KeyType.values()) {
            if(key.startsWith(type.getPrefix())) {
                final String value = key.substring(type.getPrefix().length(), key.length() - 1);

                if(type.getLength() != value.length()) {
                    throw new IllegalArgumentException("Key value length mismatch");
                }

                return new Key(type.getPrefix(), value, key.substring(key.length() - 1));
            }
        }
        throw new IllegalArgumentException("Unknown key prefix");
    }
}
