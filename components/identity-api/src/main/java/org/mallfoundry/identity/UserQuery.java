package org.mallfoundry.identity;

import java.util.List;

public interface UserQuery {

    List<String> getUsernames();

    List<String> getMobiles();
}
