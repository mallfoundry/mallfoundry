package org.mallfoundry.identity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class UserReplacer {

    private static final String EMPTY_STRING = "";

    private static final String[] SEARCHES = new String[]{"{id}", "{mobile}", "{email}"};

    public static String replace(String text, User user) {
        return StringUtils.replaceEachRepeatedly(text, SEARCHES, newReplacements(user));
    }

    private static String[] newReplacements(User user) {
        Assert.notNull(user.getId(), message("identity.user.emptyId", "User id must not be null"));
        var mobile = StringUtils.defaultIfEmpty(user.getMobile(), EMPTY_STRING);
        var email = StringUtils.defaultIfEmpty(user.getEmail(), EMPTY_STRING);
        return new String[]{user.getId(), mobile, email};
    }
}
