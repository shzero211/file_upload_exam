package com.ll.exam.app10.base;

import com.ll.exam.app10.member.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevInitData {
    @Bean
    CommandLineRunner init(MemberService memberService, PasswordEncoder passwordEncoder){
        return args -> {
            String password=passwordEncoder.encode("1234");
            memberService.join("user1",password,"user1@test.com");
            memberService.join("user2",password,"user2@test.com");
        };

    }
}
