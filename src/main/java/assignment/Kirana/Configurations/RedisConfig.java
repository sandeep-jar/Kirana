package assignment.Kirana.Configurations;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import javax.cache.CacheManager;
import javax.cache.Caching;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration class for integrating Redis as a caching provider using Bucket4j library. */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Configures the Redis connection details.
     *
     * @return Redis configuration.
     */
    @Bean
    public Config config() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        return config;
    }

    /**
     * Creates and configures the CacheManager for Redis caching.
     *
     * @param config The Redis configuration.
     * @return Configured CacheManager.
     */
    @Bean(name = "Mymanager")
    public CacheManager cacheManager(Config config) {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        manager.createCache("cache", RedissonConfiguration.fromConfig(config));
        return manager;
    }

    /**
     * Creates a ProxyManager using JCache for distributed rate limiting.
     *
     * @param cacheManager The CacheManager used for rate limiting.
     * @return ProxyManager for distributed rate limiting using JCache.
     */
    @Bean
    ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache("cache"));
    }
}
