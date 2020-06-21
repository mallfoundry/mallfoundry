package org.mallfoundry.identity;

import org.springframework.stereotype.Service;

@Service
public class UsernameGenerator {
    public String generate(String id) {
        return "mf_" + id;
    }
}
