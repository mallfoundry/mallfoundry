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
