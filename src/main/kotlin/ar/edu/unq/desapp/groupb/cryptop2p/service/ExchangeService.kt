package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ExchangeService(private val restTemplate: RestTemplate) {
    private val assetPrices = assetNames().associateWith { 10.00 }

    fun getConversionRateARStoUSD(): Double {
        val url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales"

        val response = restTemplate.getForEntity(url, Array<Root>::class.java)

        val officialRate = response.body?.first {
            it.casa?.nombre == "Dolar Oficial"
        } ?: throw ModelException("Could not find conversion rate")

        return officialRate.casa?.venta?.replace(",", ".")?.toDouble()
            ?: throw ModelException("Could not find conversion rate")
    }

    fun getCryptoAssetPrice(assetName: String): Double {
        return if (System.getenv("API_ON").isNullOrBlank()) {
            val url = "https://api.binance.com/api/v3/ticker/price?symbol=$assetName"
            val response = restTemplate.getForEntity(url, Symbol::class.java)

            response.body?.price ?: throw ModelException("Could not find asset price")
        } else {
            assetPrices[assetName] ?: throw ModelException("Could not find asset price")
        }
    }

    fun getCryptoAssetsPrices(assetNames: List<String>): Map<String, Double> {
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
}


data class Casa(
    var compra: String?,
    var venta: String?,
    var nombre: String?,
    var fecha: String?,
)


data class Root(
    var casa: Casa?,
)

data class Symbol(
    var symbol: String?,
    var price: Double?,
)


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
