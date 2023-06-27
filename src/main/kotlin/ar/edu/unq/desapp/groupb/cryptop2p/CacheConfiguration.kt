package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import org.ehcache.Cache
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class CacheConfiguration {
    @Bean
    fun assetPriceCache(): Cache<String, AssetPrice> {
        val cacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(
                String::class.java,
                AssetPrice::class.java,
                ResourcePoolsBuilder.heap(20),
            )
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
            .build()

        val cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .withCache(
                "assetPriceCache",
                cacheConfiguration,
            )
            .build()

        cacheManager.init()

        return cacheManager.getCache(
            "assetPriceCache",
            String::class.java,
            AssetPrice::class.java
        )
    }
}
