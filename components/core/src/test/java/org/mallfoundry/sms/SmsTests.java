/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
                        .countryCode("86")
                        .phone("15688477267")
                        .variable(CODE_VARIABLE_NAME, "123456")
                        .build());
    }
}
