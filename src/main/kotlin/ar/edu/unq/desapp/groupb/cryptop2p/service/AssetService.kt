package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.AssetPriceDTO
import jakarta.transaction.Transactional
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
) {
    var logger: Logger = LoggerFactory.getLogger(AssetService::class.java)
    fun save(assetName: String): Asset {
        logger.info("Saving Assets...")
        assetValidator.isCreationRequestValid(assetName)
        val price = exchangeService.getCryptoAssetPrice(assetName)
        val asset = Asset(assetName, created = LocalDateTime.now())
        val assetPrice = AssetPrice(asset, price, created = LocalDateTime.now())
        asset.prices.add(assetPrice)
        return assetRepository.save(asset)
    }

    fun getAssetPrices(): Collection<AssetPrice> {
        logger.info("Return AssetPrices...")
        return assetPriceRepository.findLatestPrices()
    }

    fun quotesFromLast24HoursOfAsset(assetName: String): AssetPriceDTO {
        logger.info("Return quotes from last 24 Hours of Asset...")
        return exchangeService.quotesFromLast24HoursOfAsset(assetName)
    }

    @Scheduled(fixedDelay = 600000)
    fun quotesEvery10Minutes() {
        assetNames().forEach { asset ->
            var assetRecovery = assetRepository.findByName(asset)
                .orElseThrow { ModelException("The asset does not belong to the system", "asset.name") }
            val price = exchangeService.getCryptoAssetPrice(asset)
            val assetPrice = AssetPrice(assetRecovery, price, created = LocalDateTime.now())
            assetRecovery.prices.add(assetPrice)
            assetRepository.save(assetRecovery)
        }
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}