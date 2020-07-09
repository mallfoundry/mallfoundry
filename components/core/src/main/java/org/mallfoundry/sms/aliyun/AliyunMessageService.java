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

package org.mallfoundry.sms.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import org.mallfoundry.sms.AbstractMessageService;
import org.mallfoundry.sms.Message;
import org.mallfoundry.sms.MessageException;
import org.mallfoundry.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AliyunMessageService extends AbstractMessageService {

    private final IAcsClient client;

    public AliyunMessageService(IAcsClient client) {
        this.client = client;
    }

    @Override
    public void sendMessage(Message message) throws MessageException {

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", message.getMobile());

        if (Objects.nonNull(message.getTemplate())) {
            request.putQueryParameter("TemplateCode", message.getTemplate());
        }

        if (Objects.nonNull(message.getSignature())) {
            request.putQueryParameter("SignName", message.getSignature());
        }

        Map<String, String> parameterMap = new HashMap<>();

        if (message.getVariables().containsKey(Message.CODE_VARIABLE_NAME)) {
            parameterMap.put(Message.CODE_VARIABLE_NAME, message.getVariables().get(Message.CODE_VARIABLE_NAME));
        }

        if (!parameterMap.isEmpty()) {
            request.putQueryParameter("TemplateParam", JsonUtils.stringify(parameterMap));
        }

        try {
            CommonResponse response = this.client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            throw new MessageException(e);
        }
    }
}
