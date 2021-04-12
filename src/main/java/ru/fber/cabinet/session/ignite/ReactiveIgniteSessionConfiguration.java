package ru.fber.cabinet.session.ignite;

import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;

import java.util.Optional;

import static ru.fber.cabinet.session.ignite.SessionRepositoryConfigurationUtils.getSessionRepository;

@Configuration
@ConditionalOnClass(name = "reactor.core.publisher.Mono")
public class ReactiveIgniteSessionConfiguration {

    /**
     * Max inactive interval in seconds
     */
    @Value("${spring.session.ignite.defaultMaxInactiveInterval:#{null}}")
    private Integer maxInactiveInterval;

    @Bean
    public ReactiveSessionRepository<MapSession> reactiveSessionRepository(IgniteClient ignite,
                                                                           ClientCacheConfiguration sessionCacheConfiguration) {
        ReactiveMapSessionRepository sessionRepository = getSessionRepository(ignite, sessionCacheConfiguration, ReactiveMapSessionRepository::new);
        Optional.ofNullable(maxInactiveInterval)
                .ifPresent(sessionRepository::setDefaultMaxInactiveInterval);

        return sessionRepository;
    }
}
