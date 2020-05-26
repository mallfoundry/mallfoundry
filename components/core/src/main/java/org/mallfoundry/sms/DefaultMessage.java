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

    private String template;

    private String signature;

    private String mobile;

    private String body;

    private Map<String, String> variables = new HashMap<>();

    @Override
    public void setVariable(String name, String value) {
        this.variables.put(name, value);
    }
}
