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

package com.mallfoundry.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;

public class AlipayTests {

    public static void main(String[] args) {

        String serverUrl = "https://openapi.alipaydev.com/gateway.do";
        String appId = "2016102100732034";
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKcr0myBWJLfGiN97h05dT7AjarRcbpJkGuz+58BrTqGBIM2yJzoUHh/J3RG1pvLF621RHz+zZd5ztvDQKJ12iQFwYdNYwiQod3qvYyWUM7XJpw6SSbSqfV+S5sJwhl0n6GgHLc6Gdyls5itdzUyt6JGulH7hj8QrwElXYt3ieoTQdC1jRCxQy9RxnXdTDG5fzFs9Cp6hCKsiKGRrm4l3jRaFpn6ty/7fucJ8zIV8E0BHj+mSi3HuOgNa5pc7MUaRZJBWAeckH1vEH3GTTTqGDqn9ujF3zeTj7YVMtf8B/eCTwSzpTvLHa8176flQq+5pJJkEvn/PWekn13EEOAe9dAgMBAAECggEAAhU710uHV2sDiadBiTA/xJl2gKO+B0JwsvM6QDfmj2ynFi7/4QrkYXqgIT88TizylqCaIgJ008jC6ai9N7Hj7L7/I7cOSeil6FEjUL2m8YzY/dJdtX5C9+bAoDKSBsdZ65Ncl40Dz3Xt7W2zVB7aNpo5rGBPy+Eigv4joIESJYDNnIOxpV9J14IfYnm0PxgCoKl4YM7WOa9s/1OKqL1Gs1zVNM/1KXL49gQnQVo4Om1PtFcqlBGjltfubbEaz3SzDC8H+aOtjxmrPrJuSxvJAY+eUMZ/jhNbzttzIhZeYC7iV0hTx4EeawZmv8nu8BX7gjv6pTE39DSCBVffKGcRmQKBgQDka36Rh2v6RZLKLl/0DDvpO1jTezxNlrYgdnX8V/enqwaEX6h8U8QtYrMBvtWSpRexp+OP9YOcrenznBiKJE723aP3/gi48EMWHr/S5nnwml456odPrN73zX1RMI3XuCPqCAhIMIYcFSzyoD8wFq9B+iO3P58BAIHkcQ7GG2HpEwKBgQCbKjNsI3jhHrlToAsw/ndhsPdaNr81JZMdwXPenXkjGbsSOfSYKyQRTJ6l5BNOivBsRLDQuLbye/m8yY0jRwjHN3SmbDP/Rf43PuAT3LaLLKQPaM5yWIov/MCzLeK68Isi+kMwfl8btPIZvT/9pfld9t32KSiif6bQAfl89B/DzwKBgHzL9iTQlj/wjxiDac1OedUdpZQ1umaxtuPZlPC/pwEmQbNFCCqVIZOiXybO+qTaKi3YCk521uddZXrBh+7Bk5rw0UsGsgFI035cYnV5kpnP3TdLWY5AZrmAvb0ECBsZ8hVFOjEmwb7KGvqcbql5W1tyrNzLl7XWbyE+W9IE1+OdAoGABULDB5GUUZ2nDuzINFsMVSWY3Vgp13V0rXeXpB6Wstf2fcG1R0t/J47/egRnmywexqz1ib4ZhdMqPnpe7KlF5Os+az4T7T3ERr88/a9oCQP0dAlLppjV5padOfX+T/RD42W0zXobY2cZJkJCXyk3cTYjhGsU052cXEUU3mWwhgUCgYBNC1ryYrsF0PzjKiNtqpCpdtNKN/1MgIon7hkek1LP4ffKye4a/TlVWyFCaNlNe0ARkdrcTxvkzE2IaFXvnO7IuTIm/127Pqh1arR+eANOuLvgF6UTJPMINx2TbZKsbrIeH8vkBBU+A7Qzgm9IzlfasKc7QKaEpU62e5xHSjBDtw==";
        String format = "json";
        String charset = "utf-8";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApAW4bkdyWlq6T8v1pcOANuDtpgGD6oVVWJQOVQtSSHDAY+VYBqTpAY7DWAeO83QRXT/FlbQ68njOWnJ05S9F504Ka5NVq+PnC3pkMdCPitmguqEbz85apKDZvv6fd3IGR9yvhhXOZlkPzX7n+IPx5irb33x+dHG4hmNmGWHNpQK+xz9U+Fetr2H9+rfM/Zx1DPYxQ+W0SRPcDJ2ER2dq43u17aF3IeBL1l9CmpsaWLXGZR82psLbDoNHqlhOLUBm6nTZXo94EtbeqBQSkZZatigmlXl3brrX9OhyIwHMstq5oeSZvIH8Ns5jBRjykk+x5hdvTKxjzczAj9Tj7bqm/wIDAQAB";
        String signType = "RSA2";
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);

//        alipayClient.execute()
    }

    public static void pay(AlipayClient alipayClient) {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        request.setBizModel(model);

        model.setOutTradeNo(System.currentTimeMillis() + "");
        model.setSubject("Iphone6 16G");
        model.setTotalAmount("0.01");
        model.setAuthCode("281016455056401995");//沙箱钱包中的付款码
        model.setScene("bar_code");

        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
            System.out.println(response.getBody());
            System.out.println(response.getTradeNo());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
