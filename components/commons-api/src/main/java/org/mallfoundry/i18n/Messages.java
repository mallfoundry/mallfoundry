package org.mallfoundry.i18n;

import org.mallfoundry.Version;

/**
 * Simplify message code key that begins with the {@code org.mallfoundry} package name.
 *
 * @author Tang Zhi
 */
public abstract class Messages {

    /**
     * Get the message code key.
     *
     * @param codeKey the code key for short
     * @return the full code key
     * @throws IllegalArgumentException if null
     */
    public static String codeKey(String codeKey) throws IllegalArgumentException {
        if (codeKey == null || codeKey.isBlank()) {
            throw new IllegalArgumentException("Code must not be empty");
        }
        return String.format("%s.%s", Version.class.getPackageName(), codeKey);
    }
}
