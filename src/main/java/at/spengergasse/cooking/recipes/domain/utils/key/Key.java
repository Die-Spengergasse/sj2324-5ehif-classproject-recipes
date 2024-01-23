package at.spengergasse.cooking.recipes.domain.utils.key;

import java.util.Random;

/**
 * Class representing an interchangeable key in a format shared by all microservices:
 * PREFIX{RANDOM_PART}{CHECK_DIGIT}
 *
 * This class allows representation of any key. Be sure to validate using {@link this#ensureValid(KeyType)}.
 */
public record Key(String prefix, String value, String checkDigits) { // TODO: Potentially use type system for key concretisation.

    @Override
    public String toString() {
        return this.prefix + this.value + this.checkDigits;
    }

    /**
     * Ensures that the {@link Key} is valid.
     * @throws IllegalStateException   if not valid
     * @return this
     */
    public Key ensureValid(KeyType expected) throws IllegalStateException {
        if(!this.isValid(expected)) {
            throw new IllegalStateException("Key is invalid.");
        }

        return this;
    }

    /**
     * @return true if the {@link Key} is valid.
     */
    private boolean isValid(KeyType expected) {
        return this.prefix.equals(expected.getPrefix()) &&
                this.value.length() == expected.getLength() &&
                KeyType.MOD_37_2.verify(this.toString());
    }
}