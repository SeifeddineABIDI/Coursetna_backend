package tn.esprit.pidev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Oauth2Config {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        return new InMemoryClientRegistrationRepository(this.clientRegistration());
    }
    private ClientRegistration clientRegistration(){
       return ClientRegistration.withRegistrationId("github")
                .clientId("Iv1.b04e44397f36f3a5")
                .clientSecret("8fc747124ad775de193db0adae0fe7344c79c10d")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope("read")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("GET https://api.github.com/user")
                .userNameAttributeName("id")
                .clientName("Seifeddine ABIDI")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:4200/login/oauth2/code/{registrationId}")
                .build();

    }

}
