package edu.ujn.config;

import edu.ujn.util.DMUtils;
import edu.ujn.util.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }

    @Bean
    public DMUtils dmUtils(){
        return new DMUtils();
    }
}
