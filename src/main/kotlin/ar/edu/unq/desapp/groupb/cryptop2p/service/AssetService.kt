package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import jakarta.transaction.Transactional
import org.ehcache.Cache
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
    fun save(assetName: String): Asset {
        assetValidator.isCreationRequestValid(assetName)
        val price = exchangeService.getCryptoAssetPrice(assetName)
        val asset = Asset(assetName, created = LocalDateTime.now())
        val assetPrice = AssetPrice(asset, price, created = LocalDateTime.now())
        asset.prices.add(assetPrice)
        assetPriceCache.put(assetName, assetPrice)
        return assetRepository.save(asset)
    }

    fun getAssetPrices(): Collection<AssetPrice> {
        val cachedAssetPrices = assetPriceCache.getAll(assetNames()).toList()
        val assetPrices = if (cachedAssetPrices.any { it.second == null }) {
            assetPriceRepository.findLatestPrices()
        } else {
            cachedAssetPrices.map { it.second }
        }

        return assetPrices
    }

    fun getAssetPricesFromLast24Hours(assetName: String): Collection<AssetPrice> {
        return assetPriceRepository.findAllByAssetAndCreatedAfter(assetName, LocalDateTime.now().minusHours(24))
    }

    @Scheduled(fixedDelay = 600000)
    fun updateAssetPricesEvery10Minutes() {
        val assets = assetRepository.findAll()
        assets.forEach {
            val price = exchangeService.getCryptoAssetPrice(it.name!!)
            val assetPrice = AssetPrice(it, price, created = LocalDateTime.now())
            it.prices.add(assetPrice)
            assetPriceCache.put(it.name!!, assetPrice)
        }
        assetRepository.saveAll(assets)
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}
