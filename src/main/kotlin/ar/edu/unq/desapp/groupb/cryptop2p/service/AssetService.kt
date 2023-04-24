package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.AssetValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
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
    private val assetPriceRepository: AssetPriceRepository,
    private val assetValidator: AssetValidator
) {
    fun save(assetName: String): Asset {
        assetValidator.isCreationRequestValid(assetName)

        val restTemplate = RestTemplate()
        val url = "https://api.binance.com/api/v3/ticker/price?symbol=$assetName"
        val response = restTemplate.getForEntity(url, String::class.java)
        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(response.body)

        val price = root.path("price").asDouble()
        val assetPrice =
            assetPriceRepository.save(AssetPrice(assetName, price, LocalDateTime.now(), LocalDateTime.now()))
        val asset = Asset(assetName, LocalDateTime.now(), mutableSetOf(assetPrice))
        return assetRepository.save(asset)
    }

    fun clear() {
        assetRepository.deleteAll()
    }
}