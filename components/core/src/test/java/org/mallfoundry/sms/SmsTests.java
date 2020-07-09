package org.mallfoundry.sms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mallfoundry.sms.Message.CODE_VARIABLE_NAME;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SmsTests {

    @Autowired
    private MessageService messageService;

    @Test
    public void testSendMessage() {

        this.messageService.sendMessage(
                this.messageService.createMessage()
                        .toBuilder()
                        .mobile("15688477267")
                        .variable(CODE_VARIABLE_NAME, "123456")
                        .build());
    }
}
