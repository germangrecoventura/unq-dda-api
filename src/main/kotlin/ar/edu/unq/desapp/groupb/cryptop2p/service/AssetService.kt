package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
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

    fun clear() {
        assetRepository.deleteAll()
    }
}
