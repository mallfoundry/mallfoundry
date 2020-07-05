package org.mallfoundry.security.authentication;

/**
 * 验证码凭证，用于使用验证码凭证进行认证。
 *
 * @author Zhi Tang
 */
public interface CaptchaCredentials extends Credentials {

    @Override
    default CredentialsType getType() {
        return CredentialsType.CAPTCHA;
    }

    String getCode();

    String getToken();
}
