package org.mallfoundry.sms;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.util.Map;

public interface Message extends Serializable {

    String CODE_VARIABLE_NAME = "code";

    String getTemplate();

    void setTemplate(String template);

    String getSignature();

    void setSignature(String signature);

    String getMobile();

    void setMobile(String mobile);

    String getBody();

    void setBody(String body);

    Map<String, String> getVariables();

    void setVariable(String name, String value);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Message> {

        Builder mobile(String mobile);

        Builder template(String template);

        Builder signature(String signature);

        Builder body(String body);

        Builder variable(String name, String value);

    }

    abstract class BuilderSupport implements Builder {

        private final Message message;

        public BuilderSupport(Message message) {
            this.message = message;
        }

        @Override
        public Builder mobile(String mobile) {
            this.message.setMobile(mobile);
            return this;
        }

        @Override
        public Builder template(String template) {
            this.message.setTemplate(template);
            return this;
        }

        @Override
        public Builder signature(String signature) {
            this.message.setSignature(signature);
            return this;
        }

        @Override
        public Builder body(String body) {
            this.message.setBody(body);
            return this;
        }

        @Override
        public Builder variable(String name, String value) {
            this.message.setVariable(name, value);
            return this;
        }

        @Override
        public Message build() {
            return this.message;
        }
    }
}
