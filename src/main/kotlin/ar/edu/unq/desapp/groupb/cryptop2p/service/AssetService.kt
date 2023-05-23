package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import jakarta.transaction.Transactional
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
    fun save(assetName: String): Asset {
        assetValidator.isCreationRequestValid(assetName)
        val price = exchangeService.getCryptoAssetPrice(assetName)

        val asset = Asset(assetName, created = LocalDateTime.now())
        val assetPrice = AssetPrice(asset, price, created = LocalDateTime.now())
        asset.prices.add(assetPrice)
        return assetRepository.save(asset)
    }

    fun getAssetPrices(): Collection<AssetPrice> {
        return assetPriceRepository.findLatestPrices()
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}
