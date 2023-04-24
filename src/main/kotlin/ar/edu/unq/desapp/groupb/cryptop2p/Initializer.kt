package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Initializer {
    @Autowired
    lateinit var assetService: AssetService

    fun cleanDataBase() {
        assetService.clear()
    }

    fun loadData() {
        loadAssets()
    }


    private fun loadAssets() {
        val assets = mutableSetOf(
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

        assets.forEach { asset ->
            assetService.save(asset)
        }
    }
}
