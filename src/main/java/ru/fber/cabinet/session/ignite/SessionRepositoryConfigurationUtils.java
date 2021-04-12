package ru.fber.cabinet.session.ignite;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.IgniteClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.session.Session;

import java.util.function.Function;

@UtilityClass
class SessionRepositoryConfigurationUtils {

    @NotNull
    static <R> R getSessionRepository(@NonNull IgniteClient ignite,
                                      @NonNull ClientCacheConfiguration sessionCacheConfiguration,
                                      @NonNull Function<CacheMapAdapter<String, Session>, R> repositorySupplier) {
        ClientCache<String, Session> sessionCache = ignite.getOrCreateCache(sessionCacheConfiguration);
        CacheMapAdapter<String, Session> mappedSessionCache = new CacheMapAdapter<>(sessionCache);
        return repositorySupplier.apply(mappedSessionCache);
    }
}
