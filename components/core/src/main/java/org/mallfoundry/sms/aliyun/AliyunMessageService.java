/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
