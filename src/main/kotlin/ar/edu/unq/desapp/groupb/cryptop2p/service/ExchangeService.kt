package ar.edu.unq.desapp.groupb.cryptop2p.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ExchangeService(private val restTemplate: RestTemplate) {
    fun getARStoUSDExchangeRate(): Double {
        val url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales"

        val response = restTemplate.getForEntity(url, Array<Root>::class.java)

        val officialRate = response.body?.first {
            it.casa?.nombre == "Dolar Oficial"
        } ?: throw Exception("Could not find conversion rate")

        return officialRate.casa?.venta?.replace(",", ".")?.toDouble()
            ?: throw Exception("Could not find conversion rate")
    }

    fun getCryptoAssetPrice(assetName: String): Double {
        val url = "https://api.binance.com/api/v3/ticker/price?symbol=$assetName"
        val response = restTemplate.getForEntity(url, String::class.java)
        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(response.body)

        return root.path("price").asDouble()
    }
}


class Casa(
    var compra: String?,
    var venta: String?,
    var nombre: String?,
    var fecha: String?,
)


class Root(
    var casa: Casa?,
)
