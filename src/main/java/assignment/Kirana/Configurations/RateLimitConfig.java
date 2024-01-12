package assignment.Kirana.Configurations;

import assignment.Kirana.Exceptions.UserNotFound;
import assignment.Kirana.Services.UserService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Configuration class for rate limiting using Bucket4j library. */
@Component
public class RateLimitConfig {

    /** ProxyManager for managing the creation and retrieval of buckets. */
    @Autowired public ProxyManager<String> buckets;

    /** Service for managing user-related operations. */
    @Autowired public UserService userService;

    /**
     * Resolves a Bucket for the specified key (in this case, user ID).
     *
     * @param key The key for which the Bucket is resolved.
     * @return The resolved Bucket.
     */
    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);
        // Does not always create a new bucket, but instead returns the existing one if it exists.
        return buckets.builder().build(key, configSupplier);
    }

    /**
     * Gets a Supplier for BucketConfiguration based on the user's information.
     *
     * @param userId The user ID for which the configuration is obtained.
     * @return The Supplier for BucketConfiguration.
     * @throws UserNotFound If the specified user is not found.
     */
    private Supplier<BucketConfiguration> getConfigSupplierForUser(String userId) {

        // Configuring rate limit: 2 requests per minute

        // Refill is deprecated
        //        Refill refill = Refill.intervally(2, Duration.ofMinutes(1));
        //        Bandwidth limit = Bandwidth.classic(2, refill);

        // new syntax from bucket4j documentation
        return () ->
                (BucketConfiguration.builder()
                        .addLimit(
                                limit ->
                                        limit.capacity(2)
                                                .refillIntervally(2, Duration.ofMinutes(1)))
                        .build());
        // return () -> (BucketConfiguration.builder().addLimit(limit).build());
    }
}
