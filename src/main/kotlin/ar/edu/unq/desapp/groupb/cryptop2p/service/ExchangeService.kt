package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.service.helpers.Root
import ar.edu.unq.desapp.groupb.cryptop2p.service.helpers.Symbol
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.AssetPriceDTO
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime


@Service
class ExchangeService(private val restTemplate: RestTemplate) {
    private val assetPrices = assetNames().associateWith { 10.00 }
    var logger: Logger = LoggerFactory.getLogger(ExchangeService::class.java)

    fun getConversionRateARStoUSD(): Double {
        logger.info("Conversion Rate ARS to USD...")
        val url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales"

        val response = restTemplate.getForEntity(url, Array<Root>::class.java)

        val officialRate = response.body?.first {
            it.casa?.nombre == "Dolar Oficial"
        } ?: throw ModelException("Could not find conversion rate")


        return officialRate.casa?.venta?.replace(",", ".")?.toDouble()
            ?: throw ModelException("Could not find conversion rate")
    }

    fun getCryptoAssetPrice(assetName: String): Double {
        logger.info("Get Crypto Asset Price...")
        return if (System.getenv("API_ON").isNullOrBlank()) {
            val url = "https://api.binance.com/api/v3/ticker/price?symbol=$assetName"
            val response = restTemplate.getForEntity(url, Symbol::class.java)

            response.body?.price ?: throw ModelException("Could not find asset price")
        } else {
            assetPrices[assetName] ?: throw ModelException("Could not find asset price")
        }
    }

    fun getCryptoAssetsPrices(assetNames: List<String>): Map<String, Double> {
        logger.info("Get Cryptos Assets Prices...")
        return if (System.getenv("API_ON").isNullOrBlank()) {
            val symbols =
                assetNames.joinToString(
                    prefix = "[",
                    postfix = "]",
                    separator = ",",
                    transform = { c -> "\"" + c + "\"" })
            val url = "https://api.binance.com/api/v3/ticker/price?symbols=$symbols"

            val response = restTemplate.getForEntity(url, Array<Symbol>::class.java)

            response.body?.associate { it.symbol!! to it.price!! }
                ?: throw ModelException("Could not find assets prices")
        } else {
            assetPrices
        }
    }

    fun quotesFromLast24HoursOfAsset(assetName: String): AssetPriceDTO {
        logger.info("Get quotes from last 24 Hours of Asset...")
        return if (System.getenv("API_ON").isNullOrBlank()) {
            val url = "https://api.binance.com/api/v3/ticker/24hr?symbol=$assetName"
            assetPrices[assetName] ?: throw ModelException("Could not find asset")
            val response = restTemplate.getForEntity(url, String::class.java)
            val mapper = ObjectMapper()
            val root: JsonNode = mapper.readTree(response.body)
            AssetPriceDTO(assetName, root.path("prevClosePrice").asDouble(), LocalDateTime.now())
        } else {
            assetPrices[assetName] ?: throw ModelException("Could not find asset")
            AssetPriceDTO(assetName, 5.0, LocalDateTime.now())
        }
    }
}

fun assetNames(): Set<String> = setOf(
    "ALICEUSDT",
    "MATICUSDT",
    "AXSUSDT",
    "AAVEUSDT",
    "ATOMUSDT",
    "NEOUSDT",
    "DOTUSDT",
    "ETHUSDT",
    "CAKEUSDT",
    "BTCUSDT",
    "BNBUSDT",
    "ADAUSDT",
    "TRXUSDT",
    "AUDIOUSDT"
)
