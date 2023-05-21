package ar.edu.unq.desapp.groupb.cryptop2p.service

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
