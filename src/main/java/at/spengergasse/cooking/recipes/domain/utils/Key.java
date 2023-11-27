package at.spengergasse.cooking.recipes.domain.utils;

import java.util.Random;

/**
 * Class representing an interchangeable key in a format shared by all microservices:
 * PREFIX{RANDOM_PART}{CHECK_DIGIT}
 */
public record Key(String prefix, String value, String checkDigits) {
    private static final Random RANDOM = new Random();
    private static final Mod37_2 MOD_37_2 = new Mod37_2();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String RECIPE_PREFIX = "REC";
    public static final int RECIPE_LENGTH = 15;

    @Override
    public String toString() {
        return this.prefix + this.value + this.checkDigits;
    }

    /**
     * Ensures that the {@link Key} is valid.
     * @throws IllegalStateException   if not valid
     * @return this
     */
    public Key ensureValid() throws IllegalStateException {
        if(!this.isValid()) {
            throw new IllegalStateException("Key is invalid.");
        }

        return this;
    }

    /**
     * @return true if the {@link Key} is valid.
     */
    public boolean isValid() {
        return Key.MOD_37_2.verify(this.toString());
    }

    /**
     * Generates the random part of a key with given length and charset {@link Key#CHARACTERS}.
     * @return the generated random part.
     */
    private static String generateRandomPart(int length) {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < length ; i++) {
            builder.append(Key.CHARACTERS.charAt(Key.RANDOM.nextInt(Key.CHARACTERS.length())));
        }

        return builder.toString();
    }

    /**
     * @param prefix    The prefix used for the digits.
     * @param value     The value used for digits.
     * @return the random check-digits for the given prefix and value.
     */
    private static String generateCheckDigits(String prefix, String value) {
        return Key.MOD_37_2.compute(prefix + value);
    }

    /**
     * Generates a random key with the given prefix.
     * @return the generated key.
     */
    public static Key randomKey(String prefix, int length) {
        final String value = Key.generateRandomPart(length);
        final String checkDigits = Key.generateCheckDigits(prefix, value);

        return new Key(prefix, value, checkDigits);
    }
}