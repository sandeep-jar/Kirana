package assignment.Kirana.Configurations;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {
    // autowiring dependencies

    @Autowired public ProxyManager buckets;

    /**
     * @param key In a production env, the resolveBucket function takes in the key param as an
     *     authentication token(say). Then the relevant user details can be extracted from that
     *     token to fetch the corresponding rate limit details for that particular user from the DB
     *     and subsequently process the request according to those details.
     */
    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);
        // Does not always create a new bucket, but instead returns the existing one if it exists.
        return buckets.builder().build(key, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String key) {

        Refill refill = Refill.intervally(2, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(2, refill);

        return () -> (BucketConfiguration.builder().addLimit(limit).build());
    }
}
