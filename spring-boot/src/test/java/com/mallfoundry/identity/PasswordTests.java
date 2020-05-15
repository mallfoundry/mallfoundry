package com.mallfoundry.identity;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTests {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String e1 = passwordEncoder.encode("1");
        String e2 = passwordEncoder.encode("1");

        System.out.println(passwordEncoder.matches("1", e1));
    }
}
