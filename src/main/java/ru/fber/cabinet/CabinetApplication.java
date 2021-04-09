package ru.fber.cabinet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CabinetApplication extends WebSecurityConfigurerAdapter {

    @GetMapping("/")
    public Map<String, Object> root(@AuthenticationPrincipal OidcUser user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("slavs", session.getAttribute("slavs"));
        map.put("oidcUser", user);
        return map;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(CabinetApplication.class, args);
    }
}