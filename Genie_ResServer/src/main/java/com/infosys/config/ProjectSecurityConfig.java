package com.infosys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {



    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        // to accept jwt tokens
        JwtAuthenticationConverter jwtAuthenticationConvertor = new JwtAuthenticationConverter();
        jwtAuthenticationConvertor.setJwtGrantedAuthoritiesConverter(new RoleConvertor());

        http.authorizeHttpRequests((req)->req.requestMatchers("/registerGenie","/registerWishmaker","/getWishes","/error","/getWishmakerByWishId").permitAll()
                .requestMatchers("/insertWish","/getWishmaker","/getWishesByWishMakerId").hasRole("WISHMAKER")
                .requestMatchers("/getGenie","/getWishByGenieId","/acceptWish").hasRole("GENIE"));
        http.sessionManagement(sc->sc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(sdf->sdf.disable());


        http.oauth2ResourceServer(orc->orc.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConvertor)));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
