package org.mallfoundry.identity;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class UserExceptions {

    public static UserException notExists(String user) {
        return new UserException(
                message("identity.user.notExists",
                        String.format("User %s not exists", user)));
    }
}
