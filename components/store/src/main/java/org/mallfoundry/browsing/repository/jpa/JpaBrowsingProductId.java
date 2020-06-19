package org.mallfoundry.browsing.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class JpaBrowsingProductId implements Serializable {

    private String browserId;

    private String id;

    public JpaBrowsingProductId(String browserId, String id) {
        this.browserId = browserId;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaBrowsingProductId that = (JpaBrowsingProductId) o;
        return Objects.equals(browserId, that.browserId)
                && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(browserId, id);
    }
}
