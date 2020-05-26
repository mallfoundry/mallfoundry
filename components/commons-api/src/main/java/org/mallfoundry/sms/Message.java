package org.mallfoundry.sms;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.util.Map;

public interface Message extends Serializable {

    String getMobile();

    void setMobile(String mobile);

    String getBody();

    void setBody(String body);

    Map<String, String> getParameters();

    void setParameter(String name, String value);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Message> {

        Builder mobile(String mobile);

        Builder body(String body);

        Builder parameter(String name, String value);

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
        public Builder body(String body) {
            this.message.setBody(body);
            return this;
        }

        @Override
        public Builder parameter(String name, String value) {
            this.message.setParameter(name, value);
            return this;
        }

        @Override
        public Message build() {
            return this.message;
        }
    }
}
