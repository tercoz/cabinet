package ru.fber.cabinet.session.ignite;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.Optional;

import static ru.fber.cabinet.session.ignite.SessionRepositoryConfigurationUtils.getSessionRepository;

@Configuration
@EnableSpringHttpSession
@Import(ReactiveIgniteSessionConfiguration.class)
public class IgniteSessionConfiguration {

    /**
     * Max inactive interval in seconds
     */
    @Value("${spring.session.ignite.defaultMaxInactiveInterval:#{null}}")
    private Integer maxInactiveInterval;

    @Value("${spring.session.ignite.cacheName:spring.session.cache}")
    private String cacheName;

    @Bean
    public ClientCacheConfiguration clientCacheConfiguration() {
        return new ClientCacheConfiguration()
                .setCacheMode(CacheMode.REPLICATED)
                .setName(cacheName);
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository(IgniteClient ignite,
                                                           ClientCacheConfiguration sessionCacheConfiguration) {
        MapSessionRepository sessionRepository = getSessionRepository(ignite, sessionCacheConfiguration, MapSessionRepository::new);
        Optional.ofNullable(maxInactiveInterval)
                .ifPresent(sessionRepository::setDefaultMaxInactiveInterval);

        return sessionRepository;
    }
}
