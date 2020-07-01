package org.mallfoundry.analytics.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DefaultObjectField extends ObjectFieldSupport {

    private String name;

    private String label;

    private String type;

    private int position;

    public DefaultObjectField(String name) {
        this.name = name;
    }

    @Override
    public String getLabel() {
        return Objects.isNull(this.label) ? this.name : this.label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultObjectField)) {
            return false;
        }
        DefaultObjectField that = (DefaultObjectField) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
