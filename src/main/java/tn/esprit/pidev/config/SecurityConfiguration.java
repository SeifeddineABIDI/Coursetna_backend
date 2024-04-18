    package tn.esprit.pidev.config;


    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.io.DefaultResourceLoader;
    import org.springframework.core.io.ResourceLoader;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.security.web.authentication.logout.LogoutHandler;
    import tn.esprit.pidev.entities.Role;
    import static tn.esprit.pidev.entities.Role.ADMIN;
    import static tn.esprit.pidev.entities.Role.MANAGER;

    import static org.springframework.http.HttpMethod.DELETE;
    import static org.springframework.http.HttpMethod.GET;
    import static org.springframework.http.HttpMethod.POST;
    import static org.springframework.http.HttpMethod.PUT;
    import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;
    import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    @EnableMethodSecurity
    public class SecurityConfiguration {
        @Bean
        public ResourceLoader resourceLoader() {
            return new DefaultResourceLoader();
        }
        private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
                "/user/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html"};
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final LogoutService logoutService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                    http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req ->
                            req.requestMatchers(WHITE_LIST_URL)
                                    .permitAll()
                                    .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                    .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(Role.ADMIN.toString())
                                    .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(Role.ADMIN.toString())
                                    .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(Role.ADMIN.toString())
                                    .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(Role.ADMIN.toString())
                                    .requestMatchers("/user/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                    .anyRequest()
                                    .authenticated()

                    )

                    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout(logout ->
                            logout.logoutUrl("/api/v1/auth/logout")
                                    .addLogoutHandler(logoutService)
                                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                    );
                     http.oauth2Login(c->c.defaultSuccessUrl("/api/v1/auth"));

            return http.build();
        }


    }