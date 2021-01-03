package com.smellylist.api.security;

import com.smellylist.api.common.Constants;
import com.smellylist.api.security.handlers.SmellyAccessDeniedHandler;
import com.smellylist.api.security.handlers.SmellyAuthenticationEntryPoint;
import com.smellylist.api.jwt.filter.JwtTokenFilterConfigurationAdapter;
import com.smellylist.api.jwt.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private Environment environment;

    /**
     * Paths to be whitelisted from checking for authorization
     */
    private static final String[] publicUrls = new String[] {
            "/auth/sign_in",
            "/marco",
            "/public/**",
    };

    /**
     * Paths to be whitelisted IN DEV PROFILE ONLY from checking for authorization.
     */
    private static final String[] devUrl = new String[] {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/h2-console/**/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
         http.csrf().and().csrf().disable()
                 // Entry points
                 .authorizeRequests()
                 // Allow whitelisted URLs
                 .antMatchers(publicAndDevUrls()).permitAll()
                 // Disallow everything else..
                 .anyRequest().authenticated()
                 .and()
                 // Add custom AccessDenied handler
                 .exceptionHandling()
                 .accessDeniedHandler(accessDeniedHandler())
                 // With a custom authentication entry point
                 .authenticationEntryPoint(authenticationEntryPoint())
                 .and()
                 // Apply JWT Filter
                 .apply(new JwtTokenFilterConfigurationAdapter(jwtTokenProvider))
                 .and()
                 // No session will be created or used by spring security
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        // If a user try to access a resource without having enough permissions
        // http.exceptionHandling().accessDeniedPage("/login");
    }

    /**
     * Whitelists urls based on active profiles.
     * If dev profile is active, merge public urls and dev profile urls.
     * Else return common public urls.
     * @return list of urls to whitelist.
     */
    private String[] publicAndDevUrls() {
        boolean isDev = Arrays.asList(this.environment.getActiveProfiles()).contains(Constants.PROFILE_DEV);
        if (isDev) {
            String[] finalPublicUrlList = new String[publicUrls.length + devUrl.length];
            System.arraycopy(publicUrls, 0, finalPublicUrlList, 0, publicUrls.length);
            System.arraycopy(devUrl, 0, finalPublicUrlList, publicUrls.length, devUrl.length);
            return finalPublicUrlList;
        } else {
            return publicUrls;
        }
    }

    /**
     * Configure CORS
     * @return Custom CORS configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Whitelist all
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new SmellyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new SmellyAuthenticationEntryPoint();
    }
}
