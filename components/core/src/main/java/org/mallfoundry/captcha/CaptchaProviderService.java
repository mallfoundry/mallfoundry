package org.mallfoundry.captcha;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CaptchaProviderService extends AbstractCaptchaService {

    private final List<CaptchaProvider> providers;

    public CaptchaProviderService(CaptchaAntiSpamService captchaAntiSpamService,
                                  CaptchaRepository repository, List<CaptchaProvider> providers) {
        super(captchaAntiSpamService, repository);
        this.providers = Objects.isNull(providers)
                ? Collections.emptyList()
                : providers;
    }

    @Override
    protected void doClearCaptcha(Captcha captcha) throws CaptchaException {
        for (var provider : this.providers) {
            if (!provider.supports(captcha)) {
                continue;
            }
            provider.clearCaptcha(captcha);
            break;
        }
    }

    @Override
    protected void doGenerateCaptcha(Captcha captcha) throws CaptchaException {
        for (var provider : this.providers) {
            if (!provider.supports(captcha)) {
                continue;
            }
            provider.generateCaptcha(captcha);
            break;
        }
    }
}
