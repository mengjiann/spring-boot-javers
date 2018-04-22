package com.mj.javers.config;

import com.google.common.collect.ImmutableMap;
import org.javers.spring.auditable.CommitPropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JaversAuditConfig {

//    Enable this if your project contains spring security, username will be auto added.
//    @Bean
//    public AuthorProvider authorProvider(){
//        return new SpringSecurityAuthorProvider();
//    }

    @Bean
    public CommitPropertiesProvider commitPropertiesProvider(){
        return new CommitPropertiesProvider() {
            @Override
            public Map<String, String> provide() {
                return ImmutableMap.of("sample-key-1","sample-value-1");
            }
        };
    }


}
