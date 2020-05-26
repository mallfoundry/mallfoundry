package org.mallfoundry.sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DefaultMessage implements Message {

    private String body;

    private String mobile;

    private Map<String, String> parameters = new HashMap<>();

    @Override
    public void setParameter(String name, String value) {
        this.parameters.put(name, value);
    }
}
