package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.AssetPriceDTO
import jakarta.transaction.Transactional
import org.ehcache.Cache
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
@EnableScheduling

class AssetService(
    private val assetRepository: AssetRepository,
    private val assetPriceRepository: AssetPriceRepository,
    private val assetValidator: AssetValidator,
    private val exchangeService: ExchangeService,
    private val assetPriceCache: Cache<String, AssetPrice>,
) {
    var logger: Logger = LoggerFactory.getLogger(AssetService::class.java)

    fun save(assetName: String): Asset {
        logger.info("Saving Asset...")
        assetValidator.isCreationRequestValid(assetName)
        val price = exchangeService.getCryptoAssetPrice(assetName)
        val asset = Asset(assetName, created = LocalDateTime.now())
        val assetPrice = AssetPrice(asset, price, created = LocalDateTime.now())
        asset.prices.add(assetPrice)
        assetPriceCache.put(assetName, assetPrice)
        return assetRepository.save(asset)
    }

    fun getAssetPrices(): Collection<AssetPrice> {
        val assetPrices = if (assetPriceCache.none { it.value == null }) {
            logger.info("Returning AssetPrice list from cache...")
            val cachedAssetPrices = assetPriceCache.getAll(assetNames()).toList()
            cachedAssetPrices.map { it.second }
        } else {
            logger.info("Returning AssetPrice list from DB...")
            assetPriceRepository.findLatestPrices()
        }
        return assetPrices
    }

    fun getAssetPricesFromLast24Hours(assetName: String): AssetPriceDTO {
        logger.info("Returning assets prices from the last 24 hours...")
        return exchangeService.getAssetPricesFromLast24Hours(assetName)
    }

    @Scheduled(fixedDelay = 600000)
    fun updateAssetPricesEvery10Minutes() {
        val assets = assetRepository.findAll()
        assets.forEach {
            val price = exchangeService.getCryptoAssetPrice(it.name!!)
            val assetPrice = AssetPrice(it, price, created = LocalDateTime.now())
            assetPriceCache.put(it.name!!, assetPrice)
        }
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}
