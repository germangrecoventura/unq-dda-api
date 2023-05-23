package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
@Transactional
class AssetService(
    private val assetRepository: AssetRepository,
    private val assetValidator: AssetValidator,
    private val restTemplate: RestTemplate,
) {
    fun save(assetName: String): Asset {
        assetValidator.isCreationRequestValid(assetName)

        val url = "https://api.binance.com/api/v3/ticker/price?symbol=$assetName"
        val response = restTemplate.getForEntity(url, String::class.java)
        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(response.body)

        val price = root.path("price").asDouble()

        val asset = Asset(assetName, created = LocalDateTime.now())
        val assetPrice = AssetPrice(asset, price, created = LocalDateTime.now())
        asset.prices.add(assetPrice)
        return assetRepository.save(asset)
    }

    fun getAssetPrices(): Set<AssetPrice> {
        val assets = assetRepository.findAll()
        return assets.map { it.prices.last() }.toSet()
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}
