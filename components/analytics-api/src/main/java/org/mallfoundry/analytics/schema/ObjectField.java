package org.mallfoundry.analytics.schema;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.io.Serializable;

public interface ObjectField extends Serializable, Position {

    String getName();

    void setName(String name);

    String getLabel();

    void setLabel(String label);

    String getType();

    void setType(String type);

    Builder toBuilder();

    interface Builder extends ObjectBuilder<ObjectField> {

        Builder name(String name);

        Builder label(String label);

        Builder type(String type);
    }
}
